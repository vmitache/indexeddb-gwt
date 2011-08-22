package com.os.idb.client;

import java.util.List;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;

public class IDBObjectStore extends JavaScriptObject {
	protected IDBObjectStore() {
	}
	
	public final native String name() /*-{
		return this.name;
	}-*/;
	
	public final native String keyPath() /*-{
		return this.keyPath;
	}-*/;
	
	private native JsArrayString indexNames0() /*-{
		return this.indexNames;
	}-*/;
	
	public final List<String> indexNames() {
		return IDBUtils.toList(indexNames0());
	}
	
	public final native IDBTransaction transaction() /*-{
		return this.transaction;
	}-*/;
	
	private native IDBRequest put0(JavaScriptObject pValue) throws JavaScriptException /*-{
		return this.put(pValue);
	}-*/;
	
	private native IDBRequest put0(JavaScriptObject pValue,JavaScriptObject pKey) throws JavaScriptException /*-{
		return this.put(pValue,pKey);
	}-*/;
	
	private native IDBRequest add0(JavaScriptObject pValue) throws JavaScriptException /*-{
		return this.add(pValue);
	}-*/;

	private native IDBRequest add0(JavaScriptObject pValue,JavaScriptObject pKey) throws JavaScriptException /*-{
		return this.add(pValue,pKey);
	}-*/;

	public final IDBRequest put(JavaScriptObject pValue) throws IDBException {
		try {
			return put0(pValue);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	public final IDBRequest put(JavaScriptObject pValue,JavaScriptObject pKey) throws IDBException {
		try {
			return put0(pValue,pKey);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	public final IDBRequest add(JavaScriptObject pValue) throws IDBException {
		try {
			return add0(pValue);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	public final IDBRequest add(JavaScriptObject pValue,JavaScriptObject pKey) throws IDBException {
		try {
			return add0(pValue,pKey);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	private native IDBRequest delete0(JavaScriptObject pKey) throws JavaScriptException /*-{
		return this["delete"](pKey);
	}-*/;

	public final IDBRequest delete(JavaScriptObject pKey) throws IDBException {
		try {
			return delete0(pKey);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	private native IDBRequest get0(JavaScriptObject pKey) throws JavaScriptException /*-{
		return this.get(pKey);
	}-*/;

	public final IDBRequest get(JavaScriptObject pKey) throws IDBException {
		try {
			return get0(pKey);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	private native IDBRequest clear0() throws JavaScriptException /*-{
		return this.clear();
	}-*/;

	public final IDBRequest clear() throws IDBException {
		try {
			return clear0();
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	private native IDBRequest openCursor0() throws JavaScriptException /*-{
		return this.openCursor();
	}-*/;
	
	private native IDBRequest openCursor0(IDBKeyRange pRange) throws JavaScriptException /*-{
		return this.openCursor(pRange);
	}-*/;
	
	private native IDBRequest openCursor0(IDBKeyRange pRange,int pDir) throws JavaScriptException /*-{
		return this.openCursor(pRange,pDir);
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
	
	public final IDBRequest openCursor(IDBKeyRange pRange,int pDir) throws IDBException {
		try {
			return openCursor0(pRange,pDir);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	private native IDBIndex createIndex0(String pName,String pKeyPath) throws JavaScriptException /*-{
		return this.createIndex(pName,pKeyPath);
	}-*/;

	private native IDBIndex createIndex0(String pName,String pKeyPath,IDBIndexOptionalParameters pOptions) throws JavaScriptException /*-{
		return this.createIndex(pName,pKeyPath,pOptions);
	}-*/;
	
	public final IDBIndex createIndex(String pName,String pKeyPath) throws IDBException {
		try {
			return createIndex0(pName,pKeyPath);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}

	public final IDBIndex createIndex(String pName,String pKeyPath,IDBIndexOptionalParameters pOptions) throws IDBException {
		try {
			return createIndex0(pName,pKeyPath,pOptions);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	private native IDBIndex index0(String pName) throws JavaScriptException /*-{
		return this.index(pName);
	}-*/;
	
	public final IDBIndex index(String pName) throws IDBException {
		try {
			return index0(pName);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	private native void deleteIndex0(String pName) throws JavaScriptException /*-{
		this.deleteIndex(pName);
	}-*/;

	public final void deleteIndex(String pName) throws IDBException {
		try {
			deleteIndex0(pName);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	private native int count0() throws JavaScriptException /*-{
		return this.count();
	}-*/;

	private native int count0(IDBKeyRange pRange) throws JavaScriptException /*-{
		return this.count(pRange);
	}-*/;

	public final int count() throws IDBException {
		try {
			return count0();
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	public final int count(IDBKeyRange pRange) throws IDBException {
		try {
			return count0(pRange);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
}