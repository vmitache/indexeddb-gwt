package com.os.idb.client;

import com.google.gwt.core.client.JavaScriptObject;

public class IDBCursorWithValue extends IDBCursor {
	protected IDBCursorWithValue() {
		super();
	}
	
	public final native JavaScriptObject value() /*-{
		return this.value;
	}-*/;
}