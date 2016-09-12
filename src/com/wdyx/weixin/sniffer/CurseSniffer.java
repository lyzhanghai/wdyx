package com.wdyx.weixin.sniffer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import org.mobangjack.common.util.MysqlUtil;

/**
 * 让我知道你有多坏
 * @author 帮杰
 *
 */
public class CurseSniffer {
	
	public static List<String> getCurseList () {
		String query = "select `curse` from `curse`";
		CachedRowSet crs = MysqlUtil.query(query);
		List<String> curseList = new ArrayList<String>();
		try {
			while(crs.next()){
				curseList.add(crs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return curseList;
	}

	public static boolean isCurse (String curse) {
		List<String> curseList = getCurseList();
		for(String s:curseList){
			if(curse.contains(s))
				return true;
		}
		return false;
	}

	
	public static boolean addCurse (String curse) {
		String sql = "insert into `curse` (curse) values ('"+curse+"')";
		return MysqlUtil.update(sql);
	}
	
	public static boolean deleteCurse (String curse) {
		String sql = "delete from `curse` where `curse`='"+curse+"'";
		return MysqlUtil.update(sql);
	}
	
	public static void main(String[] args) {
		//System.out.println(addCurse("插死"));
		//System.out.println(addCurse("插我"));
		//System.out.println(addCurse("做爱"));
		//System.out.println(deleteCurse("damn"));
		//System.out.println(isCurse("做爱"));
	}
}
