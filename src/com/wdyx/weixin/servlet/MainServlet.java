package com.wdyx.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import com.wdyx.weixin.web.admin.AdminUtil;
import com.wdyx.weixin.web.main.WebUtil;
import com.wdyx.weixin.web.main.WebView;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1010101022L;
    
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		Map<String, String[]> params = request.getParameterMap();
		Set<String> keySet = params.keySet();
		HttpSession session = request.getSession();
		for(String key:keySet){
			session.setAttribute(key, params.get(key)[0]);
		}
		String page = "";
		try {
			page = params.get("page")[0];
		} catch (Exception e) {
			page = "main/main.jsp";
			session.setAttribute("page",page);
		}
		String openid = params.get("openid")==null?null:params.get("openid")[0];
		if(AdminUtil.isAdmin(openid)){
			session.setAttribute("uid", "admin");
		}else {
			session.setAttribute("uid", "user");
		}
		response.sendRedirect(page);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		HttpSession session = request.getSession();
		String type = (String)session.getAttribute("type");
		String id = (String)session.getAttribute("id");
		WebView webView = WebUtil.getWebView(type, id);
		PrintWriter out = response.getWriter();
		out.write(JSONObject.fromObject(webView).toString());
		out.flush();
		out.close();
		out = null;
	}

}
