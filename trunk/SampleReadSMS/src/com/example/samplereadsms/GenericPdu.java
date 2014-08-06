package com.example.samplereadsms;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GenericPdu {
	protected Object mGenericPdu;
	
	public GenericPdu(Object obj) {
		mGenericPdu = obj;
	}
	
	public int getMessageType() {
		try {
			Method m = mGenericPdu.getClass().getMethod("getMessagetype");
			return (int) m.invoke(mGenericPdu);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
}
