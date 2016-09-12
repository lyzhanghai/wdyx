package org.mobangjack.ocr.plugins;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.mobangjack.common.util.FileUtil;

/**
 * 本地库管理
 * @author 帮杰
 *
 */
public class LibMgr {

	protected transient final Logger logger = Logger.getLogger(this.getClass());
	
	File ICodeDir = null;
	File DBFile = null;
	
	public LibMgr(){
		this.DBFile = new File(Const.DB_FILE);
	}
	
	/**generate DB*/
	public void train(){
		//ensure the directory exists,delete the old DB
		if(!DBFile.exists()){
			DBFile.mkdirs();
			DBFile.delete();
		}else{
			DBFile.delete();
		}
		//prepare to write
		FileWriter fw = null;
		try {
			fw = new FileWriter(new File(Const.DB_FILE), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PrintWriter pw = new PrintWriter(fw);
		this.ICodeDir = new File(Const.ICODE_DIR);
		File[] files = ICodeDir.listFiles();
		for(File file : files){
			String name = file.getName();
			if(name.endsWith(".jpg")||name.endsWith(".png")){
				try {
					BufferedImage bi = ImageIO.read(file);
					List<String> codes = new OCRHelper(bi).Coder();
					int num = codes.size();
					if(num!=name.lastIndexOf('.')){
						System.out.println("file:'"+name+"' is hard to slice!Skiped");
						continue;
					}
					for(int i=0;i<num;i++){
						pw.println(name.charAt(i)+"="+codes.get(i));
						pw.flush();
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		//close stream
		try {
			fw.flush();
			pw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 整理一下，便于分析
	 */
	public void sort(){
		
		String s = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		BufferedReader reader = null;
        
        FileWriter fw = null;
		PrintWriter pw = null;
		
        try {
            File file = new File(DBFile.getParent()+File.separator+"db_sort.txt");
            if(file.exists()){
            	file.delete();
            }
            fw = new FileWriter(file,true);
    		pw = new PrintWriter(fw);
    		
            String tempString = null;
            
            for(int i=0;i<s.length();i++){
            	reader = new BufferedReader(new FileReader(DBFile));
            	while ((tempString = reader.readLine()) != null) {
            		if(tempString.startsWith(""+s.charAt(i))){
            			pw.println(tempString);
            			pw.flush();
            		}
	            }
            }
            fw.flush();
            pw.close();
            fw.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
	}
	
	/**
	 * 转换成List,方便比对
	 * @return
	 */
	public List<String> list(){
		List<String> list = new ArrayList<String>();
		String content = FileUtil.readFile(DBFile);
		String[] group = content.split("\r\n");
		for(int i=0;i<group.length;i++){
			list.add(group[i]);
		}
		return list;
	}
}
