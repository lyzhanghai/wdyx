package com.wdyx.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wdyx.weixin.service.DigitalLibraryService;
import com.wdyx.weixin.service.EduInfoService;
import com.wdyx.weixin.service.InfoPortalService;
import com.wdyx.weixin.user.User;

/**
 * Servlet implementation class BindServlet
 */
@WebServlet("/BindServlet")
public class BindServlet extends HttpServlet {
	private static final long serialVersionUID = 11111111233L;
	private User user;
	private String msg = "绑定成功";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BindServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/plain;charset=utf-8");
		user = new User();
		user.setOpenid(request.getParameter("openid"));
		user.setUsername(request.getParameter("username"));
		user.setEISPassword(request.getParameter("EISPassword"));
		user.setIPSPassword(request.getParameter("IPSPassword"));
		user.setDLSPassword(request.getParameter("DLSPassword"));
		
		if(check()){ 
			if(!User.bindUser(user)){
				msg = "数据库操作失败，请将问题反馈给程序猿";
			}else {
				msg = "绑定成功";
			}
		}
		
		PrintWriter out = response.getWriter();
		out.write(msg);
		out.flush();
		out.close();
		out = null;
	}
	
	private boolean check(){
		boolean flag = true;
		
		for(int i=0;i<3;i++){
			if((new EduInfoService(user.getUsername(),user.getEISPassword()).login())){
				flag = true;
				break;
			}else{
				flag = false;
			}
		}
		if(!flag){
			msg = "教务密码错误";
			return false;
		}
		
		for(int i=0;i<3;i++){
			if((new InfoPortalService(user.getUsername(),user.getIPSPassword()).login())){
				flag = true;
				break;
			}else {
				flag = false;
			}
		}
		if(!flag){
			msg = "信息门户密码错误";
			return false;
		}
		
		for(int i=0;i<3;i++){
			if((new DigitalLibraryService(user.getUsername(),user.getDLSPassword()).login())){
				flag = true;
				break;
			}else{
				flag = false;
			}
		}
		if(!flag){
			msg = "图书馆密码错误";
		}
		
		return flag;
	}
}
