package com.wdyx.weixin.web.activity;


import org.mobangjack.common.util.MysqlUtil;

public class ActivityUtil {

	public static boolean rollup(Activity activity) {
		String title = activity.getTitle();
		String openid = activity.getOpenid();
		String name = activity.getName();
		String gender = activity.getGender();
		String email = activity.getEmail();
		String department = activity.getDepartment();
		String grade = activity.getGrade();
		String contact = activity.getContact();
		String remark = activity.getRemark();
		String sql = "insert into activity (title,openid,name,gender,email,department,grade,contact,remark) values "
				+ "('"+title+"','"+openid+"','"+name+"','"+gender+"','"+email+"','"+department+"','"+grade+"','"+contact+"','"+remark+"')";
		return MysqlUtil.update(sql);
	}

}
