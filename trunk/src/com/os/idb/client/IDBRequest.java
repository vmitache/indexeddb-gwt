package com.os.idb.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;

public class IDBRequest extends JavaScriptObject {
	public static final int STATUS_LOADING 	= 1;
	public static final int STATUS_DONE 		= 2;
	
	protected IDBRequest() {
	}
	
  private static void fireCallback(IDBCallback pCallback,IDBEvent pEvent) {
    if (pCallback == null) {
      return;
    }
    UncaughtExceptionHandler ueh = GWT.getUncaughtExceptionHandler();
    if (ueh != null) {
      try {
        pCallback.onEvent(pEvent);
      } catch (Throwable e) {
        ueh.onUncaughtException(e);
      }
    } else {
      pCallback.onEvent(pEvent);
    }
  }
  
	private native JavaScriptObject getResult0() throws JavaScriptException /*-{
		return this.result;
	}-*/;
	
	private native int getErrorCode0() throws JavaScriptException /*-{
		return this.errorCode;
	}-*/;
	
	public final native JavaScriptObject source() /*-{
		return this.source;
	}-*/;

	public final native IDBTransaction transaction() /*-{
		return this.transaction;
	}-*/;
	
	public final native int readyState() /*-{
		return this.readyState;
	}-*/;
	
	public final JavaScriptObject result() throws IDBException {
		try {
			return getResult0();
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	public final int errorCode() throws IDBException {
		try {
			return getErrorCode0();
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}

	public final native void onError(IDBCallback pCallback) /*-{
  	this.onerror = function(evt) {
    	@com.os.idb.client.IDBRequest::fireCallback(Lcom/os/idb/client/IDBCallback;Lcom/os/idb/client/IDBEvent;)(pCallback,evt);
  	};		
	}-*/;

	public final native void onSuccess(IDBCallback pCallback) /*-{
		this.onsuccess = function(evt) {
  		@com.os.idb.client.IDBRequest::fireCallback(Lcom/os/idb/client/IDBCallback;Lcom/os/idb/client/IDBEvent;)(pCallback,evt);
		};		
	}-*/;
}