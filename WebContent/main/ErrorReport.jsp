<%@page import="com.wdyx.weixin.web.error.Error"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
	request.setAttribute("errors", Error.getErrors());
    %>
<!DOCTYPE html>
<html>
<head>
<title>用户反馈</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
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

<article class="container box style3">
	<section>
		<header>
			<h2>用户反馈</h2>
			<p>在这里写下您对我们的服务的意见或建议，或者您在使用我们的产品时遇到的问题。</p>
		</header>
	</section>
	
	<section>
	<form method="post" action="" onsubmit="return false;">
	<input type="hidden" name="openid" id="openid" value="${openid }">
	<input type="hidden" name="code" id="code" value="${code }">
		<div class="row half">
			<div class="12u">
				<textarea name="remark" id="remark" placeholder="反馈信息"></textarea>
			</div>
		</div>
		<div class="row">
			<div class="12u">
				<ul class="actions">
					<li><input type="submit" id="send" value="Send" /></li>
					
				</ul>
			</div>
		</div>
	</form>
	</section>
</article>


<c:if test="${not empty errors }">
<article class="container box style3">
	<section>
		<header>
			<h3>最近已反馈的信息</h3>
		</header>
		<ul class="default">
		<c:forEach var="error" items="${errors }">
		<li>${error}</li>
		</c:forEach>
		</ul>	
	</section>
</article>
</c:if>

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
	<script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>
	<script src="js/time-format.js"></script>
	<script src="js/error-report.js"></script>
</body>
</html>