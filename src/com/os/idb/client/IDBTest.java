package com.os.idb.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.future.client.Future;
import com.googlecode.future.client.FutureResult;
import com.os.idb.client.IDBDAO.EmptyExecCtx;
import com.os.idb.client.IDBDAO.VersionedScopedOp;
import com.os.idb.client.IDBDAO.TransactionScopedOp;
import com.os.idb.client.util.BufferedReader;
import com.os.idb.client.util.StringReader;

public class IDBTest implements EntryPoint {
	public static class Person extends JavaScriptObject {
		protected Person() {
		}

		public static Person create() {
			return JavaScriptObject.createObject().cast();
		}

		public final native void set(String pKey, int pValue) /*-{
			this[pKey] = pValue;
		}-*/;

		public final native void set(String pKey, String pValue) /*-{
			this[pKey] = pValue;
		}-*/;

		public final native String ssn() /*-{
			return this.ssn;
		}-*/;

		public final native int id() /*-{
			return this.id;
		}-*/;

		public final String asString() {
			// return new JSONObject(this).toString();
			return this.ssn() + "-" + this.id();
		}
	}

	public static int __id = 1;

	public static Person parseLine(String pLine) {
		if (pLine == null) {
			return null;
		}
		String[] sa = pLine.split(",");
		if (sa.length < 6) {
			return null;
		}
		Person c = Person.create();
		c.set("id", __id++);
		c.set("ssn", sa[0]);
		c.set("firstname", sa[1]);
		c.set("lastname", sa[2]);
		c.set("address", sa[3]);
		c.set("county", sa[4]);
		c.set("city", sa[5]);
		return c;
	}

	private static void populateIDBCollection(final BufferedReader pReader,final AsyncCallback<Void> pCallback) throws Exception {
		String line;
		int i = 0;
		List<JavaScriptObject> ljs = new ArrayList<JavaScriptObject>();
		while ((line = pReader.readLine()) != null) {
			Person c = parseLine(line);
			if (c == null || c.id() > 200) {
				break;
			}
			i++;
			if (i % 10 == 0) {
				System.out.println("Parsed " + i + " records");
			}
			if (i > 100) {
				break;
			}
			ljs.add(c);
		}
		if (!ljs.isEmpty()) {
			Person[] cc = ljs.toArray(new Person[ljs.size()]);
			ljs.clear();
			savePersons(cc, new AsyncCallback<Void>() {
				@Override
				public void onFailure(Throwable pCaught) {
					pCallback.onFailure(pCaught);
				}

				@Override
				public void onSuccess(Void pResult) {
					try {
						populateIDBCollection(pReader, pCallback);
					} catch (Exception ex) {
						pCallback.onFailure(ex);
					}
				}
			});
		} else {
			pCallback.onSuccess(null);
		}
	}

	protected static TransactionScopedOp<EmptyExecCtx> savePersonTask(final Person pPerson) {
		return new TransactionScopedOp<EmptyExecCtx>() {
			@Override
			public Future<EmptyExecCtx> execute(IDBTransaction pTransaction,final EmptyExecCtx pCtx) {
				System.out.println("Saving:" + pPerson.asString());
				final FutureResult<EmptyExecCtx> fr = new FutureResult<IDBDAO.EmptyExecCtx>();
				try {
					IDBObjectStore os = pTransaction.objectStore(getObjectStoreName());
					IDBRequest ir = os.add(pPerson);
					ir.onError(new IDBCallback() {
						@Override
						public void onEvent(IDBEvent pEvent) {
							fr.failWithException(new IDBException(IDBException.UNKNOWN_ERR,pEvent.toString()));
						}
					});
					ir.onSuccess(new IDBCallback() {
						@Override
						public void onEvent(IDBEvent pEvent) {
							fr.setResult(pCtx);
						}
					});
				} catch (IDBException ex) {
					fr.failWithException(ex);
				}
				return fr;
			}

			@Override
			public String getObjectStoreName() {
				return "Person";
			}
		};
	}

	protected static void savePersons(final Person[] pPersons,final AsyncCallback<Void> pCallback) {
		TransactionScopedOp<EmptyExecCtx>[] ops = new TransactionScopedOp[pPersons.length];
		for (int i = 0; i < pPersons.length; i++) {
			ops[i] = savePersonTask(pPersons[i]);
		}
		Future<EmptyExecCtx> fr = IDBDAO.executeReadWriteTransaction("Test",EmptyExecCtx.INSTANCE, ops);
		fr.addCallback(new AsyncCallback<EmptyExecCtx>() {
			@Override
			public void onFailure(Throwable pCaught) {
				pCaught.printStackTrace();
				pCallback.onFailure(pCaught);
			}

			@Override
			public void onSuccess(EmptyExecCtx pResult) {
				System.out.println("Saved " + pPersons.length + " persons");
				pCallback.onSuccess(null);
			}
		});
	}

	@Override
	public void onModuleLoad() {
		Future<EmptyExecCtx> fr = IDBDAO.executeVersioningTransaction("Test",EmptyExecCtx.INSTANCE, 
				new VersionedScopedOp<EmptyExecCtx>() {
					@Override
					public Future<EmptyExecCtx> execute(IDBDatabase pDatabase, EmptyExecCtx pCtx) {
						FutureResult<EmptyExecCtx> fr = new FutureResult<EmptyExecCtx>();
						try {
							pDatabase.deleteObjectStore("Person");
							fr.setResult(pCtx);
						} catch (IDBException ex) {
							//fr.failWithException(ex);
							fr.setResult(pCtx);
						}	
						return fr;
					}
			}, new VersionedScopedOp<EmptyExecCtx>() {
					@Override
					public Future<EmptyExecCtx> execute(IDBDatabase pDatabase, EmptyExecCtx pCtx) {
						FutureResult<EmptyExecCtx> fr = new FutureResult<EmptyExecCtx>();
						try {
							IDBDatabaseOptionalParameters p = IDBDatabaseOptionalParameters.create();
							p.setKeyPath("id");
							IDBObjectStore obj = pDatabase.createObjectStore("Person", p);
							obj.createIndex("ssnIndex", "ssn");
							fr.setResult(pCtx);
						} catch (IDBException ex) {
							fr.failWithException(ex);
						}
						return fr;
					}
			});
		fr.addCallback(new AsyncCallback<EmptyExecCtx>() {
			@Override
			public void onFailure(Throwable pCaught) {
				pCaught.printStackTrace();
			}

			@Override
			public void onSuccess(EmptyExecCtx pResult) {
				System.out.println("Created ObjectStore Person");
				RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, "c1.txt");
				System.out.println("Before load");
				rb.setCallback(new RequestCallback() {
					@Override
					public void onResponseReceived(Request pRequest, Response pResponse) {
						System.out.println("onResponseReceived " + pResponse.getStatusText());
						StringReader sr = new StringReader(pResponse.getText());
						BufferedReader br = new BufferedReader(sr);
						try {
							populateIDBCollection(br, new AsyncCallback<Void>() {
								@Override
								public void onFailure(Throwable pCaught) {
									pCaught.printStackTrace();
								}

								@Override
								public void onSuccess(Void pResult) {
									System.out.println("ObjectStore iteration starts");
									Future<EmptyExecCtx> fr = IDBDAO.executeReadOnlyTransaction("Test",EmptyExecCtx.INSTANCE,new TransactionScopedOp<EmptyExecCtx>() {
												@Override
												public Future<EmptyExecCtx> execute(IDBTransaction pTransaction,final EmptyExecCtx pCtx) {
													final FutureResult<EmptyExecCtx> fr = new FutureResult<IDBDAO.EmptyExecCtx>();
													try {
														final IDBObjectStore os = pTransaction.objectStore(getObjectStoreName());
														IDBDAO.iterateCursor(os.openCursor(IDBKeyRange.only(100)), new IDBCursorCallback() {
															@Override
															public boolean onKeyValue(IDBKeyValueRecord pRecord) {
																System.out.println("Found:" + pRecord.key() + " - " + pRecord.value());
																return true;
															}
															
															@Override
															public void onError(IDBEvent pEvent) {
																System.out.println("Error:" + pEvent);
																fr.failWithException(new IDBException(IDBException.UNKNOWN_ERR, "Cursor error"));
															}
															
															@Override
															public void onDone() {
																System.out.println("ObjectStore iteration done");
																try {
																	IDBIndex idbIdx = os.index("ssnIndex"); // 2561005047781
																	IDBDAO.iterateCursor(idbIdx.openCursor(IDBKeyRange.only("1690904335010")), new IDBCursorCallback() {
																		@Override
																		public boolean onKeyValue(IDBKeyValueRecord pRecord) {
																			System.out.println("Found:" + pRecord.key() + " - " + pRecord.value());
																			return true;
																		}
																		
																		@Override
																		public void onError(IDBEvent pEvent) {
																			System.out.println("Error:" + pEvent);
																			fr.failWithException(new IDBException(IDBException.UNKNOWN_ERR, "Cursor Error"));
																		}
																		
																		@Override
																		public void onDone() {
																			System.out.println("End index iteration");
																			fr.setResult(pCtx);
																		}
																	});
																} catch(IDBException ex) {
																	ex.printStackTrace();
																	fr.failWithException(ex);
																}
															}
														});
													} catch (IDBException ex) {
														fr.failWithException(ex);
													}
													return fr;
												}

												@Override
												public String getObjectStoreName() {
													return "Person";
												}
											}
									);
									fr.addCallback(new AsyncCallback<EmptyExecCtx>() {
										@Override
										public void onFailure(Throwable pCaught) {
											pCaught.printStackTrace();
										}

										@Override
										public void onSuccess(EmptyExecCtx pResult) {
											System.out.println("DONE!!!");
										}
									});
									
								}
							});
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}

					@Override
					public void onError(Request pRequest, Throwable pException) {
						pException.printStackTrace();
					}
				});
				try {
					rb.send();
				} catch (RequestException ex) {
					ex.printStackTrace();
				}
			}
		});
	}

}
