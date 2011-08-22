package com.os.idb.client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.core.client.Duration;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.future.client.Future;
import com.googlecode.future.client.FutureResult;

public class IDBDAO {
	public interface ExecCtx<R extends ExecCtx<R>> {
	}
	
	public static class EmptyExecCtx implements ExecCtx<EmptyExecCtx> {
		private EmptyExecCtx() {
		}
		
		public static final EmptyExecCtx INSTANCE = new EmptyExecCtx();
	}
	
	@SuppressWarnings("serial")
	public static class MapExecCtx extends HashMap<String, JavaScriptObject> implements ExecCtx<MapExecCtx> {
		public MapExecCtx() {
			super();
		}
	}

	public interface TransactionScopedOp<R extends ExecCtx<R>> {
		public Future<R> execute(IDBTransaction pTransaction,R pCtx); 
		public String getObjectStoreName();
	}
	
	public interface VersionedScopedOp<R extends ExecCtx<R>> {
		public Future<R> execute(IDBDatabase pDatabase,R pCtx);
	}
	
	private static class FutureTOpPair<R extends ExecCtx<R>> {
		Future<R> m_future;
		TransactionScopedOp<R> m_op;
		
		FutureTOpPair(TransactionScopedOp<R> pOp) {
			m_op = pOp;
		}
	}

	private static class FutureVOpPair<R extends ExecCtx<R>> {
		Future<R> m_future;
		VersionedScopedOp<R> m_op;
		
		FutureVOpPair(VersionedScopedOp<R> pOp) {
			m_op = pOp;
		}
	}
	
	private static <R extends ExecCtx<R>> void executeInTransactionScope(final FutureTOpPair<R>[] pOps,final int pIdx,final R pCtx,final IDBTransaction pTransaction,final AsyncCallback<R> pCallback) {
		if(pIdx >= pOps.length) {
			pCallback.onSuccess(pCtx);
		} else {
			final FutureTOpPair<R> fop = pOps[pIdx];
			fop.m_future = fop.m_op.execute(pTransaction, pCtx);
			fop.m_future.addCallback(new AsyncCallback<R>() {
				@Override
				public void onFailure(Throwable pCaught) {
					pCallback.onFailure(pCaught);
				}

				@Override
				public void onSuccess(R pResult) {
					executeInTransactionScope(pOps, pIdx + 1, pResult, pTransaction, pCallback);
				}
			});
		}
	}
	
	public static <R extends ExecCtx<R>> Future<R> executeTransaction(String pDBName,final int pMode,final R pCtx,final TransactionScopedOp<R>... pOps) {
		final Future<R> result = new FutureResult<R>();
		IDBDatabase.open(pDBName, new AsyncCallback<IDBDatabase>() {
			@Override
			public void onFailure(Throwable pCaught) {
				result.failWithException(pCaught);
			}

			@Override
			public void onSuccess(IDBDatabase pResult) {
				final Set<String> osNames = new HashSet<String>();
				for(TransactionScopedOp<R> op : pOps) {
					osNames.add(op.getObjectStoreName());
				}
				try {
					final IDBTransaction t = pResult.transaction(osNames.toArray(new String[osNames.size()]), pMode);
					final FutureTOpPair<R>[] opsList = new FutureTOpPair[pOps.length];
					for(int i=0;i < pOps.length;i++) {
						opsList[i] = new FutureTOpPair<R>(pOps[i]);
					}
					executeInTransactionScope(opsList,0, pCtx, t, new AsyncCallback<R>() {
						@Override
						public void onFailure(Throwable pCaught) {
							try {
								t.abort();
							} catch(Exception e) {
								e.printStackTrace();
							}
							result.failWithException(pCaught);
						}

						@Override
						public void onSuccess(R pResult) {
							result.setResult(pResult);
						}
					});
				} catch(IDBException ex) {
					result.failWithException(ex);
				}
			}
		});
		return result;
	}

	public static <R extends ExecCtx<R>> Future<R> executeReadOnlyTransaction(String pDBName,final R pCtx,final TransactionScopedOp<R>... pOps) {
		return executeTransaction(pDBName, IDBTransaction.READ_ONLY, pCtx, pOps);
	}
	
	public static <R extends ExecCtx<R>> Future<R> executeReadWriteTransaction(final String pDBName,final R pCtx,final TransactionScopedOp<R>... pOps) {
		return executeTransaction(pDBName, IDBTransaction.READ_WRITE, pCtx, pOps);
	}

	private static <R extends ExecCtx<R>> void executeInVersionScope(final FutureVOpPair<R>[] pOps,final int pIdx,final R pCtx,final IDBDatabase pDatabase,final AsyncCallback<R> pCallback) {
		if(pIdx >= pOps.length) {
			pCallback.onSuccess(pCtx);
		} else {
			final FutureVOpPair<R> fop = pOps[pIdx];
			fop.m_future = fop.m_op.execute(pDatabase, pCtx);
			fop.m_future.addCallback(new AsyncCallback<R>() {
				@Override
				public void onFailure(Throwable pCaught) {
					pCallback.onFailure(pCaught);
				}

				@Override
				public void onSuccess(R pResult) {
					executeInVersionScope(pOps,pIdx+1, pResult, pDatabase, pCallback);
				}
			});
		}
	}
	
	public static <R extends ExecCtx<R>> Future<R> executeVersioningTransaction(final String pDBName,final R pCtx,final VersionedScopedOp<R>... pOps) {
		final Future<R> result = new FutureResult<R>();
		IDBDatabase.open(pDBName, new AsyncCallback<IDBDatabase>() {
			@Override
			public void onFailure(Throwable pCaught) {
				result.failWithException(pCaught);
			}
			@Override
			public void onSuccess(final IDBDatabase pResult) {
				try {
					IDBVersionChangeRequest req = pResult.setVersion("V" + (Math.random()*10) + "-" + Duration.currentTimeMillis());
					req.onError(new IDBCallback() {
						@Override
						public void onEvent(IDBEvent pEvent) {
							System.out.println("Error:" + pEvent.toString());
							result.failWithException(new IDBException(IDBException.UNKNOWN_ERR, pEvent.toString()));
						}
					});
					req.onBlocked(new IDBCallback() {
						@Override
						public void onEvent(IDBEvent pEvent) {
							System.out.println("Blocked:" + pEvent.toString());
							result.failWithException(new IDBException(IDBException.UNKNOWN_ERR, pEvent.toString()));
						}
					});
					req.onSuccess(new IDBCallback() {
						@Override
						public void onEvent(IDBEvent pEvent) {
							final FutureVOpPair<R>[] opsList = new FutureVOpPair[pOps.length];
							for(int i=0;i < pOps.length;i++) {
								opsList[i] = new FutureVOpPair<R>(pOps[i]);
							}
							executeInVersionScope(opsList,0, pCtx, pResult, new AsyncCallback<R>() {
								@Override
								public void onFailure(Throwable pCaught) {
									result.failWithException(pCaught);
								}

								@Override
								public void onSuccess(R pResult) {
									result.setResult(pResult);
								}
							});
							
						}
					});
					pResult.onVersionChange(new IDBCallback() {
						@Override
						public void onEvent(IDBEvent pEvent) {
							pResult.close();
						}
					});
				} catch(IDBException ex) {
					result.failWithException(ex);
				}
			}
		});
		return result;
	}

	public static native void iterateCursor(IDBRequest pRequest,IDBCursorCallback pCallback) /*-{
		pRequest.onsuccess = function(evt) {
 			var cursor = event.target.result;
  		if (cursor) {
				@com.os.idb.client.IDBUtils::fireCallback(Lcom/os/idb/client/IDBCursorCallback;Lcom/os/idb/client/IDBKeyValueRecord;)(pCallback,cursor);
    		cursor["continue"]();
  		} else {
				@com.os.idb.client.IDBUtils::fireCallback(Lcom/os/idb/client/IDBCursorCallback;)(pCallback);
  		}
		};
		pRequest.onerror = function(evt) {
			@com.os.idb.client.IDBUtils::fireCallback(Lcom/os/idb/client/IDBCursorCallback;Lcom/os/idb/client/IDBEvent;)(pCallback,evt);
		};
	}-*/;
}