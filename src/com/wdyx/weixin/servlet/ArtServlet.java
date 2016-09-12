package com.wdyx.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.wdyx.weixin.web.art.Art;
import com.wdyx.weixin.web.art.ArtUtil;

/**
 * Servlet implementation class ArtServlet
 */
@WebServlet("/ArtServlet")
public class ArtServlet extends HttpServlet {
	private static final long serialVersionUID = 110112119L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ArtServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		String lastid = request.getParameter("lastid");
		Art art = ArtUtil.getArt(lastid);
		PrintWriter out = response.getWriter();
		out.write(JSONObject.fromObject(art).toString());
		out.flush();
		out.close();
		out = null;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/plain;charset=utf-8");
		String openid = request.getParameter("openid");
		String title = request.getParameter("title");
		String review = request.getParameter("review");
		String artid = request.getParameter("artid");
		String msg = "评论功能暂时关闭";
		if(ArtUtil.reviewArt(openid, title, review, artid)){
			msg = "评论成功";
		}
		PrintWriter out = response.getWriter();
		out.write(msg);
		out.flush();
		out.close();
		out = null;
	}

}
