package com.os.idb.client;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.JavaScriptObject;

public class IDBTransaction extends JavaScriptObject {
	public static final int READ_ONLY 			= 0;
	public static final int READ_WRITE 			= 1;
	public static final int VERSION_CHANGE 	= 2;
	
	private static native void init() /*-{
		$wnd.IDBTransaction = $wnd.IDBTransaction || $wnd.webkitIDBTransaction;
	}-*/;

	static {
		init();
	}

	protected IDBTransaction() {
	}
	
	public final native void onError(IDBCallback pCallback) /*-{
    this.onerror = function(evt) {
      @com.os.idb.client.IDBUtils::fireCallback(Lcom/os/idb/client/IDBCallback;Lcom/os/idb/client/IDBEvent;)(pCallback,evt);
    };		
	}-*/;
	
	public final native void onAbort(IDBCallback pCallback) /*-{
  	this.onabort = function(evt) {
    	@com.os.idb.client.IDBUtils::fireCallback(Lcom/os/idb/client/IDBCallback;Lcom/os/idb/client/IDBEvent;)(pCallback,evt);
  	};		
	}-*/;
	
	public final native void onComplete(IDBCallback pCallback) /*-{
		this.oncomplete = function(evt) {
  		@com.os.idb.client.IDBUtils::fireCallback(Lcom/os/idb/client/IDBCallback;Lcom/os/idb/client/IDBEvent;)(pCallback,evt);
		};		
	}-*/;
	
	private native void abort0() throws JavaScriptException /*-{
		this.abort();
	}-*/;
	
	public final void abort() throws IDBException {
		try {
			abort0();
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	public final native int mode() /*-{
		return this.mode;
	}-*/;
	
	public final native IDBDatabase db() /*-{
		return this.db;
	}-*/;
	
	private native IDBObjectStore objectStore0(String pName) throws JavaScriptException /*-{
		return this.objectStore(pName);
	}-*/; 
	
	public final IDBObjectStore objectStore(String pName) throws IDBException {
		try {
			return objectStore0(pName);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
}