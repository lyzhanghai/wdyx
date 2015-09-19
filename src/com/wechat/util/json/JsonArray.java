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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * JsonArray
 * @author 帮杰
 *
 */
public class JsonArray {

	private List<Object> list = null;
	
	public JsonArray(){
		list = new ArrayList<Object>();
	}
	
	public JsonArray(List<Object> list){
		this.list = list;
	}
	
	/**
	 * add element
	 * @param e
	 * @return JsonArray
	 */
	public JsonArray add(Object e) {
		list.add(e);
		return this;
	}
	
	/**
	 * add elements
	 * @param objects
	 * @return
	 */
	public JsonArray addAll(Object...elements) {
		Collections.addAll(list, elements);
		return this;
	}
	
	/**
	 * add elements
	 * @param list
	 * @return JsonArray
	 */
	@SuppressWarnings({ "rawtypes" })
	public JsonArray addAll(List list) {
		for(Object e:list){
			this.list.add(e);
		}
		return this;
	}
	
	/**
	 * add element in the specific index
	 * @param i
	 * @param e
	 * @return JsonArray
	 */
	public JsonArray add(int i,Object e) {
		list.add(i, e);
		return this;
	}
	
	/**
	 * remove element
	 * @param e
	 * @return JsonArray
	 */
	public JsonArray remove(Object e) {
		list.remove(e);
		return this;
	}
	
	/**
	 * remove element
	 * @param i
	 * @return JsonArray
	 */
	public JsonArray remove(int i) {
		list.remove(i);
		return this;
	}
	
	/**
	 * clear JsonArray
	 * @return
	 */
	public JsonArray clear() {
		list.clear();
		return this;
	}
	
	/**
	 * get element by index
	 * @param i
	 * @return Object
	 */
	public Object get(int i) {
		return list.get(i);
	}
	
	/**
	 * get or default
	 * @param i
	 * @param defaultVal
	 * @return
	 */
	public Object get(int i,Object defaultVal) {
		Object e = list.get(i);
		return e==null?defaultVal:e;
	}
	
	/**
	 * get String element by index
	 * @param i
	 * @return
	 */
	public String getString(int i) {
		Object o = get(i);
		if (o==null) {
			return null;
		} else {
			return o.toString();
		}
	}
	
	/**
	 * get or default
	 * @param i
	 * @param defaultVal
	 * @return
	 */
	public String getString(int i,String defaultVal) {
		String s = getString(i);
		return s==null?defaultVal:s;
	}
	
	/**
	 * get Integer element by index
	 * @param i
	 * @return
	 */
	public Integer getInteger(int i) {
		return (Integer)get(i);
	}
	
	/**
	 * get or default
	 * @param i
	 * @param defaultVal
	 * @return
	 */
	public Integer getInteger(int i,Integer defaultVal) {
		Integer e = getInteger(i);
		return e==null?defaultVal:e;
	}
	
	/**
	 * get Long element by index
	 * @param i
	 * @return
	 */
	public Long getLong(int i) {
		return (Long)get(i);
	}
	
	/**
	 * get or default
	 * @param i
	 * @param defaultVal
	 * @return
	 */
	public Long getLong(int i,Long defaultVal) {
		Long e = getLong(i);
		return e==null?defaultVal:e;
	}
	
	/**
	 * get Float element by index
	 * @param i
	 * @return
	 */
	public Float getFloat(int i) {
		return (Float)get(i);
	}
	
	/**
	 * get or default
	 * @param i
	 * @param defaultVal
	 * @return
	 */
	public Float getFloat(int i,Float defaultVal) {
		Float e = getFloat(i);
		return e==null?defaultVal:e;
	}
	
	/**
	 * get Double element by index
	 * @param i
	 * @return
	 */
	public Double getDouble(int i) {
		return (Double)get(i);
	}
	
	/**
	 * get or default
	 * @param i
	 * @param defaultVal
	 * @return
	 */
	public Double getDouble(int i,Double defaultVal) {
		Double e = getDouble(i);
		return e==null?defaultVal:e;
	}
	
	/**
	 * get Boolean element by index
	 * @param i
	 * @return
	 */
	public Boolean getBoolean(int i) {
		return (Boolean)get(i);
	}
	
	/**
	 * get or default
	 * @param i
	 * @param defaultVal
	 * @return
	 */
	public Boolean getBoolean(int i,Boolean defaultVal) {
		Boolean e = getBoolean(i);
		return e==null?defaultVal:e;
	}
	
	/**
	 * get JsonObject element by index
	 * @param i
	 * @return
	 */
	public JsonObject getJsonObject(int i) {
		return (JsonObject)get(i);
	}
	
	/**
	 * get or default
	 * @param i
	 * @param defaultVal
	 * @return
	 */
	public JsonObject getJsonObject(int i,JsonObject defaultVal) {
		JsonObject e = getJsonObject(i);
		return e==null?defaultVal:e;
	}
	
	/**
	 * get getJsonArray element by index
	 * @param i
	 * @return
	 */
	public JsonArray getJsonArray(int i) {
		return (JsonArray)get(i);
	}
	
	/**
	 * get or default
	 * @param i
	 * @param defaultVal
	 * @return
	 */
	public JsonArray getJsonArray(int i,JsonArray defaultVal) {
		JsonArray e = getJsonArray(i);
		return e==null?defaultVal:e;
	}
	
	/**
	 * list elements
	 * @return a list of Objects
	 */
	public List<Object> toList() {
		return list;
	}
	
	/**
	 * transfer data to the list given type
	 * @param list
	 */
	@SuppressWarnings("unchecked")
	public <T> void toList(List<T> list) {
		for (Object o : this.list) {
			list.add((T)o);
		}
	}
	
	/**
	 * to array
	 * @param array
	 */
	public <T> void toArray(T[] array) {
		list.toArray(array);
	}
	
	/**
	 * to array
	 * @return
	 */
	public Object[] toArray() {
		return list.toArray();
	}
	
	/**
	 * Returns the index of the first occurrence of the specified element in this list, or -1 if this list does not contain the element. More formally, returns the lowest index i such that (o==null ? get(i)==null : o.equals(get(i))), or -1 if there is no such index.
	 * @param e
	 * @return
	 */
	public int indexOf(Object e) {
		return list.indexOf(e);
	}
	
	/**
	 * Returns the index of the first occurrence of the specified element in this list, or -1 if this list does not contain the element. More formally, returns the lowest index i such that (o==null ? get(i)==null : o.equals(get(i))), or -1 if there is no such index.
	 * @param e
	 * @return
	 */
	public int lastIndexOf(Object e) {
		return list.lastIndexOf(e);
	}
	
	/**
	 * Returns the number of elements in this list. If this list contains more than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE.
	 * @return
	 */
	public int size() {
		return list.size();
	}
	
	/**
	 * if the JsonArray is empty
	 * @return true if the JsonObject array is empty.
	 */
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	/**
	 * if the JsonArray contains the given value
	 * @param o
	 * @return
	 */
	public boolean contains(Object e) {
		return list.contains(e);
	}
	
	/**
	 * to JsonArray String
	 */
	@Override
	public String toString() {
		if(list==null||list.isEmpty()){
			return "null";
		}else {
			StringBuilder sb = new StringBuilder();
			for(Object e:list){
				if(e instanceof String){
					sb.append("\""+JsonObject.escape(e.toString())+"\",");
				}else {
					sb.append(e+",");
				}
			}
			return "["+sb.substring(0, sb.length()-1)+"]";
		}
	}

}
