package org.mobangjack.db;

import java.util.HashMap;
import java.util.Map;

/**
 * Bundle for restoring data.
 * @author 帮杰
 *
 */
public class Bundle {
	
	private Map<String, Object> bundle = new HashMap<String, Object>();
	
	public Object getAttr(String key){
		return bundle.get(key);
	}
	
	public void setAttr(String key,Object value){
		bundle.put(key, value);
	}
	
	public Object removeAttr(String key){
		return bundle.remove(key);
	}
	
	public void clear(){
		bundle.clear();
	}
	
	public boolean isEmpty(){
		return bundle.isEmpty();
	}
	
	public boolean containsAttr(String key){
		return bundle.containsKey(key);
	}
	
	public boolean containsValue(Object value){
		return bundle.containsValue(value);
	}
}