package org.mobangjack.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InputStreamUtil {
	
	public static byte[] readBytes(InputStream is) {
		BufferedInputStream bufin = new BufferedInputStream(is);  
        int buffSize = 1024;  
        ByteArrayOutputStream out = new ByteArrayOutputStream(buffSize);  
        byte[] temp = new byte[buffSize];  
        int size = 0;  
        try {
			while ((size = bufin.read(temp)) != -1) {  
			    out.write(temp, 0, size);  
			}
			bufin.close();  
		} catch (IOException e) {
			e.printStackTrace();
		}  
        return out.toByteArray();  
	}
	
	public static String readStr(InputStream is,String encoding){
		String str = null;
		try {
			InputStreamReader reader = new InputStreamReader(is,encoding);
			BufferedReader bufferedReader = new BufferedReader(reader);
			
			String line = null;
			StringBuffer buffer = new StringBuffer();
			while ((line = bufferedReader.readLine()) != null) {
				buffer.append(line);
			}
			str = buffer.toString();
			
			bufferedReader.close();
			reader.close();
			is.close();
			is = null;

		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return str;
	}

}
