package com.wdyx.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wdyx.weixin.web.activity.Activity;
import com.wdyx.weixin.web.activity.ActivityUtil;

/**
 * Servlet implementation class ActivityServlet
 */
@WebServlet("/ActivityServlet")
public class ActivityServlet extends HttpServlet {
	private static final long serialVersionUID = 10000035L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActivityServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/plain;charset=utf-8");
		Activity activity = new Activity();
		activity.setOpenid(request.getParameter("openid"));
		activity.setTitle(request.getParameter("title"));
		activity.setName(request.getParameter("name"));
		activity.setGender(request.getParameter("gender"));
		activity.setEmail(request.getParameter("email"));
		activity.setDepartment(request.getParameter("department"));
		activity.setGrade(request.getParameter("grade"));
		activity.setContact(request.getParameter("contact"));
		activity.setRemark(request.getParameter("remark"));
		String msg = null;
		if(ActivityUtil.rollup(activity)){
			msg = "您的报名信息已提交，请您留意您的个人邮箱或社交网络，我们会择时与您联系。感谢您的参与！";
		}else{
			msg = "在线报名暂不可用，请与在武大艺协微信公众平台留言反馈。";
		}
		PrintWriter out = response.getWriter();
		out.write(msg);
		out.flush();
		out.close();
		out = null;
	}

}
