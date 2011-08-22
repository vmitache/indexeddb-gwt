package com.os.idb.client;

import com.google.gwt.core.client.JavaScriptObject;

public class IDBVersionChangeEvent extends JavaScriptObject {
	protected IDBVersionChangeEvent() {
	}
	
	public final native String version() /*-{
		return this.version;
	}-*/;
	
	public final native void initIDBVersionChangeEvent(String pType,boolean pCanBubble,boolean pCancelable,String pVersion) /*-{
		this.initIDBVersionChangeEvent(pType,pCanBubble,pCancelable,pVersion);
	}-*/;
}
