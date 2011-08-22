package com.os.idb.client;

import java.util.List;

import com.google.gwt.core.client.Duration;
import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class IDBDatabase extends JavaScriptObject {
	protected IDBDatabase() {
	}
	
	public final native String name() /*-{
		return this.name;
	}-*/;

	public final native String version() /*-{
		return this.version;
	}-*/;

	public final List<String> getObjectStoreNames() {
		return IDBUtils.toList(getObjectStoreNames0());
	}

	private native JsArrayString getObjectStoreNames0() /*-{
		return this.objectStoreNames;
	}-*/;

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

	public final native void onVersionChange(IDBCallback pCallback) /*-{
		this.onversionchange = function(evt) {
			@com.os.idb.client.IDBUtils::fireCallback(Lcom/os/idb/client/IDBCallback;Lcom/os/idb/client/IDBEvent;)(pCallback,evt);
		};
	}-*/;
	
	public final native void close() /*-{
		this.close();
	}-*/;
	
	private native IDBTransaction transaction0(JsArrayString pStoreNames,int pMode) throws JavaScriptException /*-{
		return this.transaction(pStoreNames,pMode);
	}-*/;
	
	public final IDBTransaction transaction(String[] pStoreNames,int pMode) throws IDBException {
		try {
			return transaction0(IDBUtils.toJsArray(pStoreNames), pMode);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}

	public final IDBTransaction transaction(String[] pStoreNames) throws IDBException {
		return transaction(pStoreNames,IDBTransaction.READ_WRITE);
	}
	
	private native IDBObjectStore createObjectStore0(String pName) throws JavaScriptException /*-{
		return this.createObjectStore(pName);
	}-*/;

	private native IDBObjectStore createObjectStore0(String pName,IDBDatabaseOptionalParameters pParams) throws JavaScriptException /*-{
		return this.createObjectStore(pName,pParams);
	}-*/;
	
	public final IDBObjectStore createObjectStore(String pName) throws IDBException {
		try {
			return this.createObjectStore0(pName);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}

	public final IDBObjectStore createObjectStore(String pName,IDBDatabaseOptionalParameters pParams) throws IDBException {
		try {
			return this.createObjectStore0(pName,pParams);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}

	private native void deleteObjectStore0(String pName) throws JavaScriptException /*-{
		this.deleteObjectStore(pName);
	}-*/;

	public final void deleteObjectStore(String pName) throws IDBException {
		try {
			this.deleteObjectStore0(pName);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	private native IDBVersionChangeRequest setVersion0(String pVersion) throws JavaScriptException /*-{
		return this.setVersion(pVersion);
	}-*/;

	public final IDBVersionChangeRequest setVersion(String pName) throws IDBException {
		try {
			return this.setVersion0(pName);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	public final void createObjectStore(final String pName,final String pKeyPath,final boolean pAutoIncrement,final AsyncCallback<IDBObjectStore> pCallback) throws IDBException {
		IDBVersionChangeRequest ir = setVersion("" + Duration.currentTimeMillis() + "-" + Math.random());
		ir.onBlocked(new IDBCallback() {
			@Override
			public void onEvent(IDBEvent pEvent) {
				System.out.println("Blocked:" + pEvent);
				pCallback.onFailure(new IDBException(IDBException.UNKNOWN_ERR, pEvent.toString()));
			}
		});	
		ir.onError(new IDBCallback() {
			@Override
			public void onEvent(IDBEvent pEvent) {
				System.out.println("Error:" + pEvent);
				pCallback.onFailure(new IDBException(IDBException.UNKNOWN_ERR, pEvent.toString()));
			}
		});
		ir.onSuccess(new IDBCallback() {
			@Override
			public void onEvent(IDBEvent pEvent) {
				IDBDatabaseOptionalParameters p = IDBDatabaseOptionalParameters.create();
				p.setAutoIncrement(pAutoIncrement);
				if(pKeyPath != null) {
					p.setKeyPath(pKeyPath);
				}
				try {
					IDBObjectStore os = IDBDatabase.this.createObjectStore(pName, p);
					pCallback.onSuccess(os);
				} catch(IDBException ex) {
					pCallback.onFailure(ex);
				}
			}
		});
	}

	public final void deleteObjectStore(final String pName,final AsyncCallback<Void> pCallback) throws IDBException {
		IDBVersionChangeRequest ir = setVersion("" + Duration.currentTimeMillis() + "-" + Math.random());
		ir.onBlocked(new IDBCallback() {
			@Override
			public void onEvent(IDBEvent pEvent) {
				System.out.println("Blocked:" + pEvent);
				pCallback.onFailure(new IDBException(IDBException.UNKNOWN_ERR, pEvent.toString()));
			}
		});	
		ir.onError(new IDBCallback() {
			@Override
			public void onEvent(IDBEvent pEvent) {
				System.out.println("Error:" + pEvent);
				pCallback.onFailure(new IDBException(IDBException.UNKNOWN_ERR, pEvent.toString()));
			}
		});
		ir.onSuccess(new IDBCallback() {
			@Override
			public void onEvent(IDBEvent pEvent) {
				try {
					IDBDatabase.this.deleteObjectStore(pName);
					pCallback.onSuccess(null);
				} catch(IDBException ex) {
					pCallback.onFailure(ex);
				}
			}
		});
	}
	
	public static void open(String pName,final AsyncCallback<IDBDatabase> pCallback) {
		final IDBRequest ir = IDBFactory.open(pName);
		ir.onError(new IDBCallback() {
			@Override
			public void onEvent(IDBEvent pEvent) {
				System.out.println("Error:" + pEvent);
				pCallback.onFailure(new IDBException(IDBException.UNKNOWN_ERR, pEvent.toString()));
			}
		});
		ir.onSuccess(new IDBCallback() {
			@Override
			public void onEvent(IDBEvent pEvent) {
				try {
					pCallback.onSuccess((IDBDatabase)ir.result().cast());
				} catch(IDBException ex) {
					pCallback.onFailure(ex);
				}
			}
		});
	}
	
	public static void deleteDatabase(String pName,final AsyncCallback<IDBEvent> pCallback) {
		final IDBVersionChangeRequest ir = IDBFactory.deleteDatabase(pName);
		ir.onError(new IDBCallback() {
			@Override
			public void onEvent(IDBEvent pEvent) {
				System.out.println("Error:" + pEvent);
				pCallback.onFailure(new IDBException(IDBException.UNKNOWN_ERR, pEvent.toString()));
			}
		});
		ir.onBlocked(new IDBCallback() {
			@Override
			public void onEvent(IDBEvent pEvent) {
				System.out.println("Blocked:" + pEvent);
				pCallback.onFailure(new IDBException(IDBException.UNKNOWN_ERR, pEvent.toString()));
			}
		});
		ir.onSuccess(new IDBCallback() {
			@Override
			public void onEvent(IDBEvent pEvent) {
				pCallback.onSuccess(pEvent);
			}
		});
	}
	
}
