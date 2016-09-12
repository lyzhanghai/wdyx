<%@page import="com.wdyx.weixin.web.art.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
	<%
	Art art = ArtUtil.getArt((String)session.getAttribute("artid"));
	request.setAttribute("art", art);
	request.setAttribute("artRecords", ArtUtil.getArtRecord());
	String title = art.getTitle();
	request.setAttribute("title",title.substring(title.lastIndexOf("】")+1));
	%>
<!DOCTYPE html>
<html>
	<head>
		<title>武大艺协</title>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<meta name="description" content="武大艺协" />
		<meta name="keywords" content="武大艺协" />
		<!--[if lte IE 8]><script src="css/ie/html5shiv.js"></script><![endif]-->
		<script src="js/jquery.min.js"></script>
		<script src="js/jquery.scrolly.min.js"></script>
		<script src="js/jquery.poptrox.min.js"></script>
		<script src="js/skel.min.js"></script>
		<script src="js/init.js"></script>
		<noscript>
			<link rel="stylesheet" href="css/skel.css" />
			<link rel="stylesheet" href="css/style.css" />
		</noscript>
		<!--[if lte IE 8]><link rel="stylesheet" href="css/ie/v8.css" /><![endif]-->
	</head>
	<body>
	<!-- 文章主体 -->
	<article id="${art.id }" class="container box style3">
		<section>
			<header>
				<h2>${title }</h2>
				<p>${art.description }</p>
			</header>
		</section>
		
		<c:forEach var="item" items="${art.items }">
		<c:forEach var="pic" items="${item.picUrl }">
		<section>
			<div class="inner gallery">
				<a href="${baseItemPicUrl}${pic}" class="image fit"><img src="${baseItemPicUrl}${pic}" alt="${item.title }" title="${item.title }"/></a>
			</div>
		</section>
		</c:forEach>
		<section>
			<div class="inner">
				<p>${item.detail }</p>
			</div>
		</section>
		</c:forEach>
	</article>
	
	<!-- 发表评论 -->
	<article id="publish-review" class="container box style3">
		<section>
			<header>
			<h2>发表评论</h2>
			</header>
			<form method="post" action="" onsubmit="return false;">
			<input type="hidden" name="openid" value="${openid }">
			<input type="hidden" name="artid" value="${art.id }">
			<input type="hidden" name="title" value="${title }">
				<div class="row half">
					<div class="12u">
						<textarea name="review" placeholder="在这里写下您的评论（512字以下）"></textarea>
					</div>
				</div>
				<div class="row">
					<div class="12u">
						<ul class="actions">
							<li><input type="submit" name="publish" value="发表" /></li>
						</ul>
					</div>
				</div>
			</form>
		</section>
	</article>
	
	<!-- 评论列表 -->
	<article id="review-list" class="container box style3">
		<header>
			<h3>最新评论</h3>
		</header>
		<ul class="default">
		<c:forEach var="review" items="${art.reviews }">
		<li>${review}</li>
		</c:forEach>
		</ul>	
	</article>
	
	<!-- 文章列表 -->
	<article id="art-list" class="container box style3">
		<header>
			<h3>文章列表</h3>
		</header>
		<ul class="default">
		<c:forEach var="artRecord" items="${artRecords }">
		<li><a href="/MainServlet?page=main/art.jsp&artid=${artRecord.id}">${artRecord}</a></li>
		</c:forEach>
		</ul>	
	</article>
	
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
				<a href="/MainServlet?page=main/art.jsp">周末文苑</a>|
				<a href="/MainServlet?page=plugins/bbs.jsp">艺协树洞</a>|
				<a href="/MainServlet?page=main/ErrorReport.jsp">用户反馈</a>
				</li>
			</ul>
		</div>
	</section>
	
	<script src="js/time-format.js" type="text/javascript"></script>
	<script src="js/art.js" type="text/javascript"></script>
	
	</body>
</html>