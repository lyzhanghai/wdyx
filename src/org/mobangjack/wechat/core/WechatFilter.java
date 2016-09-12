package org.mobangjack.wechat.core;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mobangjack.wechat.api.config.ThreadLocalApiConfig;
import org.mobangjack.wechat.util.SignUtil;

/**
 * 拦截并处理微信服务器发来的请求
 * @author 帮杰
 *
 */
public class WechatFilter implements Filter {

	private WechatConfig currentConfig;
	
    public WechatFilter() {}

	public void init(FilterConfig fConfig) throws ServletException {
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		String url = req.getRequestURI();
		currentConfig = WechatConfig.forUrl(url);
		if(currentConfig!=null){
			WechatConfig.setCurrent(currentConfig);
			req.setCharacterEncoding("UTF-8");
			res.setCharacterEncoding("UTF-8");
			res.setContentType("text/xml");
			if(req.getMethod().equalsIgnoreCase("GET"))
				doGet(req,res);
			else if(req.getMethod().equalsIgnoreCase("POST"))
				doPost(req,res);
			return;
		}

		chain.doFilter(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戮
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		// 通过检验 signature 对请求进行校验，若校验成功则原样返回 echostr，表示接入成功，否则接入失败
		if(SignUtil.checkSignature(currentConfig.getApiConfig().getToken(),signature, timestamp, nonce)){
			PrintWriter out = response.getWriter();
			out.write(echostr);
			out.flush();
			out.close();
			out = null;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ThreadLocalApiConfig.set(currentConfig.getApiConfig());
		String xml = currentConfig.getHandler().handle(request);
		ThreadLocalApiConfig.remove();
		PrintWriter out = response.getWriter();
		out.write(xml);
		out.flush();
		out.close();
		out = null;
	}
	
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		WechatConfig.removeCurrent();
	}
}
