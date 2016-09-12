<%@page import="com.wdyx.weixin.web.bbs.InterMsgBlock"%>
<%@page import="com.wdyx.weixin.web.bbs.BbsUtil"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
List<InterMsgBlock> msgs = BbsUtil.getInterMsgBlocks(30, null, null, null);
request.setAttribute("msgs", msgs);
request.setAttribute("maxId", ""+msgs.get(0).getId());
request.setAttribute("minId", ""+msgs.get(msgs.size()-1).getId());
%>
<!DOCTYPE html>
<html>
<head>
<title>艺协树洞</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
<!-- bootstrap 3.0.2 -->
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<!-- font Awesome -->
<link href="css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<!-- Theme style -->
<link href="css/AdminLTE.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="box box-success">
    <div class="box-header">
        <h3 class="box-title"><i class="fa fa-comments-o"></i> 艺协树洞</h3>
        <div class="box-tools pull-right" data-toggle="tooltip" title="">
        	<div class="btn-group">
            <button type="button" class="btn btn-info">自动刷新</button>
            <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown">
                <span class="caret"></span>
                <span class="sr-only">自动刷新</span>
            </button>
            <ul class="dropdown-menu" role="menu">
                <li><a onclick="autoRefresh(2000);">2s</a></li>
              <li><a href="javascript:void(0)" onclick="autoRefresh(4000);">4s</a></li>
              <li><a href="javascript:void(0)" onclick="autoRefresh(6000);">6s</a></li>
              <li><a href="javascript:void(0)" onclick="autoRefresh(8000);">8s</a></li>
              <li class="divider"></li>
              <li><a href="javascript:void(0)" onclick="disableAutoRefresh();">无</a></li>
          	</ul>
           	</div>
        	<div class="btn-group" data-toggle="btn-toggle" >
               	<button type="button" id="atme" class="btn btn-default btn-sm active"><i class="fa text-green">@ me</i></button>
             	<button type="button" id="home" class="btn btn-default btn-sm active"><i class="fa text-green">Home</i></button>
           </div>
      	</div>
  	</div>
  	<br>
	<div class="box-footer">
		<div class="input-group">
	    	<input class="form-control" id="msg" placeholder="Say something..."/>
	    	<div class="input-group-btn">
	        <button class="btn btn-success" id="send-msg"><i class="fa fa-plus"></i><span style="display:none"><img src="img/ajax-loader1.gif" width="20px" height="20px"></span></button>
	     	</div>
 		</div>
 	</div>
	<br>
	<div class="box-body chat" id="chat-box">
	<c:forEach var="msg" items="${msgs }">
		<!-- chat item -->
		<div class="msg-body" id="${msg.openid }">
			<div class="item" id="${msg.id }">
			    <img src="${msg.headimgurl }" class="online"/>
			    <p class="message">
			        <a href="javascript:void(0)" class="name" id="${msg.openid }">
			            <small class="text-muted pull-right"><i class="fa fa-clock-o"></i> ${msg.timestamp }</small>
			            <span>${msg.spokesman }</span>
			        </a>
			        ${msg.msg }
			    </p>
			    <c:if test="${uid=='admin' }">
			    <div class="operation" align="right"><a class="delete" href="javascript:void(0)">删除</a>&nbsp;|&nbsp;<a class="forbid" href="javascript:void(0)">禁言</a></div>
			    <script src="js/bbs-admin.js" type="text/javascript"></script>
			    </c:if>
			    <c:if test="${not empty msg.review }">
			    <c:forEach var="review" items="${msg.review }">
			    <div class="attachment">
				    <div class="item" id="${review.id }">
				    <img src="${review.headimgurl }" class="online"/>
			    		<p class="message">
			        		<a href="javascript:void(0)" class="name" id="${review.openid }">
			            	<small class="text-muted pull-right"><i class="fa fa-clock-o"></i> ${review.timestamp }</small>
			            	<span>${review.spokesman }</span>
			        		</a>
			        		${review.msg }
			    		</p>
			    		<c:if test="${uid=='admin' }">
			    		<div class="operation" align="right"><a class="delete" href="javascript:void(0)">删除</a>&nbsp;|&nbsp;<a class="forbid" href="javascript:void(0)">禁言</a></div>
			    		</c:if>
					</div>
			   	</div>
			    </c:forEach>
			</c:if>
			</div>
		</div>
	</c:forEach>
	</div>
</div>
<div class="box-footer" style="width:100%; min-height:30px; max-height:50px; height:40px;">
	<div align="center">
		<span id="loading-img" style="display:none"><img src="img/ajax-loader.gif" width="18px" height="18px"></span>
		<span id="no-more" style="display:none">没有更多</span>
	</div>
</div> 

<form action="">
<input type="hidden" name="openid" id="openid" value="${openid }">
<input type="hidden" name="uid" id="uid" value="${uid }">
<input type="hidden" name="maxId" id="maxId" value="${maxId }">
<input type="hidden" name="minId" id="minId" value="${minId }">
</form>

<div id="box-review" class="box-footer" style="display:none;position:fixed;bottom:1px;width:100%;padding:2px">
	<div class="input-group">
    	<input class="form-control" id="review" placeholder="Say something..."/>
    	<div class="input-group-btn">
        <button class="btn btn-success" id="send-review"><i class="fa fa-plus"></i><span style="display:none"><img src="img/ajax-loader1.gif" width="20px" height="20px"></span></button>
      	</div>
  	</div>
</div>  

<script src="js/jquery.min.js"></script>
<script src="js/face.js" type="text/javascript"></script>
<script src="js/time-format.js" type="text/javascript"></script>
<script src="js/bbs.js" type="text/javascript"></script>
<script src="js/bbs-admin.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
</body>
</html>