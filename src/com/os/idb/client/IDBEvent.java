package com.os.idb.client;

import com.google.gwt.core.client.JavaScriptObject;

public class IDBEvent extends JavaScriptObject {
	protected IDBEvent() {
	}
	
	public final native JavaScriptObject target() /*-{
		return this.target;
	}-*/; 
	
	public final native String type() /*-{
		return this.type;
	}-*/; 

	public final native JavaScriptObject targetResult() /*-{
		return this.target ? this.target.result : null;
	}-*/; 
	
	public final native void preventDefault() /*-{
		this.preventDefault();
	}-*/;
	
	public final native boolean cancelable() /*-{
		return this.cancelable;
	}-*/;
	
	public final native boolean bubbles() /*-{
		return this.bubbles;
	}-*/;
	
	public final native void stopPropagation() /*-{
		this.stopPropagation();
	}-*/;
}