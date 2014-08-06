package com.example.samplereadsms;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PduParser {
	
	Object pduParser;
	Class pduParserClass;
	public PduParser(byte[] data) {
		try {
			pduParserClass = Class.forName("com.google.android.mms.pdu.PduParser");
			Constructor c = pduParserClass.getConstructor(byte[].class);
			pduParser = c.newInstance(data);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
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
	}
	
	protected PduHeaders parseHeaders(ByteArrayInputStream pduDataStream){
		 if (pduParser != null) {
			 try {
				Method m = pduParserClass.getMethod("parseHeaders", ByteArrayInputStream.class);
				Object obj = m.invoke(pduParser, pduDataStream);
				return new PduHeaders(obj);
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
			 
		 }
		 return null;
	}
}