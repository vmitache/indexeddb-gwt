package com.os.idb.client;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.JavaScriptObject;

public class IDBCursor extends JavaScriptObject {
	public static final int NEXT 							= 0;
	public static final int NEXT_NO_DUPLICATE = 1;
	public static final int PREV 							= 2;
	public static final int PREV_NO_DUPLICATE = 3;
	
	protected IDBCursor() {
	}
	
	public final native JavaScriptObject source() /*-{
		return this.source;
	}-*/;
	
	public final native int direction() /*-{
		return this.direction;
	}-*/;
	
	public final native JavaScriptObject key() /*-{
		return this.key;
	}-*/;
	
	public final native JavaScriptObject primaryKey() /*-{
		return this.primaryKey;
	}-*/;
	
	private native IDBRequest update0(JavaScriptObject pValue) throws JavaScriptException /*-{
		this.update(pValue);
	}-*/;
	
	public final IDBRequest update(JavaScriptObject pValue) throws IDBException {
		try {
			return update0(pValue);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	public final native void advance(int pCount) /*-{
		this.advance(pCount);
	}-*/; 
	
	
	private native IDBRequest delete0() throws JavaScriptException /*-{
		return this["delete"]();
	}-*/;

	public final IDBRequest delete() throws IDBException {
		try {
			return delete0();
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	private native void continue0() throws JavaScriptException /*-{
		this["continue"]();
	}-*/;
	
	private native void continue0(JavaScriptObject pKey) throws JavaScriptException /*-{
		this["continue"](pKey);
	}-*/;
	
	public final void doContinue() throws IDBException {
		try {
			continue0();
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	public final void doContinue(JavaScriptObject pKey) throws IDBException {
		try {
			continue0(pKey);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
}
