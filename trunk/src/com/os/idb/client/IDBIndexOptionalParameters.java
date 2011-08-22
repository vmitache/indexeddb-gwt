package com.os.idb.client;

import com.google.gwt.core.client.JavaScriptObject;

public class IDBIndexOptionalParameters extends JavaScriptObject {
	protected IDBIndexOptionalParameters() {
	}
	
	public final native void setUnique(boolean pValue) /*-{
		this.unique = pValue;
	}-*/;
	
	public final native void setMultientry(boolean pValue) /*-{
		this.multientry = pValue;
	}-*/;
	
	public static IDBIndexOptionalParameters create() {
		IDBIndexOptionalParameters res = JavaScriptObject.createObject().cast();
		res.setUnique(false);
		res.setMultientry(false);
		return res;
	}
	
	public static IDBIndexOptionalParameters create(boolean pUnique,boolean pMultientry) {
		IDBIndexOptionalParameters res = JavaScriptObject.createObject().cast();
		res.setUnique(pUnique);
		res.setMultientry(pMultientry);
		return res;
	}
	
}
