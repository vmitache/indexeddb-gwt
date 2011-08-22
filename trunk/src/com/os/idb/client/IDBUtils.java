package com.os.idb.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.core.client.JsArrayString;

public class IDBUtils {
  public static void fireCallback(IDBCallback pCallback,IDBEvent pEvent) {
    if (pCallback == null) {
      return;
    }
    UncaughtExceptionHandler ueh = GWT.getUncaughtExceptionHandler();
    if (ueh != null) {
      try {
        pCallback.onEvent(pEvent);
      } catch (Throwable e) {
        ueh.onUncaughtException(e);
      }
    } else {
      pCallback.onEvent(pEvent);
    }
  }

  public static void fireCallback(IDBCursorCallback pCallback) {
    if (pCallback == null) {
      return;
    }
    UncaughtExceptionHandler ueh = GWT.getUncaughtExceptionHandler();
    if (ueh != null) {
      try {
        pCallback.onDone();
      } catch (Throwable e) {
        ueh.onUncaughtException(e);
      }
    } else {
      pCallback.onDone();
    }
  }

  public static void fireCallback(IDBCursorCallback pCallback,IDBEvent pEvent) {
    if (pCallback == null) {
      return;
    }
    UncaughtExceptionHandler ueh = GWT.getUncaughtExceptionHandler();
    if (ueh != null) {
      try {
        pCallback.onError(pEvent);
      } catch (Throwable e) {
        ueh.onUncaughtException(e);
      }
    } else {
      pCallback.onError(pEvent);
    }
  }

  public static void fireCallback(IDBCursorCallback pCallback,IDBKeyValueRecord pRecord) {
    if (pCallback == null) {
      return;
    }
    UncaughtExceptionHandler ueh = GWT.getUncaughtExceptionHandler();
    if (ueh != null) {
      try {
        pCallback.onKeyValue(pRecord);
      } catch (Throwable e) {
        ueh.onUncaughtException(e);
      }
    } else {
      pCallback.onKeyValue(pRecord);
    }
  }
  
  public static List<String> toList(JsArrayString pJSArray) {
  	List<String> result = new ArrayList<String>(pJSArray.length());
  	for(int i=0;i < pJSArray.length();i++) {
  		result.add(pJSArray.get(i));
  	}
  	return result;
  }
  
  public static JsArrayString toJsArray(List<String> pList) {
		JsArrayString js = JsArrayString.createArray().cast();
		for(int i=0;i < pList.size();i++) {
			js.set(i, pList.get(i));
		}
		return js;
  }
  
  public static JsArrayString toJsArray(String[] pArgs) {
		JsArrayString js = JsArrayString.createArray().cast();
		for(int i=0;i < pArgs.length;i++) {
			js.set(i, pArgs[i]);
		}
		return js;
  }
  
}
