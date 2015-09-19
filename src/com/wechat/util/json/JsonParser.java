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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JsonParser parses JsonObject or JsonArray from JsonObject String.
 * @author 帮杰
 *
 */
public class JsonParser {

	public static final String PATERN = "(\"\\w+\"):(\\{.*\\}|\\[.*\\]|null|\"[^\"]+\"|true|false|\\d+|\\d+\\.?\\d+)";
	
	/**
	 * parse JsonObject from the given String
	 */
	public static JsonObject parseJsonObject(String json) {
		
		if(json==null){
			throw new IllegalArgumentException("The given JsonObject String cannot be null.");
		}
		
		//remove the blanks in the head or tail
		json = trim(json);
        if(!isJsonObject(json)){
        	System.err.println("The given JsonObject String '"+json+"' is not valid!");
        	return null;
        }
        
        JsonObject jsonObject = new JsonObject();
        
        Pattern p = Pattern.compile(PATERN);
        Matcher m = p.matcher(json);
        while(m.find()) {
        	String group = m.group();
        	//unquote key
        	String k = unquot(group.substring(0, group.indexOf(':')));
        	//parse value
        	Object v = parse(group.substring(group.indexOf(':')+1));
        	//put it to jsObj
    		jsonObject.put(k, v);
        }
        
		return jsonObject;
	}
	
	/**
	 * parse JsonArray from the given String s
	 */
	public static JsonArray parseJsonArray(String json) {
		
		if(json==null){
			throw new IllegalArgumentException("The given JsonObject-array String cannot be null.");
		}
		
		//remove the blanks in the head or tail
		json = trim(json);
		
        if(!isJsonArray(json)){
        	System.err.println("The given JsonObject-array String '"+json+"' is not valid!");
        	return null;
        }
        
        JsonArray jsonArray = new JsonArray();
        
        String s;
        Object e;
        
        if(json.indexOf(',')<0) {
        	s = json.substring(1, json.length()-1);
        	e = parse(s);
        	//validate
    		if(s!=e){
    			jsonArray.add(e);
    		}
        	return jsonArray;
        }
        
        int i1 = 1;
        int i2 = json.indexOf(',');
        
        while (i1<i2) {
        	s = json.substring(i1, i2);
    		e = parse(s);
    		//validate
    		if(s!=e){
    			jsonArray.add(e);
    			i1 = i2+1;
    		}
        	i2 = json.indexOf(',', i2+1);
		}
        //last element
        s = json.substring(i1, json.length()-1);
		e = parse(s);
		//validate
		if(s!=e){
			jsonArray.add(e);
		}
		return jsonArray;
	}

	/**
	 * parse JsonObject value to Java Object.
	 * @param s
	 * @return Primary Java type,JsonObject or JsonArray
	 */
	public static Object parse(String s) {
		
		if(s==null){
			throw new IllegalArgumentException("The given JsonObject value cannot be null.");
		}
		
		//null
		if (isNull(s)) {
			return null;
		}
		
		//String
		if (isString(s)){
			//trim
			s = trim(s);
			//unquote
			s = unquot(s);
			//unescape
			s = unescape(s);
			return s;
		}
		
		//Boolean
		if (isBool(s)) {
			return Boolean.parseBoolean(s);
		}
		
		//Integer
		try {return Integer.parseInt(s);} catch (Exception ex) {}
		
		//Long
		try {return Long.parseLong(s);} catch (Exception ex) {}
		
		//Float
		try {return Float.parseFloat(s);} catch (Exception ex) {}
		
		//Double
		try {return Double.parseDouble(s);} catch (Exception ex) {}
		
		//JsonArray
		if (isJsonArray(s)) {
			return parseJsonArray(s);
		}
				
		//JsonObject
		if (isJsonObject(s)) {
			return parseJsonObject(s);
		}
		
		//treat it as a String if the type is not supported
		return s;

	}
	
	/**
	 * if the give String represents a null value
	 * @param s
	 * @return
	 */
	public static boolean isNull(String s) {
		if(s==null) {
			return false;
		}
		s = trim(s);
		return s.equalsIgnoreCase("null");
	}
	
	/**
	 * if the give String represents a String value
	 * @param s
	 * @return
	 */
	public static boolean isString(String s) {
		if(s==null) {
			return false;
		}
		s = trim(s);
		return s.startsWith("\"")&&s.endsWith("\"");
	}
	
	/**
	 * if the give String represents a Boolean value
	 * @param s
	 * @return
	 */
	public static boolean isBool(String s) {
		if(s==null) {
			return false;
		}
		s = trim(s);
		return s.equalsIgnoreCase("true")|s.equalsIgnoreCase("false");
	}
	
	/**
	 * if the given String represents an Integer value
	 * @param s
	 * @return
	 */
	public static boolean isInt(String s) {
		s = trim(s);
		try {
			Integer.parseInt(s);
			return true;
		} catch (Exception ex) {
			return false;
		}
		
	}
	
	/**
	 * if the given String represents a Long value
	 * @param s
	 * @return
	 */
	public static boolean isLong(String s) {
		s = trim(s);
		try {
			Long.parseLong(s);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	/**
	 * if the given String represents a Float value
	 * @param s
	 * @return
	 */
	public static boolean isFloat(String s) {
		s = trim(s);
		try {
			Float.parseFloat(s);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	/**
	 * if the given String represents a Double value
	 * @param s
	 * @return
	 */
	public static boolean isDouble(String s) {
		s = trim(s);
		try {
			Double.parseDouble(s);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
	
	/**
	 * if the given String represents a JsonObject
	 * @param s
	 * @return
	 */
	public static boolean isJsonObject(String s) {
		if(s==null) {
			return false;
		}
		s = trim(s);
		Pattern p = Pattern.compile("^\\{.*\\}$");
        Matcher m = p.matcher(s);
        return m.matches();
	}
	
	/**
	 * if the given String represents a JsonArray
	 * @param s
	 * @return
	 */
	public static boolean isJsonArray(String s) {
		if(s==null) {
			return false;
		}
		s = trim(s);
		Pattern p = Pattern.compile("^\\[.*\\]$");
        Matcher m = p.matcher(s);
        return m.matches();
	}

	/**
	 * remove the blanks in the head or tail
	 * @param s
	 * @return
	 */
	static String trim(String s) {
		if(s==null||s.equals("")){
			return s;
		}else {
			if(s.charAt(0)==' '){
				int i;
				for(i=0;i<s.length();i++){
					if(s.charAt(i)!=' ')
						break;
				}
				s = s.substring(i).trim();
			}else {
				s = s.trim();
			}
			return s;
		}
		
	}
	
	/**
	 * unquote String("s"->s)
	 * @param s
	 * @return
	 */
	static String unquot(String s) {
		if(s==null||s.trim().equals("")){
			return s;
		}else {
			s = trim(s);
			if(s.startsWith("\"")&&s.endsWith("\"")){
				s = s.substring(1, s.lastIndexOf("\""));
			}
			return s;
		}
	}
	
	/**
	 * unescape "，\，/ \b，\f，\n，\r，\t . etc.
	 * @param s
	 * @return
	 */
	public static String unescape(String s) { 
		if(s==null||!s.contains("\\")){
			return s;
		}
		if (s.contains("\\\"")) {
			s = s.replaceAll("\\\"", "\"");
		}
		if (s.contains("\\\\")) {
			s = s.replaceAll("\\\\", "\\");
		}
		if (s.contains("\\/")) {
			s = s.replaceAll("\\/", "/");
		}
		if (s.contains("\\b")) {
			s = s.replaceAll("\\b", "\b");
		}
		if (s.contains("\\f")) {
			s = s.replaceAll("\\f", "\f");
		}
		if (s.contains("\\n")) {
			s = s.replaceAll("\\n", "\n");
		}
		if (s.contains("\\r")) {
			s = s.replaceAll("\\r", "\r");
		}
		if (s.contains("\\t")) {
			s = s.replaceAll("\\t", "\t");
		}
		return s;
	}
	
}
