# Introduction #

IndexedDB API - http://www.w3.org/TR/IndexedDB/ - wrapper for GWT


# Details #

Uses http://code.google.com/p/gwt-async-future/ module ..
Usage
```
 <inherits name="com.os.idb.IndexedDB"/>
```

> define some classes to store (or use a JSON mapper library/convertor for regular java bean like classes)..
```
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
```

define a transactional task :

```
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
```

for batch updates:
```

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
```

example for Versioning transaction scope:

```
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
                        }
   });
```