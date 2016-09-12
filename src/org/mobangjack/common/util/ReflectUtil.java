package org.mobangjack.common.util;

import java.lang.reflect.Field;

public class ReflectUtil {

	public static Class<?> cloneClass(Class<?> clazz){
		Class<?> newClazz = null;
		try {
			newClazz = Class.forName(clazz.getName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return newClazz;
	}
	
	public static Field getFieldByName(Class<?> modelClass,String name){
		Class<?> clazz = cloneClass(modelClass);
		Field field = null;
		for(int i=0;i<100;i++){
			try {
				field = clazz.getDeclaredField(name);
			} catch (NoSuchFieldException e) {
				clazz = clazz.getSuperclass();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			if(field!=null)break;
		}
		return field;
	}
	
	public static Object getFieldVal(Object object,String name){
		Object val = null;
		try {
			Field field = getFieldByName(object.getClass(),name);
			field.setAccessible(true);
			val = field.get(object);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return val;
	}
	
	public static void setFieldVal(Object object,String name,Object val){
		Field field = getFieldByName(object.getClass(),name);
		field.setAccessible(true);
		try {
			field.set(object, val);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
