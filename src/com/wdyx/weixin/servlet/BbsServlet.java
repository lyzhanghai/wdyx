package com.wdyx.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.mobangjack.common.util.StrUtil;
import org.mobangjack.wechat.api.AccessToken.AccessTokenMgr;
import org.mobangjack.wechat.api.UserMgr.UserMgrApi;

import com.wdyx.weixin.service.SimsimServiceUtil;
import com.wdyx.weixin.user.UserInfo;
import com.wdyx.weixin.web.admin.AdminUtil;
import com.wdyx.weixin.web.bbs.BbsUtil;
import com.wdyx.weixin.web.bbs.InterMsgBlock;

/**
 * Servlet implementation class BbsServlet
 */
@WebServlet("/BbsServlet")
public class BbsServlet extends HttpServlet {
	private static final long serialVersionUID = 112211225L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BbsServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		Integer count = StrUtil.toInt(request.getParameter("count"));
		Integer lastid = StrUtil.toInt(request.getParameter("lastId"));
		String ori = request.getParameter("ori");
		String openid = request.getParameter("openid");
		List<InterMsgBlock> msgs = BbsUtil.getInterMsgBlocks(count, lastid, ori, openid);
		PrintWriter out = response.getWriter();
		out.write(JSONArray.fromObject(msgs).toString());
		out.flush();
		out.close();
		out = null;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ path + "/MainServlet";
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		
		String operation = request.getParameter("operation");
		String openid = request.getParameter("openid");
		
		String json = "";
		if(operation.equals("add")){
			Integer msgid = StrUtil.toInt(request.getParameter("msgid"));
			String msg = request.getParameter("msg");
			String msgParentId = request.getParameter("msgParentId");
			String msgChildId = request.getParameter("msgChildId");
			String msgParent = request.getParameter("msgParent");
			String msgChild = request.getParameter("msgChild");
			UserInfo userInfo = UserInfo.getUserInfo(openid);
			int id = BbsUtil.writeBbs(openid, msg, msgid);
			if(id>0){
				String message = "";
				if(!msgid.equals(0)){
					if(!openid.equals(msgParentId)){
						message = "---------树洞消息---------\n您的树洞说说:\n“"
								+msgParent+"”\n"
								+ "有了 "+userInfo.getNickname()+" 的新评论:\n“"
								+msg+"”\n"
								+ "Tips:发送@+消息内容可直接回复\n"
								+ "<a href=\\\""+basePath+"?page=plugins\\/bbs.jsp&openid="+openid+"\\\">点我进入树洞<\\/a>";
						if(!UserMgrApi.sendMsgToUser(AccessTokenMgr.getAccessToken("wdyx").toString(),msgParentId, message)){
							System.out.println("Fail to send review msg to user 01");
						}
					}
					if(!openid.equals(msgChildId)&&!msgChildId.equals(msgParent)){
						message = "---------树洞消息---------\n"
								+ "您的树洞评论:\n“"
								+msgChild+"”\n"
								+ "有了 "+userInfo.getNickname()+" 的新回复:\n“"
								+msg+"”\n"
								+ "Tips:发送@+消息内容可直接回复\n"
								+ "<a href=\\\""+basePath+"?page=plugins\\/bbs.jsp&openid="+openid+"\\\">点我进入树洞<\\/a>";
						if(!UserMgrApi.sendMsgToUser(AccessTokenMgr.getAccessToken("wdyx").toString(),msgChildId, message)){
							System.out.println("Fail to send review msg to user 02");
						}
					}
				}else{
					id = BbsUtil.writeBbs("0000000000000000000000000000", SimsimServiceUtil.filteredResponse(msg), msgid);
					if(id>0){
						message = "---------树洞消息---------\n"
								+ "您的树洞说说:\n“"
								+msgChild+"”\n"
								+ "有了 "+"I-Robot"+" 的新回复:\n“"
								+msg+"”\n"
								+ "Tips:发送@+消息内容可直接回复\n"
								+ "<a href=\\\""+basePath+"?page=plugins\\/bbs.jsp&openid="+openid+"\\\">点我进入树洞<\\/a>";
						if(!UserMgrApi.sendMsgToUser(AccessTokenMgr.getAccessToken("wdyx").toString(),msgParentId, message)){
							System.out.println("Fail to send review msg to user 03");
						}
					}
				}
				json = "{\"headimgurl\":\""+userInfo.getHeadimgurl()+"\",\"spokesman\":\""+userInfo.getNickname()+"\"}";
			}
		}else if (operation.equals("delete")||operation.equals("forbid")) {
			if(AdminUtil.isAdmin(openid)){
				String id = request.getParameter("id");
				if (operation.equals("delete")){
					if(BbsUtil.deleteMsgById(Integer.parseInt(id))){
						json = "{\"state\":\"1\",\"msg\":\"操作成功！\"}";
					}else{
						json = "{\"state\":\"0\",\"msg\":\"操作失败！\"}";
					}
				}else if (operation.equals("forbid")){
					json = "{\"state\":\"0\",\"msg\":\"禁言功能暂不可用\"}";
				}
			}else{
				json = "{\"state\":\"0\",\"msg\":\"操作失败！您没有此项操作权限。\"}";
			}
		}
		
		PrintWriter out = response.getWriter();
		out.write(json);
		out.flush();
		out.close();
		out = null;
	}

}
