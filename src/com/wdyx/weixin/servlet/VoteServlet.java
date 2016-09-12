package com.wdyx.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wdyx.weixin.web.vote.VoteUtil;

/**
 * Servlet implementation class VoteServlet
 */
@WebServlet("/VoteServlet")
public class VoteServlet extends HttpServlet {
	private static final long serialVersionUID = 10010L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VoteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		String openid = request.getParameter("openid");
		String candidate = request.getParameter("candidate");
		String state = "0";
		String msg = "投票功能暂不可用";
		List<String> candidates = VoteUtil.getCandidate(openid);
		if(candidates.isEmpty()){
			if(VoteUtil.vote(openid, candidate)) {
				state = "1";
				msg = "投票成功！您还可以给2个选手投票";
			}
		}else{
			if(candidates.size()>2){
				state = "0";
				msg = "一人只能投三票，您的票权已用完";
			}else if(candidates.contains(candidate)){
				state = "0";
				msg = "您已经给"+candidate+"投过票";
			}else if(VoteUtil.vote(openid, candidate)) {
				state = "1";
				msg = "投票成功！您还可以给"+(2-candidates.size())+"个选手投票";
			}
		}
		
		String json = "{\"state\":\""+state+"\",\"msg\":\""+msg+"\"}";
		PrintWriter out = response.getWriter();
		out.write(json);
		out.flush();
		out.close();
		out = null;
	}

}
