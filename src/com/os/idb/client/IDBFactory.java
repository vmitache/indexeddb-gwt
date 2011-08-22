package com.os.idb.client;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.JavaScriptObject;

public class IDBFactory extends JavaScriptObject {
	protected IDBFactory() {
	}
	
	private static native void init() /*-{
		$wnd.indexedDB = $wnd.indexedDB || $wnd.mozIndexedDB || $wnd.webkitIndexedDB;
	}-*/;

	static {
		init();
	}

	public final static native IDBRequest open(String pDBName) /*-{
		return $wnd.indexedDB.open(pDBName);
	}-*/;
	
	public final static native IDBVersionChangeRequest deleteDatabase(String pDBName) /*-{
		return $wnd.indexedDB.deleteDatabase(pDBName);
	}-*/;
	
	private static native int cmp0(JavaScriptObject pKey1,JavaScriptObject pKey2) throws JavaScriptException /*-{
		return $wnd.indexedDB.cmp(pKey1,pKey2);
	}-*/;
	
	public final static int cmp(JavaScriptObject pKey1,JavaScriptObject pKey2) throws IDBException {
		try {
			return cmp0(pKey1,pKey2);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
}