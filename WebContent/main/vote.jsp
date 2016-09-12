<%@page import="com.wdyx.weixin.web.vote.VoteUtil"%>
<%@page import="com.wdyx.weixin.web.vote.Vote"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
	Vote vote = VoteUtil.getVote();
    %>
<!DOCTYPE html>
<html>
<head>
<title>【投票】<%=vote.getTitle() %></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta name="description" content="武大艺协" />
<meta name="keywords" content="武大艺协" />
<!--[if lte IE 8]><script src="css/ie/html5shiv.js"></script><![endif]-->
<script src="js/jquery.min.js"></script>
<script src="js/jquery.scrolly.min.js"></script>
<script src="js/jquery.poptrox.min.js"></script>
<script src="js/skel.min.js"></script>
<script src="js/init.js"></script>
<script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>
<script src="js/vote.js"></script>
<noscript>
	<link rel="stylesheet" href="css/skel.css" />
	<link rel="stylesheet" href="css/style.css" />
</noscript>
<!--[if lte IE 8]><link rel="stylesheet" href="css/ie/v8.css" /><![endif]-->
</head>
<body>

<!-- Header -->
<section id="header">
	<header>
		<h1><%=vote.getTitle() %></h1>
		<p>你来定义</p>
	</header>
	<footer>
		<a href="#banner" class="button style2 scrolly scrolly-centered">星光大道</a>
	</footer>
</section>

<!-- Banner -->
<section id="banner">
	<header>
		<h2>你心中的<%=vote.getTitle() %></h2>
	</header>
	<p>这是一个群英荟萃的舞台，每一个参赛者都闪耀无比。筑梦艺协，我们离不开他们的璀璨；绽放自我，我们甘做你脚下的舞台。发出你的声音，释放你的呐喊，告诉我，谁是夜空中最亮的星？</p>
	<footer>
		<a href="#1" class="button style2 scrolly">开启投票</a>
	</footer>
</section>

<input type="hidden" name="openid" id="openid" value="${openid }"></input>

<%int i=0; %>
<c:forEach var="candidate" items="<%=vote.getCandidate() %>">
	<article id="<%=i+1 %>" class="container box style3">
			<header>
				<h2>${candidate }</h2>
			</header>
			<div class="inner gallery">
					<a href="${baseItemPicUrl}<%="vote/"+vote.getPicurl()[i] %>" class="image fit"><img src="${baseItemPicUrl}<%="vote/"+vote.getPicurl()[i] %>" alt="${candidate }" title="${candidate }"/></a>
			</div>
			<div class="inner">
				<p><%=vote.getDescription()[i] %></p>
				<p>当前票数：<span><%=VoteUtil.getVotes(vote.getCandidate()[i])%></span></p>
			</div>
			<div class="row" align="center">
			<div class="12u">
				<ul class="actions">
					<li><input type="hidden" value="${candidate }" /></li>
					<li><input type="submit" value="给我投票" /></li>
				</ul>
			</div>
		</div>
	</article>
	<%i++; %>
</c:forEach>

<section id="footer">
	<ul class="icons">
		<li>&copy; 2015武大艺协. All rights reserved.</li>
	</ul>
	<div class="copyright">
		<ul class="menu">
			<li>
			<a href="/MainServlet?page=main/main.jsp&type=21">部门简介</a>|
			<a href="/MainServlet?page=main/main.jsp&type=22">造星舞台</a>|
			<a href="/MainServlet?page=main/main.jsp&type=23">男神女神</a>|
			<a href="/MainServlet?page=main/main.jsp&type=24">最新活动</a>|
			<a href="/MainServlet?page=main/main.jsp&type=25">招新信息</a>|
			<a href="/MainServlet?page=main/art.jsp&type=34">周末文苑</a>|
			<a href="/MainServlet?page=plugins/bbs.jsp">艺协树洞</a>|
			<a href="/MainServlet?page=main/ErrorReport.jsp">用户反馈</a>
			</li>
		</ul>
	</div>
</section>
</body>
</html>