<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>传智一下，你就知道</title>
<style type="text/css">
#thead {
	word-spacing: 15px;
	font-weight: bold;
	/*color: gray;*/
	font-size: 13px;
	margin-top: 20.5px;
	margin-right: 8px;
	font-family: 宋体；
}

.footer-top {
	word-spacing: 20px;
	font-weight: bold;
	font-size: 10px;
	margin-top: 28px;
	font-family: 宋体;
	color: darkgray;
}

#tag3 {
	/*word-spacing: 20px;
*/
	font-weight: bold;
	font-size: 10px;
	margin-top: 0;
	font-family: 宋体;
	color: gray;
}

#list {
	background: #38f;
	border: 0;
	height: 25px;
	width: 60px;
	color: #fff;
}

#bg {
	margin-top: 92px;
}

#bg2 {
	margin-top: 280px;
}

#phoneb {
	margin-top: 5px;
	font-size: 12px;
	font-weight: bold;
	color: gray;
}

#baid {
	background: #38f;
	border: 0;
	height: 35px;
	width: 100px;
	color: #fff;
	font-size: 15px;
}

#search {
	width: 530px;
	height: 30px;
	padding-right: 0;
}

#tbox a {
	color: #000000;
}

#stbox a {
	color: gray;
}
//
link常规 /*a:visited{ color:black; text-decoration:none ；} //访问过后
            a:hover{ color:red; text-decoration:none ；} //鼠标指上去的时候
*/
</style>
</head>
<body>
	<div id="sbox">
		<div id="bg" align="center">
			<img src="http://www.baidu.com/img/bdlogo.gif" id="pic1" alt="百度log">
			<!--  <img src="http://www.itcast.cn/images/logo.png" id="pic1" alt="百度log">-->
		</div>
		<div id="inp1" align="center">
			<form action="http://127.0.0.1/search/search.html" method="post">
				<input class="input" type="text" name="keyword" id="search" value="">
				<input type="submit" value="传智一下">
			</form>
		</div>
	</div>
	<div id="stbox">
		<div id="bg2" align="center">
			<img src="" id="pic2">
		</div>
		<div class="footer-top" align="center">
			<span> <a href="#" target="_blank">把传智设为首页</a> <a href="#"
				target="_blank">关于传智</a> <a href="#" target="_blank">AboutChuanZhi</a>
				<a href="#" target="_blank">传智推广</a>
			</span>
		</div>
		<div id="tag3" align="center">
			<span>
				<p>
					此项目为<a href="http://www.itcast.cn">传智播客</a>搜索课程演示项目
				</p>
			</span>
		</div>
	</div>
</body>
</html>