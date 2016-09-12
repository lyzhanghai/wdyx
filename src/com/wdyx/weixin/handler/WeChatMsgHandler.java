package com.wdyx.weixin.handler;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.sql.rowset.CachedRowSet;

import org.mobangjack.common.util.DateUtil;
import org.mobangjack.common.util.MysqlUtil;
import org.mobangjack.wechat.api.AccessToken.AccessTokenMgr;
import org.mobangjack.wechat.api.UserMgr.UserMgrApi;
import org.mobangjack.wechat.core.WechatRequestHandlerImpl;
import org.mobangjack.wechat.ext.Const;
import org.mobangjack.wechat.message.handler.ToArticles;
import org.mobangjack.wechat.message.handler.ToCommonText;
import org.mobangjack.wechat.message.handler.ToMusic;
import org.mobangjack.wechat.message.request.EventMsg;
import org.mobangjack.wechat.message.request.TextMsg;
import org.mobangjack.wechat.message.request.VoiceMsg;
import org.mobangjack.wechat.message.response.Article;
import org.mobangjack.wechat.message.response.Music;

import com.wdyx.weixin.service.BaiduMusicService;
import com.wdyx.weixin.service.DigitalLibraryService;
import com.wdyx.weixin.service.DigitalLibraryServiceUtil;
import com.wdyx.weixin.service.EduInfoServiceUtil;
import com.wdyx.weixin.service.InfoPortalService;
import com.wdyx.weixin.service.MeiCaoMovieServiceUtil;
import com.wdyx.weixin.service.SimsimServiceUtil;
import com.wdyx.weixin.service.TodayInHistoryServiceUtil;
import com.wdyx.weixin.user.User;
import com.wdyx.weixin.web.admin.AdminUtil;
import com.wdyx.weixin.web.bbs.BbsUtil;
import com.wdyx.weixin.web.gift.Gift;
import com.wdyx.weixin.web.gift.GiftUtil;
import com.wdyx.weixin.web.ticket.Ticket;
import com.wdyx.weixin.web.ticket.TicketOwner;
import com.wdyx.weixin.web.ticket.TicketUtil;
import com.wdyx.weixin.web.vote.Vote;
import com.wdyx.weixin.web.vote.VoteUtil;

public class WeChatMsgHandler extends WechatRequestHandlerImpl {

	private String basePath = getRequest().getScheme() + "://"
			+ getRequest().getServerName() + ":" + getRequest().getServerPort()
			+ getRequest().getContextPath() + "/";
	
	@Override
	protected Object handleEventMsg(EventMsg eventMsg) {
		Object response = null;
		String openid = eventMsg.getFromUserName();
		String ErrorMsg = "<a href=\""+basePath+"MainServlet?page=main/ErrorReport.jsp&openid="+openid+"\">将问题报告给我们</a>";
		String key = eventMsg.getEventKey();
		if(key.equals("11")||key.equals("12")||key.equals("13")||key.equals("14")||key.equals("15")){
			String notice = "<a href=\""+basePath+"MainServlet?page=plugins/bind.jsp&openid="+openid+"\">绑定账号</a>";
			String info = "/尴尬信息获取失败，请稍候重试。若问题依旧，请尝试重新"+notice+"，或"+ErrorMsg;
			String footer = "\n\n不爱我了？回复“松绑”可将我扫地出门/委屈";
			String warn = "/玫瑰亲爱的，请先"+notice;
			if(key.equals("11")||key.equals("12")){
				if(User.validate(openid)){
					Map<String, String> data = EduInfoServiceUtil.getDataFluently(openid);
					if(data!=null){
						info = (key.equals("11")?data.get("score")+"\n\t/玫瑰再接再厉哦~":data.get("course"))+footer;
					}
				}else{
					info = warn;
				}
			}else if(key.equals("13")||key.equals("14")){
				if(User.validate(openid)){
					if(key.equals("13")){
						Map<String, String> data = DigitalLibraryServiceUtil.getDataFluently(openid);
						if (data!=null) {
							info = data.get("borrow")+footer;
						}
					}else {
						User user = User.getUser(openid);
						DigitalLibraryService DLS = new DigitalLibraryService(user.getUsername(),user.getDLSPassword());
						if(DLS.login()){
							info = DLS.renewBookReturnStr()+footer;
						}
					}
				}else{
					info = warn;
				}
			}else if(key.equals("15")){
				if(User.validate(openid)){
					User user = User.getUser(openid);
					InfoPortalService IPS = new InfoPortalService(user.getUsername(),user.getIPSPassword());
					if(IPS.login()){
						String balance = IPS.getShortMsg();
						if(balance==null||balance.trim().equals("")){
							info = "/尴尬校园卡信息获取失败，请稍候重试。若问题依旧，请"+ErrorMsg;
						}else{
							info = "/飞吻亲爱的，您当前的校园卡状态如下：\n\n"+balance+footer;
						}
					}
				}else{
					info = warn;
				}
			}
			ToCommonText toText = new ToCommonText(info);
			response =  toText.handleMessage(eventMsg);
		}else if (key.equals("21")||key.equals("22")||key.equals("23")||key.equals("24")||key.equals("25")||key.equals("34")||key.equals("35")) {
			List<Article> articles = getArticlesByType(key,1,openid);
			ToArticles toArticles = new ToArticles(articles);
			response =  toArticles.handleMessage(eventMsg);
		}else if (key.equals("31")||key.equals("32")||key.equals("33")) {
			String info = "";
			if(key.equals("31")||key.equals("32")){
				if(key.equals("31")){
					Map<String, String> data = MeiCaoMovieServiceUtil.getDataFluently();
					if(data!=null){
						info = data.get("raw");
					}
					if(info==null||info.trim().equals("")){
						info = "梅操电影信息获取异常，请"+ErrorMsg;
					}
				}else{
					Map<String, String> data = TodayInHistoryServiceUtil.getDataFluently();
					if(data==null){
						info = "过去现在信息获取异常，请"+ErrorMsg;
					}else {
						info = data.get("today");
						info = "------历史上的"+info+"------\n";
						info+= data.get("history");
					}
				}
			}else if(key.equals("33")){
				if(User.getMode(openid).equals(User.Mode.DianGe)){
					if(User.setMode(openid, User.Mode.Normal)){
						info = "/OK"+"您已退出音乐时光";
					}else{
						info = "/尴尬"+"额。。音乐时光机出了点问题。。";
					}
					
				}else {
					if(User.setMode(openid, User.Mode.DianGe)){
						info = "/玫瑰么么哒，你喜欢听什么音乐呢？请告诉我歌名："+"\n(ps:再次点击“音乐时光”按钮退出音乐时光。)";
					}else{
						info = "/尴尬"+"额。。音乐时光机出了点问题，请"+ErrorMsg;
					}
				}
			}
			ToCommonText toText = new ToCommonText(info);
			response =  toText.handleMessage(eventMsg);
		}
		return response;
	}

	@Override
	protected Object handleTextMsg(TextMsg textMsg) {
		Object response = null;
		String openid = textMsg.getFromUserName();
		String content = textMsg.getContent().trim();
		String mode = User.getMode(openid);
		String ErrorMsg = "<a href=\""+basePath+"MainServlet?page=main/ErrorReport.jsp&openid="+openid+"\">将问题报告给我们</a>";
		if(content.equals("admin")){
			String info = "<a href=\""+basePath+"MainServlet?page=plugins/admin.jsp&openid="+openid+"\">Hello,admin.</a>";
			ToCommonText toText = new ToCommonText(info);
			response =  toText.handleMessage(textMsg);
		}else if(content.equals("logout")){
			String info = AdminUtil.logout(openid)?"OK":"ERROR";
			ToCommonText toText = new ToCommonText(info);
			response =  toText.handleMessage(textMsg);
		}else if(content.startsWith("@")){
			String info = "";
			if(UserMgrApi.sendMsgToUser(AccessTokenMgr.getAccessToken("wdyx").toString(), openid, content.replace("@", ""))){
				info = "/OK发送成功！<a href=\""+basePath+"MainServlet?page=plugins/bbs.jsp&openid="+openid+"\">顺便去树洞看看</a>";
			}else {
				info = "发送失败。<a href=\""+basePath+"MainServlet?page=plugins/bbs.jsp&openid="+openid+"\">去树洞回复</a>";
			}
			ToCommonText toText = new ToCommonText(info);
			response =  toText.handleMessage(textMsg);
		}else if (content.contains("十佳歌手")) {
			String info = "";
			Gift gift = GiftUtil.getGift();
			if(gift!=null){
				if(gift.getTime().after(new Date())){
					info = "抽奖活动还未开始";
				}else {
					int count = MysqlUtil.getCount("GiftOwner", "gift", gift.getGift());
					if(count<gift.getCount()){
						if(GiftUtil.signOwner(openid)){
							info = "恭喜你抽中了奖品:"+gift;
						}else {
							info = "系统故障，请"+ErrorMsg;
						}
					}else {
						info = "啊噢，奖品已被抽光了，下次下手要快哦~";
					}
				}
			}else {
				info = "暂无十佳歌手的相关活动";
			}
			ToCommonText toText = new ToCommonText(info);
			response =  toText.handleMessage(textMsg);
		}else if (content.contains("抢票")) {
			Ticket ticket = TicketUtil.getTicket();
			if(ticket!=null){
				List<Article> articles = new ArrayList<Article>();
				Article article = new Article();
				article.setTitle("【抢票】"+ticket.getTitle());
				article.setDescription("开始时间："+DateUtil.format(ticket.getDate(), DateUtil.FORMAT_DATETIME)+",点我进入");
				article.setPicUrl(Const.EXT_CONFIG.get("BASE_WEB_VIEW_PIC_URL")+ticket.getPicurl());
				article.setUrl(basePath+"MainServlet?page=plugins/ticket.jsp&openid="+openid);
				articles.add(article);
				ToArticles toArticles = new ToArticles(articles);
				return toArticles.handleMessage(textMsg);
			}else {
				String info ="最近暂无抢票活动";
				ToCommonText toText = new ToCommonText(info);
				response =  toText.handleMessage(textMsg);
			}
		}else if (content.contains("检票")) {
			String info ="--------检票结果--------\n";
			Ticket ticket = TicketUtil.getTicket();
			TicketOwner owner = TicketUtil.getTicketOwner(openid);
			if(owner!=null){
				info+="序号："+owner.getId()+"\n";
				info+="演出："+owner.getTitle()+"\n";
				info+="票种："+owner.getType()+"\n";
				info+="状态："+owner.getState()+"\n";
				info+="备注："+ticket.getRemark()+"\n";
				if(owner.getState().equals("未领票")){
					info+="<a href=\""+basePath+"MainServlet?page=plugins/verifyOwner.jsp&openid="+openid+"\">点击进入检票现场</a>";
				}else {
					info+="领票时间："+owner.getTime();
				}
			}else {
				info+="您当前无任何票务信息";
			}
			ToCommonText toText = new ToCommonText(info);
			response =  toText.handleMessage(textMsg);
		}else if (content.contains("投票")) {
			Vote vote = VoteUtil.getVote();
			if(vote!=null){
				List<Article> articles = new ArrayList<Article>();
				Article article = new Article();
				article.setTitle("【投票】"+vote.getTitle());
				article.setDescription("点我进入");
				article.setPicUrl(Const.EXT_CONFIG.get("BASE_WEB_VIEW_PIC_URL")+vote.getCover());
				article.setUrl(basePath+"MainServlet?page=main/vote.jsp&openid="+openid);
				articles.add(article);
				ToArticles toArticles = new ToArticles(articles);
				return toArticles.handleMessage(textMsg);
			}else {
				String info ="最近暂无投票活动";
				ToCommonText toText = new ToCommonText(info);
				response =  toText.handleMessage(textMsg);
			}
		}else if(content.equals("松绑")||content.equals("解绑")){
			String info = "/再见松绑成功。没有你的日子里我会更加珍惜自己/快哭了";
			if(User.validate(openid)){ 
				info = "您还未绑定账号哦~"+"<a href=\""+basePath+"MainServlet?page=plugins/bind.jsp&openid="+openid+"\">点我绑定账号，轻一点哦，么么哒~</a>";
			}else if(!User.unbindUser(openid)) {
				info = "账户解除操作失败，请将问题反馈给程序猿";
			}
			ToCommonText toText = new ToCommonText(info);
			response =  toText.handleMessage(textMsg);
		}else if (content.equals("绑定")) {
			String info = "<a href=\""+basePath+"MainServlet?page=plugins/bind.jsp&openid="+openid+"\">点我绑定账号，轻一点哦，么么哒~</a>";
			ToCommonText toText = new ToCommonText(info);
			response =  toText.handleMessage(textMsg);
		}else if (content.equals("留言")) {
			String info = "/玫瑰么么哒~";
			if(User.setMode(openid, User.Mode.LiuYan)){
				info = "从现在起你说的每一句话我都会认真听的。/玫瑰么么哒\n(ps:回复“。”结束留言，点击右下方“艺协树洞”按钮可查看最新留言)";
			}else {
				info = "/衰额。。我今天有点不舒服";
			}
			ToCommonText toText = new ToCommonText(info);
			response = toText.handleMessage(textMsg);
		}else if (content.equals("反馈")) {
			String info ="<a href=\""+ basePath+"MainServlet?page=main/ErrorReport.jsp&openid="+openid+"\">点我填写反馈信息</a>";
			ToCommonText toText = new ToCommonText(info);
			response =  toText.handleMessage(textMsg);
		}else if (content.equals("活动")) {
			String info ="<a href=\""+basePath+"MainServlet?page=main/main.jsp&type=24&openid="+openid+"\">点我查看最新活动</a>";
			ToCommonText toText = new ToCommonText(info);
			response =  toText.handleMessage(textMsg);
		}else if (content.equals("招新")) {
			String info ="<a href=\""+basePath+"MainServlet?page=main/main.jsp&type=25&openid="+openid+"\">点我查看招新信息</a>";
			ToCommonText toText = new ToCommonText(info);
			response =  toText.handleMessage(textMsg);
		}else if(mode.equals(User.Mode.DianGe)){
			Music music = BaiduMusicService.searchMusic(content, "");
			ToMusic toMusic = new ToMusic(music);
			response =  toMusic.handleMessage(textMsg);
		}else if(mode.equals(User.Mode.LiuYan)){
			String info = "/玫瑰么么哒~收到";
			if(content.equals("。")){
				if(User.setMode(openid, User.Mode.Normal)){
					info = "/玫瑰谢谢你的留言，么么哒~";
				}else {
					info = "/衰额。。我今天有点不舒服";
				}
			}else {
				if(BbsUtil.writeBbs(openid, content,null)>0){
					info = "/玫瑰收到，么么哒~";
				}else {
					info = "/衰额。。我今天有点不舒服";
				}
			}
			ToCommonText toText = new ToCommonText(info);
			response =  toText.handleMessage(textMsg);
		}else {
			BbsUtil.writeBbs(openid, content,null);
			String info = SimsimServiceUtil.filteredResponse(content);
			info = (info==null||info.equals(""))?getRamResponseByKey("随机回复"):info;
			ToCommonText toText = new ToCommonText(info);
			response =  toText.handleMessage(textMsg);
		}
		return response;
	}

	@Override
	public Object handleVoiceMsg(VoiceMsg voiceMsg) {
		String recognition = voiceMsg.getRecognition();
		String info = SimsimServiceUtil.filteredResponse(recognition);
		ToCommonText toText = new ToCommonText(info);
		return toText.handleMessage(voiceMsg);
	}

	@Override
	public Object onSubscribe(EventMsg eventMsg) {
		String openid = eventMsg.getFromUserName();
		User.addCommonUser(openid);
		String info = "欢迎关注武大艺协！服务指南请看菜单，回复任意内容有惊喜，赶快试试吧~~~";
		ToCommonText toText = new ToCommonText(info);
		return toText.handleMessage(eventMsg);
	}

	@Override
	public Object onUnsubscribe(EventMsg eventMsg) {
		String openid = eventMsg.getFromUserName();
		User.removeCommonUser(openid);
		return null;
	}

	public List<Article> getArticlesByType(String type,int count,String openid) {
		List<Article> articles = new ArrayList<Article>();
		CachedRowSet crs = null;
		try {
			String query = "select id,title,description,picurl from webview where type='"+type+"' order by id desc limit 0,"+count;
			crs = MysqlUtil.query(query);
			while (crs.next()) {
				Article article = new Article();
				article.setTitle(crs.getString(2));
				article.setDescription(crs.getString(3));
				article.setPicUrl(Const.EXT_CONFIG.get("BASE_WEB_VIEW_PIC_URL")+crs.getString(4));
				String url = basePath;
				if(type.equals("35")){
					url+="MainServlet?page=plugins/bbs.jsp&openid="+openid;
				}else if(type.equals("34")) {
					url+="MainServlet?page=main/art.jsp&openid="+openid;
				}else {
					url+="MainServlet?page=main/main.jsp&openid="+openid+"&type="+type;
				}
				article.setUrl(url);
				articles.add(article);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return articles;
	}
	
	public List<String> getResponseByKey(String key) {
		List<String> responses = new ArrayList<String>();
		CachedRowSet crs = null;
		try {
			String query = "select response from keyword where `key` = '"+key+"'";
			crs = MysqlUtil.query(query);
			while(crs.next()){
				responses.add(crs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return responses.isEmpty()?null:responses;
	}
	
	public String getRamResponseByKey(String key) {
		List<String> responses = getResponseByKey(key);
		String response = null;
		if(responses!=null){
			Random random = new Random();
			response = responses.get(random.nextInt(responses.size()));
		}
		return response;
	}

}
