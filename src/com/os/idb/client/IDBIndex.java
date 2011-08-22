package com.os.idb.client;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.JavaScriptObject;

public class IDBIndex extends JavaScriptObject {
	protected IDBIndex() {
	}
	
	public final native String name() /*-{
		return this.name;
	}-*/;
	
	public final native IDBObjectStore objectStore() /*-{
		return this.objectStore;
	}-*/;
	
	public final native String keyPath() /*-{
		return this.keyPath;
	}-*/;
	
	public final native boolean unique() /*-{
		return this.unique;
	}-*/;

	private native IDBRequest openCursor0() throws JavaScriptException /*-{
		return this.openCursor();
	}-*/;
	
	private native IDBRequest openKeyCursor0() throws JavaScriptException /*-{
		return this.openKeyCursor();
	}-*/;
	
	private native IDBRequest openCursor0(IDBKeyRange pRange) throws JavaScriptException /*-{
		return this.openCursor(pRange);
	}-*/;

	private native IDBRequest openKeyCursor0(IDBKeyRange pRange) throws JavaScriptException /*-{
		return this.openKeyCursor(pRange);
	}-*/;
	
	private native IDBRequest openCursor0(IDBKeyRange pRange,int pDirection) throws JavaScriptException /*-{
		return this.openCursor(pRange,pDirection);
	}-*/;

	private native IDBRequest openKeyCursor0(IDBKeyRange pRange,int pDirection) throws JavaScriptException /*-{
		return this.openKeyCursor(pRange,pDirection);
	}-*/;
	
	public final IDBRequest openCursor() throws IDBException {
		try {
			return openCursor0();
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}

	public final IDBRequest openCursor(IDBKeyRange pRange) throws IDBException {
		try {
			return openCursor0(pRange);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}

	public final IDBRequest openCursor(IDBKeyRange pRange,int pDirection) throws IDBException {
		try {
			return openCursor0(pRange,pDirection);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	public final IDBRequest openKeyCursor() throws IDBException {
		try {
			return openKeyCursor0();
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}

	public final IDBRequest openKeyCursor(IDBKeyRange pRange) throws IDBException {
		try {
			return openKeyCursor0(pRange);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}

	public final IDBRequest openKeyCursor(IDBKeyRange pRange,int pDirection) throws IDBException {
		try {
			return openKeyCursor0(pRange,pDirection);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	private native IDBRequest get0(IDBKeyRange pRange) throws JavaScriptException /*-{
		return this.get(pRange);
	}-*/;
	
	private native IDBRequest getKey0(IDBKeyRange pRange) throws JavaScriptException /*-{
		return this.getKey(pRange);
	}-*/;

	public final IDBRequest get(IDBKeyRange pRange) throws IDBException {
		try {
			return get0(pRange);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}

	public final IDBRequest getKey(IDBKeyRange pRange) throws IDBException {
		try {
			return getKey0(pRange);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}

	private native IDBRequest count0(IDBKeyRange pRange) throws JavaScriptException /*-{
		return this.count(pRange);
	}-*/;
	
	private native IDBRequest count0() throws JavaScriptException /*-{
		return this.count();
	}-*/;
	
	public final IDBRequest count() throws IDBException {
		try {
			return count0();
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	public final IDBRequest count(IDBKeyRange pRange) throws IDBException {
		try {
			return count0(pRange);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
}
