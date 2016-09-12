package com.wdyx.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import com.wdyx.weixin.user.User;
import com.wdyx.weixin.web.admin.AdminUtil;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 100023456L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String openid = request.getParameter("openid");
		String msg = "";
		if(User.validate(openid)){
			if(AdminUtil.login(openid, username, password)){
				msg = "Admin Registered!";
			}else {
				msg = "DB_ERROR";
			}
		}else {
			openid = AdminUtil.login(username, password);
			if(openid!=""){
				msg = "Admin Login From None-WeChat Platform";
			}else {
				msg = "Please Registere Your openid as AdminID in WeChat First.";
			}
		}
		String json = "{\"openid\":\""+openid+"\",\"msg\":\""+msg+"\"}";
		PrintWriter out = response.getWriter();
		out.write(json);
		out.flush();
		out.close();
		out = null;
	}

}
