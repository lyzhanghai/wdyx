package com.wdyx.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mobangjack.common.util.MysqlUtil;

import com.wdyx.weixin.web.ticket.Ticket;
import com.wdyx.weixin.web.ticket.TicketOwner;
import com.wdyx.weixin.web.ticket.TicketUtil;

/**
 * Servlet implementation class TicketServlet
 */
@WebServlet("/TicketServlet")
public class TicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TicketServlet() {
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
		response.setContentType("text/plain;charset=utf-8");
		String openid = request.getParameter("openid");
		String key = request.getParameter("key");
		Ticket ticket = TicketUtil.getTicket();
		TicketOwner owner = TicketUtil.getTicketOwner(openid);
		String msg = "检票系统暂不可用";
		if(owner==null){
			msg = "该用户当前没有票务信息";
		}else if(owner.getState().equals("已领票")) {
			msg = "该用户已领过票（序号："+owner.getId()+",票名："+owner.getTitle()+",票种："+owner.getType()+",领票时间："+owner.getTime()+"）";
		}else if(key.equals(ticket.getKey())){
			if(TicketUtil.changeOwnerState(openid, "已领票")){
				msg = "领票事件注册成功（序号："+owner.getId()+",票名："+owner.getTitle()+",票种："+owner.getType()+",领票时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"）";
			}else {
				msg = "领票事件注册失败,领票状态未改变。（序号："+owner.getId()+",票名："+owner.getTitle()+",票种："+owner.getType()+",领票状态："+owner.getState()+",抢票时间："+owner.getTime()+"）";
			}
			
		}else {
			msg = "密匙错误，领票状态未改变。（序号："+owner.getId()+",票名："+owner.getTitle()+",票种："+owner.getType()+",领票状态："+owner.getState()+",抢票时间："+owner.getTime()+"）";
		}
		PrintWriter out = response.getWriter();
		out.write(msg);
		out.flush();
		out.close();
		out = null;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/plain;charset=utf-8");
		String openid = request.getParameter("openid");
		Ticket ticket = TicketUtil.getTicket();
		TicketOwner owner = TicketUtil.getTicketOwner(openid);
		String msg = "抢票平台暂不可用";
		if (owner!=null) {
			msg = "您已经拥有"+owner.getTitle()+"的"+owner.getType()+"票。"+ticket.getRemark();
		}else{
			int count = MysqlUtil.getCount("TicketOwner", "title", ticket.getTitle());
			if(count<ticket.getVip()+ticket.getNormal()){
				if(count<ticket.getVip()){
					if(TicketUtil.signOwner(openid, "vip")){
						msg = "恭喜你抢到"+ticket.getTitle()+"的vip票。"+ticket.getRemark();
					}
				}else if(TicketUtil.signOwner(openid,"普通")){
					msg = "恭喜你抢到"+ticket.getTitle()+"的普通票。"+ticket.getRemark();
				}
			}else{
				msg = "啊哦，"+ticket.getTitle()+"的票已经被抢光了，下次记得早点来哦~";
			}
		}
		
		PrintWriter out = response.getWriter();
		out.write(msg);
		out.flush();
		out.close();
		out = null;
	}

}
