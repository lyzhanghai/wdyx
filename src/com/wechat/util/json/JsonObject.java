/**
 * Copyright (c) 2011-2015, Mobangjack 莫帮杰 (mobangjack@foxmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wechat.util.json;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * JsonObject.
 * @author 帮杰
 *
 */
public class JsonObject {

	Map<String, Object> map = null;
	
	public JsonObject() {
		map = new LinkedHashMap<String, Object>();
	}

	public JsonObject(Map<String,Object> map) {
		this.map = map;
	}
	
	/**
	 * put data to the JsonObject
	 * @param k
	 * @param v
	 * @return
	 */
	public JsonObject put(String k,Object v) {
		map.put(k, v);
		return this;
	}
	
	/**
	 * put data to the JsonObject
	 * @param map
	 * @return
	 */
	public JsonObject putAll(Map<String,Object> map) {
		this.map.putAll(map);
		return this;
	}
	
	/**
	 * remove data from the JsonObject
	 * @param k
	 * @return
	 */
	public JsonObject remove(String k) {
		map.remove(k);
		return this;
	}
	
	/**
	 * remove data from the JsonObject
	 * @param k
	 * @return
	 */
	public JsonObject remove(String k,Object v) {
		map.remove(k, v);
		return this;
	}
	
	/**
	 * replace old entry using the given one
	 * @param k
	 * @param v
	 * @return the previous value of the given key
	 */
	public Object replace(String k,Object v) {
		return map.replace(k, v);
	}
	
	/**
	 * replace old entry using the given one
	 * @param key
	 * @param oldValue
	 * @param newValue
	 * @return
	 */
	public Object replace(String key,Object oldValue,Object newValue) {
		return map.replace(key, oldValue, newValue);
	}
	
	/**
	 * if the JsonObject contains the given key
	 * @param k
	 * @return
	 */
	public boolean containsKey(String k) {
		return map.containsKey(k);
	}
	
	/**
	 *  if the JsonObject contains the given value
	 * @param v
	 * @return
	 */
	public boolean containsValue(Object v) {
		return map.containsValue(v);
	}
	
	/**
	 * if the JsonObject contains the given key value pair.
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean contains(String key,Object value) {
		Object o = map.get(key);
		return o==null?false:o.equals(value);
	}
	
	/**
	 * clear data from the JsonObject
	 * @return
	 */
	public JsonObject clear() {
		map.clear();
		return this;
	}
	
	/**
	 * size of the JsonObject
	 * @return
	 */
	public int size() {
		return map.size();
	}
	
	/**
	 * entrySet of this JsonObject
	 * @return
	 */
	public Set<Entry<String, Object>> entrySet() {
		return map.entrySet();
	}
	
	/**
	 * keySet of this JsonObject
	 * @return
	 */
	public Set<String> keySet() {
		return map.keySet();
	}
	
	/**
	 * values
	 * @return
	 */
	public Collection<Object> values() {
		return map.values();
	}
	
	/**
	 * get value by key
	 * @param k
	 * @return
	 */
	public Object get(String k) {
		return map.get(k);
	}
	
	/**
	 * get or default
	 * @param k
	 * @param defaultVal
	 * @return
	 */
	public Object get(String k,Object defaultVal) {
		Object e = get(k);
		return e==null?defaultVal:e;
	}
	
	/**
	 * get String by key
	 * @param k
	 * @return
	 */
	public String getString(String k) {
		Object e = get(k);
		if (e==null) {
			return null;
		} else {
			return e.toString();
		}
	}
	
	/**
	 * get or default
	 * @param k
	 * @param defaultVal
	 * @return
	 */
	public String getString(String k,String defaultVal) {
		String e = getString(k);
		return e==null?defaultVal:e;
	}
	
	/**
	 * get Integer by key
	 * @param k
	 * @return
	 */
	public Integer getInteger(String k) {
		return (Integer)get(k);
	}
	
	/**
	 * get or default
	 * @param k
	 * @param defaultVal
	 * @return
	 */
	public Integer getInteger(String k,Integer defaultVal) {
		Integer e = getInteger(k);
		return e==null?defaultVal:e;
	}
	
	/**
	 * get Long by key
	 * @param k
	 * @return
	 */
	public Long getLong(String k) {
		return (Long)get(k);
	}
	
	/**
	 * get or default
	 * @param k
	 * @param defaultVal
	 * @return
	 */
	public Long getLong(String k,Long defaultVal) {
		Long e = getLong(k);
		return e==null?defaultVal:e;
	}
	
	/**
	 * get Float by key
	 * @param k
	 * @return
	 */
	public Float getFloat(String k) {
		return (Float)get(k);
	}
	
	/**
	 * get or default
	 * @param k
	 * @param defaultVal
	 * @return
	 */
	public Float getFloat(String k,Float defaultVal) {
		Float e = getFloat(k);
		return e==null?defaultVal:e;
	}
	
	/**
	 * get Double by key
	 * @param k
	 * @return
	 */
	public Double getDouble(String k) {
		return (Double)get(k);
	}
	
	/**
	 * get or default
	 * @param k
	 * @param defaultVal
	 * @return
	 */
	public Double getDouble(String k,Double defaultVal) {
		Double e = getDouble(k);
		return e==null?defaultVal:e;
	}
	
	/**
	 * get Boolean by key
	 * @param k
	 * @return
	 */
	public Boolean getBoolean(String k) {
		return (Boolean)get(k);
	}
	
	/**
	 * get or default
	 * @param k
	 * @param defaultVal
	 * @return
	 */
	public Boolean getBoolean(String k,Boolean defaultVal) {
		Boolean e = getBoolean(k);
		return e==null?defaultVal:e;
	}
	
	/**
	 * get JsonObject by key
	 * @param k
	 * @return
	 */
	public JsonObject getJsonObject(String k) {
		Object o = get(k);
		if (o instanceof JsonObject) {
			return (JsonObject)o;
		} else {
			return JsonParser.parseJsonObject(o.toString());
		}
	}
	
	/**
	 * get or default
	 * @param k
	 * @param defaultVal
	 * @return
	 */
	public JsonObject getJsonObject(String k,JsonObject defaultVal) {
		JsonObject e = getJsonObject(k);
		return e==null?defaultVal:e;
	}
	
	/**
	 * get JsonArray by key
	 * @param k
	 * @return
	 */
	public JsonArray getJsonArray(String k) {
		Object o = get(k);
		if (o instanceof JsonArray) {
			return (JsonArray)o;
		} else {
			return JsonParser.parseJsonArray(o.toString());
		}
	}
	
	/**
	 * get or default
	 * @param k
	 * @param defaultVal
	 * @return
	 */
	public JsonArray getJsonArray(String k,JsonArray defaultVal) {
		JsonArray e = getJsonArray(k);
		return e==null?defaultVal:e;
	}
	
	/**
	 * output map data
	 * @return
	 */
	public Map<String, Object> toMap() {
		return map;
	}
	
	/**
	 * parse data from bean
	 * @param bean
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JsonObject fromBean(Object bean) {
		try {
			List<Field> fields = new ArrayList<Field>();
			Class<?> xClass = bean.getClass();
			Field[] fs;
			//find all fields
			while (xClass!=null) {
				fs = xClass.getDeclaredFields();
				if (fs!=null) {
					Collections.addAll(fields, fs);
				}
				xClass = xClass.getSuperclass();
			}
			for (Field field : fields) {
				field.setAccessible(true);
				//ignore transient or static field
				if (!(Modifier.isTransient(field.getModifiers())||Modifier.isStatic(field.getModifiers()))) {
					String key = field.getName();
					Object val = field.get(bean);
					//map
					if (val instanceof Map) {
						val = new JsonObject().putAll((Map)val);
					}
					//list
					else if (val instanceof List) {
						val = new JsonArray().addAll((List)val);
					}
					put(key, val);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}
	
	/**
	 * transfer data to bean
	 * @param bean
	 */
	public void toBean(Object bean) {
		try {
			List<Field> fields = new ArrayList<Field>();
			Class<?> xClass = bean.getClass();
			Field[] fs;
			//find all fields
			while (xClass!=null) {
				fs = xClass.getDeclaredFields();
				if (fs!=null) {
					Collections.addAll(fields, fs);
				}
				xClass = xClass.getSuperclass();
			}
			for (Field field : fields) {
				field.setAccessible(true);
				String key = field.getName();
				Object val = map.get(key);
				//ignore transient or static field
				if (val!=null&&!(Modifier.isTransient(field.getModifiers())||Modifier.isStatic(field.getModifiers()))) {
					if ((val instanceof JsonObject)&&field.getType()==Map.class) {
						val = ((JsonObject)val).toMap();
					}else if ((val instanceof JsonArray)&&field.getType()==List.class) {
						val = ((JsonArray)val).toList();
					}
					field.set(bean, val);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * output String format
	 */
	@Override
	public String toString() {
		if(map==null||map.isEmpty()){
			return "null";
		}
		StringBuilder sb = new StringBuilder();
		for(Map.Entry<String, Object> e:map.entrySet()){
			Object v = e.getValue();
			if(v instanceof String){
				sb.append("\"" + e.getKey() + "\":\""+escape(v.toString())+"\",");
			}else {
				sb.append("\"" + e.getKey() + "\":"+v+",");
			}
		}
		return "{"+sb.substring(0, sb.length()-1)+"}";
	}
	
	/**
	 * escape "，\，/ \b，\f，\n，\r，\t & 4 HEX digits
	 * @param s
	 * @return
	 */
	static String escape(String s) { 
	    StringBuilder sb = new StringBuilder(s.length()+20); 
	    for (int i=0; i<s.length(); i++) { 
	        char c = s.charAt(i); 
	        switch (c) { 
	        case '\"': 
	            sb.append("\\\""); 
	            break; 
	        case '\\': 
	            sb.append("\\\\"); 
	            break; 
	        case '/': 
	            sb.append("\\/"); 
	            break; 
	        case '\b': 
	            sb.append("\\b"); 
	            break; 
	        case '\f': 
	            sb.append("\\f"); 
	            break; 
	        case '\n': 
	            sb.append("\\n"); 
	            break; 
	        case '\r': 
	            sb.append("\\r"); 
	            break; 
	        case '\t': 
	            sb.append("\\t"); 
	            break; 
	        default: 
	        	if ((c >= '\u0000' && c <= '\u001F') || (c >= '\u007F' && c <= '\u009F') || (c >= '\u2000' && c <= '\u20FF')) {
					String str = Integer.toHexString(c);
					sb.append("\\u");
					for(int k=0; k<4-str.length(); k++) {
						sb.append('0');
					}
					sb.append(str.toUpperCase());
				}else {
					sb.append(c); 
				}
	        } 
	    } 
	    return sb.toString(); 
	 }
}
