package org.mobangjack.ocr.plugins;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * 自创蒙板法
 * 按匹配度根据生成的库来识别
 * @author 帮杰
 *
 */
public class OCR {

	public String recognize(BufferedImage image,List<String> libs){
		String result = "";
		List<String> codes = new OCRHelper(image).Coder();
		for(String code : codes){
			int MaxCount = 0;
			String target = "";
			for(String lib : libs){
				int count = 0;
				for(int i=0;i<5;i++){
					int tmp = 0;
					for(int j=0;j<code.length()-i;j++){
						if(code.charAt(j)=='1'&&lib.charAt(j+i)=='1'){
							tmp++;
						}
					}
					if(tmp>count){
						count = tmp;
					}
				}
				if(count>MaxCount){
					MaxCount = count;
					target = lib;
				}
			}
			result+=target.charAt(0);
		}
		return result;
	}
	
	public String ocr(BufferedImage image,List<String> libs){
		String result = "";
		List<String> codes = new OCRHelper(image).Coder();
		for(String code : codes){
			int MinCount = Const.STD_WIDTH*Const.STD_HEIGHT;
			String target = "";
			for(String lib : libs){
				int count = Const.STD_WIDTH*Const.STD_HEIGHT;
				for(int i=0;i<5;i++){
					int tmp = 0;
					for(int j=0;j<code.length()-i;j++){
						char sample = code.charAt(j);
						char model = lib.charAt(j+i);
						if((sample=='1'&&model=='0')||(sample=='0'&&model=='1')){
							tmp++;
						}
					}
					if(tmp<count){
						count = tmp;
					}
				}
				if(count<MinCount){
					MinCount = count;
					target = lib;
				}
			}
			result+=target.charAt(0);
		}
		return result;
	}
	
	
}
