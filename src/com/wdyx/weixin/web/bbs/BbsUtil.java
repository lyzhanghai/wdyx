package com.wdyx.weixin.web.bbs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import org.apache.log4j.Logger;
import org.mobangjack.common.util.MysqlUtil;
import org.mobangjack.common.util.StrUtil;

import com.wdyx.weixin.user.UserInfo;

public class BbsUtil {

	private static final Logger logger = Logger.getLogger(BbsUtil.class);
	
	public static boolean initDB(){
		String sql = "create table if not exists bbs("
				+ "id int(11) unsigned not null auto_increment,"
				+ "openid varchar(28) default null,"
				+ "msg varchar(2048) default null,"
				+ "time timestamp not null default current_timestamp,"
				+ "msgid int(11) default null,"
				+ "readflag int(1) not null default 0 auto_increment,"
				+ "primary key(id)) "
				+ "comment='bbs' default character set utf8 collate utf8_general_ci";
		return MysqlUtil.update(sql);
	}
	
	public static BbsUser getBbsUser(String openid) {
		BbsUser bbsUser = null;
		Connection con = MysqlUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps=con.prepareStatement("select * from `BbsUser` where `openid`=? limit 0,1");
			ps.setString(1, openid);
			rs = ps.executeQuery();
			if(rs.next()){
				bbsUser = new BbsUser();
				bbsUser.setId(rs.getInt("id"));
				bbsUser.setOpenid(openid);
				bbsUser.setNickname(rs.getString("nickname"));
				bbsUser.setAvatar(rs.getString("avatar"));
				bbsUser.setSex(rs.getInt("sex"));
				bbsUser.setAddress(rs.getString("address"));
				bbsUser.setLastActiveTime(rs.getTimestamp("lastActiveTime"));
				bbsUser.setForbid(rs.getInt("forbid"));
			}
		} catch (SQLException e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			MysqlUtil.close(rs, ps, con);
		}
		return bbsUser;
	}
	
	public static BbsUser getBbsUser(int msgid) {
		BbsUser bbsUser = null;
		Connection con = MysqlUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps=con.prepareStatement("select `openid` from `bbs` where `id`=? limit 0,1");
			ps.setInt(1, msgid);
			rs = ps.executeQuery();
			String openid = null;
			if(rs.next()){
				openid = rs.getString(1);
			}
			rs.close();
			ps.close();
			if(openid!=null){
				ps=con.prepareStatement("select * from `BbsUser` where `openid`=? limit 0,1");
				ps.setString(1, openid);
				rs = ps.executeQuery();
				if(rs.next()){
					bbsUser = new BbsUser();
					bbsUser.setId(rs.getInt("id"));
					bbsUser.setOpenid(openid);
					bbsUser.setNickname(rs.getString("nickname"));
					bbsUser.setAvatar(rs.getString("avatar"));
					bbsUser.setSex(rs.getInt("sex"));
					bbsUser.setAddress(rs.getString("address"));
					bbsUser.setLastActiveTime(rs.getTimestamp("lastActiveTime"));
					bbsUser.setForbid(rs.getInt("forbid"));
				}
			}
		} catch (SQLException e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			MysqlUtil.close(rs, ps, con);
		}
		return bbsUser;
	}
	
	public static Bbs getLatestReply(String openid) {
		Bbs bbs = null;
		Connection con = MysqlUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		int id = 0;
		try {
			ps=con.prepareStatement("select `id` from `bbs` where `openid`=? order by id desc limit 0,1");
			ps.setString(1, openid);
			rs = ps.executeQuery();
			if(rs.next()){
				id = rs.getInt(1);
			}
			rs.close();
			ps.close();
			if(id!=0){
				ps=con.prepareStatement("select * from `bbs` where `msgid`=? order by id desc limit 0,1");
				ps.setInt(1, id);
				rs = ps.executeQuery();
				if(rs.next()){
					bbs = new Bbs();
					bbs.setId(rs.getInt("id"));
					bbs.setOpenid(rs.getString("openid"));
					bbs.setMsg(rs.getString("msg"));
					bbs.setMsgid(rs.getInt("msgid"));
					bbs.setTime(rs.getTimestamp("time"));
					bbs.setReadflag(rs.getInt("readflag"));
				}
			}
		} catch (SQLException e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			MysqlUtil.close(rs, ps, con);
		}
		return bbs;
	}
	
	public static List<Bbs> getNotification(String openid) {
		List<Bbs> bbss = new ArrayList<Bbs>();
		List<Integer> ids = new ArrayList<Integer>();
		Connection con = MysqlUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps=con.prepareStatement("select `id` from `bbs` where `openid`=?");
			ps.setString(1, openid);
			rs = ps.executeQuery();
			while (rs.next()) {
				ids.add(rs.getInt(1));
			}
			ps.close();
			if(!ids.isEmpty()){
				ps=con.prepareStatement("select * from `bbs` where `msgid`=? and `readflag`=0");
				for(int i:ids){
					ps.setInt(1, i);
					rs = ps.executeQuery();
					while(rs.next()){
						Bbs bbs = new Bbs();
						bbs.setId(rs.getInt("id"));
						bbs.setOpenid(rs.getString("openid"));
						bbs.setMsg(rs.getString("msg"));
						bbs.setMsgid(rs.getInt("msgid"));
						bbs.setTime(rs.getTimestamp("time"));
						bbs.setReadflag(rs.getInt("readflag"));
						bbss.add(bbs);
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			MysqlUtil.close(rs, ps, con);
		}
		
		return bbss;
	}
	
	public static int addUser(BbsUser bbsUser) {
		int key = -1;
		if(bbsUser==null){
			return key;
		}
		Connection con = MysqlUtil.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps=con.prepareStatement("insert int `BbsUser`(`openid`,`avatar`,`nickname`,`sex`,`address`,`lastActiveTime`,`forbid`",
					PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, bbsUser.getOpenid());
			ps.setString(2, bbsUser.getAvatar());
			ps.setString(3,bbsUser.getNickname());
			ps.setInt(4, bbsUser.getSex());
			ps.setString(5,bbsUser.getAddress());
			ps.setTimestamp(6, bbsUser.getLastActiveTime());
			ps.setInt(7, bbsUser.getForbid());
			ps.executeUpdate();
			
			rs = ps.getGeneratedKeys();
			if(rs.next()){
				key = rs.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			MysqlUtil.close(rs, ps, con);
		}
		return key;
	}
	
	public static List<Integer> addUsers(List<BbsUser> bbsUsers) {
		if(bbsUsers==null||bbsUsers.isEmpty()){
			return null;
		}
		List<Integer>  keys = new ArrayList<Integer>();
		Connection con = MysqlUtil.getConnection();
		PreparedStatement ps = null;
		try {
			ps=con.prepareStatement("insert int `BbsUser`(`openid`,`avatar`,`nickname`,`sex`,`address`,`lastActiveTime`,`forbid`",
					PreparedStatement.RETURN_GENERATED_KEYS);
			for(BbsUser bbsUser:bbsUsers){
				ps.setString(1, bbsUser.getOpenid());
				ps.setString(2, bbsUser.getAvatar());
				ps.setString(3,bbsUser.getNickname());
				ps.setInt(4, bbsUser.getSex());
				ps.setString(5,bbsUser.getAddress());
				ps.setTimestamp(6, bbsUser.getLastActiveTime());
				ps.setInt(7, bbsUser.getForbid());
				ps.addBatch();
			}
			int flags[] = ps.executeBatch();
			ResultSet rs = ps.getGeneratedKeys();
			while(rs.next()){
				int i = 0;
				keys.add(flags[i]==PreparedStatement.EXECUTE_FAILED?-1:rs.getInt("id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			MysqlUtil.close(null, ps, con);
		}
		return keys;
	}
	
	public static int writeBbs(String openid, String msg,Integer msgid) {
		msgid = msgid==null||msgid<0?0:msgid;
		msg = msg.replaceAll("(?is)(http://[/\\.\\w]+\\.jpg)","<img src='$1'/>");
		msg = msg.replaceAll("(?is)(?<!')(http://[/\\.\\w]+)","<a href='$1'>$1</a>");
		String sql = "insert into bbs (openid,msg,msgid) values ('"+openid+"','"+msg+"',"+msgid+")";
		return MysqlUtil.updateAndRetrieveKey(sql);
	}

	public static List<InterMsgBlock> getInterMsgBlocks(Integer count,Integer lastId,String ori,String openid) {
		count = count==null||count<0?30:count;
		lastId = lastId==null||lastId<0?999999999:lastId;
		ori = StrUtil.isNOB(ori)||!ori.equals("gt")?"lt":ori;
		String query = "select * from bbs where msgid=0 and id<"+lastId+" and openid='"+openid+" order by id desc limit 0,"+count;
		if(StrUtil.isNOB(openid)){
			query = "select * from bbs where msgid=0 and id<"+lastId+" order by id desc limit 0,"+count;
		}else if(ori.equals("gt")){
			query = query.replace("<", ">").replace("desc", "asc");
		}
		CachedRowSet crs = MysqlUtil.query(query);
		List<InterMsgBlock> msgs = new ArrayList<InterMsgBlock>();
		try {
			while(crs.next()){
				openid = crs.getString("openid");
				UserInfo userInfo = UserInfo.getUserInfo(openid);
				InterMsgBlock msg = new InterMsgBlock();
				msg.setId(crs.getInt("id"));
				msg.setOpenid(openid);
				msg.setHeadimgurl(userInfo.getHeadimgurl());
				String remark = "(PROVINCE CITY)";
				if(userInfo.getProvince().length()!=0&&userInfo.getCity().length()!=0){
					remark = "("+userInfo.getProvince()+" "+userInfo.getCity()+")";
				}else if(userInfo.getProvince().length()==0&&userInfo.getCity().length()==0) {
					remark = "";
				}else {
					remark = "("+userInfo.getProvince()+userInfo.getCity()+")";
				}
				msg.setSpokesman(userInfo.getNickname()+remark);
				msg.setMsg(crs.getString("msg"));
				msg.setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(crs.getTimestamp("time")));
				msg.setReview(getReview(crs.getInt("id")));
				msgs.add(msg);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return msgs;
	}
	public static List<MsgBlock> getReview(int msgid) {
		String query = "select * from bbs where `msgid`="+msgid;
		CachedRowSet crs = MysqlUtil.query(query);
		List<MsgBlock> reviews = new ArrayList<MsgBlock>();
		try {
			while(crs.next()){
				String openid = crs.getString("openid");
				UserInfo userInfo = UserInfo.getUserInfo(openid);
				MsgBlock msg = new MsgBlock();
				msg.setId(crs.getInt("id"));
				msg.setOpenid(openid);
				msg.setHeadimgurl(userInfo.getHeadimgurl());
				String remark = "(PROVINCE CITY)";
				if(userInfo.getProvince().length()!=0&&userInfo.getCity().length()!=0){
					remark = "("+userInfo.getProvince()+" "+userInfo.getCity()+")";
				}else if(userInfo.getProvince().length()==0&&userInfo.getCity().length()==0) {
					remark = "";
				}else {
					remark = "("+userInfo.getProvince()+userInfo.getCity()+")";
				}
				msg.setSpokesman(userInfo.getNickname()+remark);
				msg.setMsg(crs.getString("msg"));
				msg.setTimestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(crs.getTimestamp("time")));
				reviews.add(msg);
			}
		} catch (SQLException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return reviews;
	}
	
	public static boolean deleteMsgById(int id){
		boolean flag = true;
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(id);
		String query = "select id from bbs where `msgid`='"+id+"'";
		CachedRowSet crs = MysqlUtil.query(query);
		try {
			while(crs.next()){
				ids.add(crs.getInt(1));
			}
		} catch (SQLException e) {
			flag = false;
			logger.error(e);
			e.printStackTrace();
		}
		String sql = "delete from bbs where `id`=?";
		Connection con = MysqlUtil.getConnection();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			for(int i:ids){
				ps.setInt(1, i);
				ps.addBatch();
			}
			ps.executeBatch();
		} catch (SQLException e) {
			flag = false;
			logger.error(e);
			e.printStackTrace();
		} finally {
			MysqlUtil.close(null, ps, con);
		}
		return flag;
	}
	
	public static boolean forbid(String openid){
		String sql = "update bbsuser set `forbid`=1 where `openid`='"+openid+"'";
		return MysqlUtil.update(sql);
	}
	
	public static void main(String[] args) {
		System.out.println(getLatestReply("oWXKeuCTghFIXJtLOgZUziT6o6HI"));
	}
}
