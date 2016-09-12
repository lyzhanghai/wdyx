package com.wdyx.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mobangjack.common.util.MysqlUtil;

/**
 * Servlet implementation class JoinUsServlet
 */
@WebServlet("/JoinUsServlet")
public class JoinUsServlet extends HttpServlet {
	private static final long serialVersionUID = 10010223L;
	private String openid = "";
	private String name = "";
	private String gender = "";
	private String grade = "";
	private String department = "";
	private String contact = "";
	private String experience = "";
	private String position = "";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JoinUsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/plain;charset=utf-8");
		openid = request.getParameter("openid");
		name = request.getParameter("name");
		gender = request.getParameter("gender");
		grade = request.getParameter("grade");
		department = request.getParameter("department");
		contact = request.getParameter("contact");
		experience = request.getParameter("experience");
		position = request.getParameter("position");
		String msg = null;
		if(joinus()){
			msg = "您的简历信息已提交，请您留意您的个人邮箱或社交网络，我们会择时与您联系。感谢您的关注！";
		}else{
			msg = "在线报名暂不可用，请与在武大艺协微信公众平台留言反馈。";
		}
		PrintWriter out = response.getWriter();
		out.write(msg);
		out.flush();
		out.close();
		out = null;
	}
	
	public boolean joinus() {
		String sql = "insert into joinus (openid,name,gender,grade,department,contact,experience,position) values "
				+ "('"+openid+"','"+name+"','"+gender+"','"+grade+"','"+department+"','"+contact+"','"+experience+"','"+position+"')";
		return MysqlUtil.update(sql);
	}

}
