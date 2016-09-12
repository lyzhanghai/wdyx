<%@ page import="com.wdyx.weixin.web.main.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
	<%
	String type = (String)session.getAttribute("type");
	
	if(type==null){
		type = "21";
		request.setAttribute("type", type);
	}
	WebView view = WebUtil.getWebView(type,null);
	request.setAttribute("view",view);
	String title = view.getTitle();
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
	<c:if test="${type=='21' }">
		<!-- Header -->
		<section id="header">
			<header>
				<h1>武大艺协</h1>
				<p>大学生的艺术圈</p>
			</header>
			<footer>
				<a href="#banner" class="button style2 scrolly scrolly-centered">走进艺协</a>
			</footer>
		</section>
		
		<!-- Banner -->
		<section id="banner">
			<header>
				<h2>打造全国最专业的学生组织</h2>
			</header>
			<p>武汉大学大学生文化艺术协会（艺协）是在校团委领导下，以校团委文化艺术办公室为指导，
			由理事会和执行机构两部分构成的校级组织。下设办公室、公关推广部、外联部、艺术竞技场、
			文化万花筒、周末艺苑、大型活动策划部、理事会联络部8个部门。各个部门紧密配合，共同担
			负起探寻武大文化，繁荣武大艺术的神圣使命，为全校师生奉献丰盛的文艺大餐，打造珞珈文艺
			精品。我们力求打造全国最专业的学生组织，实现价值，共同成就梦想。</p>
			<footer>
				<a href="#${view.id}" class="button style2 scrolly">更多精彩</a>
			</footer>
		</section>
	</c:if>
	
	<c:if test="${type=='24' }">
		<!-- Header -->
		<section id="header">
			<header>
				<h1>${title }</h1>
				<p>虚位以待，只等你来</p>
			</header>
			<footer>
				<a href="#banner" class="button style2 scrolly scrolly-centered">走进${title }</a>
			</footer>
		</section>
		
		<!-- Banner -->
		<section id="banner">
			<header>
				<h2>${title },一鸣惊人</h2>
			</header>
			<p>${view.description}</p>
			<footer>
				<a href="#${view.id}" class="button style2 scrolly">更多精彩</a>
			</footer>
		</section>
	</c:if>
	
	<c:if test="${type=='21' || type=='22' || type=='23' || type=='24' || type=='25'  }">
		<c:if test="${type!='24' }">
		<article id="${view.id }" class="container box style3">
			<section>
				<header>
					<h2>${view.title }</h2>
					<p>${view.description }</p>
				</header>
				<!-- <p>Paragraph</p> -->
			</section>
		</article>
		</c:if>
		<c:forEach var="item" items="${view.items }">
		<article id="${view.id }" class="container box style3">
		<header>
			<h2>${item.title }</h2>
		</header>
		<c:if test="${not empty item.picUrl}">
			<c:forEach var="pic" items="${item.picUrl }">
			<div class="inner gallery">
				<a href="${baseItemPicUrl}${pic}" class="image fit"><img src="${baseItemPicUrl}${pic}" alt="${item.title }" title="${item.title }"/></a>
			</div>
			</c:forEach>
		</c:if>
		<div class="inner">
			<p>${item.detail }</p>
		</div>
		</article>
		</c:forEach>
	</c:if>
	
	<c:choose>
		<c:when test="${type=='24'}">
			<article class="container box style3">
				<section>
					<header>
						<h3>我要报名</h3>
					</header>
					<form method="post" action="" onsubmit="return false;">
					<input type="hidden" name="openid" id="openid" value="${openid }"/>
					<input type="hidden" name="title" id="title" value="${title }"/>
						<div class="row">
							<div class="12u">
								<input class="text" type="text" name="name" id="name" value="" placeholder="姓名" />
							</div>
						</div>
						<div class="row">
							<div class="12u">
								<select name="gender" id="gender">
									<option value="">性别</option>
									<option value="男">男</option>
									<option value="女">女</option>
								</select>
							</div>
						</div>
						<div class="row">
							<div class="12u">
								<input class="text" type="email" name="email" id="email" value="" placeholder="邮箱" />
							</div>
						</div>
						<div class="row">
							<div class="12u">
								<input class="text" type="text" name="department" id="department" value="" placeholder="院系" />
							</div>
						</div>
						
						<div class="row">
							<div class="12u">
								<select name="grade" id="grade">
									<option value="">年级</option>
									<option value="2011">2011</option>
									<option value="2012">2012</option>
									<option value="2013">2013</option>
									<option value="2014">2014</option>
									<option value="2015">2015</option>
								</select>
							</div>
						</div>
						
						<div class="row">
							<div class="12u">
								<input class="text" type="text" name="contact" id="contact" value="" placeholder="QQ/微信/手机" />
							</div>
						</div>
						
						<div class="row">
							<div class="12u">
								<input class="text" type="text" name="remark" id="remark" value="" placeholder="参赛方式（个人或团体等）" />
							</div>
						</div>
						
						<div class="row">
							<div class="12u">
								<ul class="actions">
									<li><input type="submit" id="submit" value="确认" /></li>
								</ul>
							</div>
						</div>
					</form>
				</section>
			</article>
			<script src="js/rollup.js" type="text/javascript"></script>
		</c:when>
		
		<c:when test="${type=='25'}">
			<article class="container box style3">
				<section>
					<header>
						<h3>加入我们</h3>
					</header>
					<form method="post" action="" onsubmit="return false;">
					<input type="hidden" name="openid" id="openid" value="${openid }"/>
						<div class="row">
							<div class="12u">
								<input class="text" type="text" name="name" id="name" value="" placeholder="姓名" />
							</div>
						</div>
						<div class="row">
							<div class="12u">
								<select name="gender" id="gender">
									<option value="">性别</option>
									<option value="男">男</option>
									<option value="女">女</option>
								</select>
							</div>
						</div>
						<div class="row">
							<div class="12u">
								<select name="grade" id="grade">
									<option value="">年级</option>
									<option value="2010">2010</option>
									<option value="2011">2011</option>
									<option value="2012">2012</option>
									<option value="2013">2013</option>
									<option value="2014">2014</option>
									<option value="2015">2015</option>
									<option value="其他">其他</option>
								</select>
							</div>
						</div>
						<div class="row">
							<div class="12u">
								<input class="text" type="text" name="department" id="department" value="" placeholder="院系" />
							</div>
						</div>
						<div class="row">
							<div class="12u">
								<input class="text" type="text" name="contact" id="contact" value="" placeholder="QQ/微信/手机" />
							</div>
						</div>
						<div class="row half">
							<div class="12u">
								<textarea name="experience" id="experience" placeholder="相关经历（512字以内）"></textarea>
							</div>
						</div>
						<div class="row">
							<div class="12u">
								<select name="position" id="position">
									<option value="">所报部门</option>
									<option value="外联部">外联部</option>
									<option value="公共推广部">公共推广部</option>
									<option value="视觉创意部">视觉创意部</option>
									<option value="梦想艺术团项目办">梦想艺术团项目办</option>
									<option value="文化万花筒项目部">文化万花筒项目部</option>
									<option value="周末艺苑项目办">周末艺苑项目办</option>
									<option value="大型活动策划部">大型活动策划部</option>
									<option value="艺术竞技场项目办">艺术竞技场项目办</option>
								</select>
							</div>
						</div>
						<div class="row">
							<div class="12u">
								<ul class="actions">
									<li><input type="submit" id="submit" value="确认" /></li>
								</ul>
							</div>
						</div>
					</form>
				</section>
			</article>
		<script src="js/joinus.js" type="text/javascript"></script>
		</c:when>
	</c:choose>
	
	
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
	
	</body>
</html>