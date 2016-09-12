package com.wdyx.weixin.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wdyx.weixin.user.CommonUser;

@WebFilter({"/main/vote.jsp","/VoteServlet","/plugins/bbs.jsp","/BbsServlet","/plugins/bind.jsp","/plugins/ticket.jsp","/TicketServlet","/plugins/verifyOwner.jsp"})
public class UserFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		HttpSession session = req.getSession();
		
		String openid = (String)session.getAttribute("openid");
		String info = "请从武大艺协微信公众平台访问此页面";
		if(!CommonUser.validate(openid)){
			session.setAttribute("info", info);
			res.sendRedirect("/error.jsp");
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
