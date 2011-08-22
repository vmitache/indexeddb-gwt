package com.os.idb.client;

public interface IDBCursorCallback {
	public boolean onKeyValue(IDBKeyValueRecord pRecord);
	public void onDone();
	public void onError(IDBEvent pEvent);
}

