package com.os.idb.client;

import com.google.gwt.core.client.JavaScriptObject;

public class IDBDatabaseOptionalParameters extends JavaScriptObject {
	protected IDBDatabaseOptionalParameters() {
	}
	
	public final native void setAutoIncrement(boolean pValue) /*-{
		this.autoIncrement = pValue;
	}-*/;
	
	public final native void setKeyPath(String pKeyPath) /*-{
		this.keyPath = pKeyPath;
	}-*/;
	
	public static IDBDatabaseOptionalParameters create() {
		IDBDatabaseOptionalParameters res = JavaScriptObject.createObject().cast();
		res.setKeyPath(null);
		res.setAutoIncrement(true);
		return res;
	}
	
	public static IDBDatabaseOptionalParameters create(String pKeyPath,boolean pAutoIncrement) {
		IDBDatabaseOptionalParameters res = JavaScriptObject.createObject().cast();
		res.setKeyPath(pKeyPath);
		res.setAutoIncrement(pAutoIncrement);
		return res;
	}
	
}