package com.os.idb.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayMixed;

public class IDBKeyValueRecord extends JavaScriptObject {
	protected IDBKeyValueRecord() {
	}
	
	public final native JsArrayMixed key() /*-{
		return [this.key];
	}-*/;

	public final native JavaScriptObject value() /*-{
		return this.value;
	}-*/;
}
