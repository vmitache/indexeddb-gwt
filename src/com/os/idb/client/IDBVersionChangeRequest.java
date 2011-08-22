package com.os.idb.client;

public class IDBVersionChangeRequest extends IDBRequest {
	protected IDBVersionChangeRequest() {
		super();
	}
	
	public final native void onBlocked(IDBCallback pCallback) /*-{
		this.onblocked = function(evt) {
			@com.os.idb.client.IDBRequest::fireCallback(Lcom/os/idb/client/IDBCallback;Lcom/os/idb/client/IDBEvent;)(pCallback,evt);
		};		
}-*/;
}
