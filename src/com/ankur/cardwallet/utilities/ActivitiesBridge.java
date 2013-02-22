package com.ankur.cardwallet.utilities;

public class ActivitiesBridge {
	
	private static Object object;
	
	public static void setObject(Object obj){
		object = obj;
	}
	
	public static Object getObject() {
		Object obj = object;
		// can get only once
		object = null;
		return obj;
	}

}
