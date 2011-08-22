package com.os.idb.client;

import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayMixed;

public class IDBKeyRange extends JavaScriptObject {
	private static native void init() /*-{
		$wnd.IDBKeyRange = $wnd.IDBKeyRange || $wnd.webkitIDBKeyRange;
	}-*/;
	
	static {
		init();
	}
	
	protected IDBKeyRange() {
	}
	
	public final native JsArrayMixed lower() /*-{
		return [this.lower];
	}-*/;
	
	public final native JsArrayMixed upper() /*-{
		return [this.upper];
	}-*/;
	
	public final native boolean lowerOpen() /*-{
		return this.lowerOpen;
	}-*/;
	
	public final native boolean upperOpen() /*-{
		return this.upperOpen;
	}-*/;

	public final static IDBKeyRange only(JavaScriptObject pKey) throws IDBException {
		try {
			return only0(pKey);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}

	public final static IDBKeyRange only(int pKey) throws IDBException {
		try {
			return only0(pKey);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}

	public final static IDBKeyRange only(String pKey) throws IDBException {
		try {
			return only0(pKey);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	private native static IDBKeyRange only0(JavaScriptObject pKey) throws JavaScriptException /*-{
		return new $wnd.IDBKeyRange.only(pKey);
	}-*/;

	private native static IDBKeyRange only0(int pKey) throws JavaScriptException /*-{
		return new $wnd.IDBKeyRange.only(pKey);
	}-*/;

	private native static IDBKeyRange only0(String pKey) throws JavaScriptException /*-{
		return new $wnd.IDBKeyRange.only(pKey);
	}-*/;

	public final static IDBKeyRange lowerBound(JavaScriptObject pKey) throws IDBException {
		try {
			return lowerBound0(pKey, false);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}

	public final static IDBKeyRange lowerBound(int pKey) throws IDBException {
		try {
			return lowerBound0(pKey, false);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}

	public final static IDBKeyRange lowerBound(String pKey) throws IDBException {
		try {
			return lowerBound0(pKey, false);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	public final static IDBKeyRange lowerBound(JavaScriptObject pKey,boolean pOpen) throws IDBException {
		try {
			return lowerBound0(pKey, pOpen);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}

	public final static IDBKeyRange lowerBound(int pKey,boolean pOpen) throws IDBException {
		try {
			return lowerBound0(pKey, pOpen);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	public final static IDBKeyRange lowerBound(String pKey,boolean pOpen) throws IDBException {
		try {
			return lowerBound0(pKey, pOpen);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	private native static IDBKeyRange lowerBound0(JavaScriptObject pKey,boolean pOpen) throws JavaScriptException /*-{
		return new $wnd.IDBKeyRange.lowerBound(pKey,pOpen);
	}-*/; 
	
	private native static IDBKeyRange lowerBound0(int pKey,boolean pOpen) throws JavaScriptException /*-{
		return new $wnd.IDBKeyRange.lowerBound(pKey,pOpen);
	}-*/; 

	private native static IDBKeyRange lowerBound0(String pKey,boolean pOpen) throws JavaScriptException /*-{
		return new $wnd.IDBKeyRange.lowerBound(pKey,pOpen);
	}-*/; 

	public final static IDBKeyRange upperBound(JavaScriptObject pKey) throws IDBException {
		try {
			return upperBound0(pKey, false);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}

	public final static IDBKeyRange upperBound(int pKey) throws IDBException {
		try {
			return upperBound0(pKey, false);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	public final static IDBKeyRange upperBound(String pKey) throws IDBException {
		try {
			return upperBound0(pKey, false);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}

	public final static IDBKeyRange upperBound(JavaScriptObject pKey,boolean pOpen) throws IDBException {
		try {
			return upperBound0(pKey, pOpen);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	public final static IDBKeyRange upperBound(int pKey,boolean pOpen) throws IDBException {
		try {
			return upperBound0(pKey, pOpen);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	public final static IDBKeyRange upperBound(String pKey,boolean pOpen) throws IDBException {
		try {
			return upperBound0(pKey, pOpen);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	private native static IDBKeyRange upperBound0(JavaScriptObject pKey,boolean pOpen) throws JavaScriptException /*-{
		return new $wnd.IDBKeyRange.upperBound(pKey,pOpen);
	}-*/; 

	private native static IDBKeyRange upperBound0(int pKey,boolean pOpen) throws JavaScriptException /*-{
		return new $wnd.IDBKeyRange.upperBound(pKey,pOpen);
	}-*/; 

	private native static IDBKeyRange upperBound0(String pKey,boolean pOpen) throws JavaScriptException /*-{
		return new $wnd.IDBKeyRange.upperBound(pKey,pOpen);
	}-*/; 

	public final static IDBKeyRange bound(JavaScriptObject pStartKey,JavaScriptObject pEndKey) throws IDBException {
		try {
			return bound0(pStartKey, pEndKey,false,false);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}

	public final static IDBKeyRange bound(int pStartKey,int pEndKey) throws IDBException {
		try {
			return bound0(pStartKey, pEndKey,false,false);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	public final static IDBKeyRange bound(String pStartKey,String pEndKey) throws IDBException {
		try {
			return bound0(pStartKey, pEndKey,false,false);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	
	public final static IDBKeyRange bound(JavaScriptObject pStartKey,JavaScriptObject pEndKey,boolean pStartOpen) throws IDBException {
		try {
			return bound0(pStartKey, pEndKey,pStartOpen,false);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	public final static IDBKeyRange bound(int pStartKey,int pEndKey,boolean pStartOpen) throws IDBException {
		try {
			return bound0(pStartKey, pEndKey,pStartOpen,false);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	public final static IDBKeyRange bound(String pStartKey,String pEndKey,boolean pStartOpen) throws IDBException {
		try {
			return bound0(pStartKey, pEndKey,pStartOpen,false);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	public final static IDBKeyRange bound(JavaScriptObject pStartKey,JavaScriptObject pEndKey,boolean pStartOpen,boolean pEndOpen) throws IDBException {
		try {
			return bound0(pStartKey, pEndKey,pStartOpen,pEndOpen);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	public final static IDBKeyRange bound(int pStartKey,int pEndKey,boolean pStartOpen,boolean pEndOpen) throws IDBException {
		try {
			return bound0(pStartKey, pEndKey,pStartOpen,pEndOpen);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	public final static IDBKeyRange bound(String pStartKey,String pEndKey,boolean pStartOpen,boolean pEndOpen) throws IDBException {
		try {
			return bound0(pStartKey, pEndKey,pStartOpen,pEndOpen);
		} catch(JavaScriptException jex) {
			throw new IDBException(jex);
		}
	}
	
	private native static IDBKeyRange bound0(JavaScriptObject pStartKey,JavaScriptObject pEndKey,boolean pStartOpen,boolean pEndOpen) throws JavaScriptException /*-{
		return new $wnd.IDBKeyRange.bound(pStartKey,pEndKey,pStartOpen,pEndOpen);
	}-*/; 

	private native static IDBKeyRange bound0(int pStartKey,int pEndKey,boolean pStartOpen,boolean pEndOpen) throws JavaScriptException /*-{
		return new $wnd.IDBKeyRange.bound(pStartKey,pEndKey,pStartOpen,pEndOpen);
	}-*/; 

	private native static IDBKeyRange bound0(String pStartKey,String pEndKey,boolean pStartOpen,boolean pEndOpen) throws JavaScriptException /*-{
		return new $wnd.IDBKeyRange.bound(pStartKey,pEndKey,pStartOpen,pEndOpen);
	}-*/; 
}