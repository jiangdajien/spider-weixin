<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<meta content="always" name="referrer">
<title>传智一下_你就知道</title>
<style data-for="result">
body {
	color: #333;
	background: #fff;
	padding: 0;
	margin: 0;
	position: relative;
	min-width: 700px;
	font-family: arial;
	font-size: 12px
}

p, form, ol, ul, li, dl, dt, dd, h3 {
	margin: 0;
	padding: 0;
	list-style: none
}

input {
	padding-top: 0;
	padding-bottom: 0;
	-moz-box-sizing: border-box;
	-webkit-box-sizing: border-box;
	box-sizing: border-box
}

img {
	border: none;
}

.logo {
	width: 117px;
	height: 38px;
	cursor: pointer
}

#wrapper {
	_zoom: 1
}

#head {
	padding-left: 35px;
	margin-bottom: 20px;
	width: 900px
}

.fm {
	clear: both;
	position: relative;
	z-index: 297
}

.btn, #more {
	font-size: 14px
}

.s_btn {
	width: 95px;
	height: 32px;
	padding-top: 2px\9;
	font-size: 14px;
	padding: 0;
	background-color: #ddd;
	background-position: 0 -48px;
	border: 0;
	cursor: pointer
}

.s_btn_h {
	background-position: -240px -48px
}

.s_btn_wr {
	width: 97px;
	height: 34px;
	display: inline-block;
	background-position: -120px -48px;
	*position: relative;
	z-index: 0;
	vertical-align: top
}

#foot {
	
}

#foot span {
	color: #666
}

.s_ipt_wr {
	height: 32px
}

.s_form:after, .s_tab:after {
	content: ".";
	display: block;
	height: 0;
	clear: both;
	visibility: hidden
}

.s_form {
	zoom: 1;
	height: 55px;
	padding: 0 0 0 10px
}

#result_logo {
	float: left;
	margin: 7px 0 0
}

#result_logo img {
	width: 101px
}

#head {
	padding: 0;
	margin: 0;
	width: 100%;
	position: absolute;
	z-index: 301;
	min-width: 1000px;
	background: #fff;
	border-bottom: 1px solid #ebebeb;
	position: fixed;
	_position: absolute;
	-webkit-transform: translateZ(0)
}

#head .head_wrapper {
	_width: 1000px
}

#head.s_down {
	box-shadow: 0 0 5px #888
}

.fm {
	clear: none;
	float: left;
	margin: 11px 0 0 10px
}

#s_tab {
	background: #f8f8f8;
	line-height: 36px;
	height: 38px;
	padding: 55px 0 0 121px;
	float: none;
	zoom: 1
}

#s_tab a, #s_tab b {
	width: 54px;
	display: inline-block;
	text-decoration: none;
	text-align: center;
	color: #666;
	font-size: 14px
}

#s_tab b {
	border-bottom: 2px solid #38f;
	font-weight: bold;
	color: #323232
}

#s_tab a:hover {
	color: #323232
}

#content_left {
	width: 540px;
	padding-left: 121px;
	padding-top: 5px
}

.to_tieba, .to_zhidao_bottom {
	margin: 10px 0 0 121px
}

#help {
	background: #f5f6f5;
	zoom: 1;
	padding: 0 0 0 50px;
	float: right
}

#help a {
	color: #777;
	padding: 0 15px;
	text-decoration: none
}

#help a:hover {
	color: #333
}

#foot {
	position: fixed;
	bottom: 0;
	width: 100%;
	background: #f5f6f5;
	border-top: 1px solid #ebebeb;
	text-align: left;
	height: 42px;
	line-height: 42px;
	margin-top: 40px;
	*margin-top: 0;
	_position: absolute;
	_bottom: auto;
	_top: expression(eval(document.documentElement.scrollTop + 
		 document.documentElement.clientHeight-this.offsetHeight- ( parseInt(this.currentStyle.marginTop
		, 10)||0)-(parseInt(this.currentStyle.marginBottom, 10)||0)));
}

.content_none {
	padding: 45px 0 25px 121px
}

.s_ipt_wr.bg, .s_btn_wr.bg, #su.bg {
	background-image: none
}

.s_ipt_wr.bg {
	background: 0
}

.s_btn_wr {
	width: auto;
	height: auto;
	border-bottom: 1px solid transparent;
	*border-bottom: 0
}

.s_btn {
	width: 100px;
	height: 34px;
	color: white;
	letter-spacing: 1px;
	background: #3385ff;
	border-bottom: 1px solid #2d78f4;
	outline: medium;
	*border-bottom: 0;
	-webkit-appearance: none;
	-webkit-border-radius: 0
}

.s_btn:hover {
	background: #317ef3;
	border-bottom: 1px solid #2868c8;
	*border-bottom: 0;
	box-shadow: 1px 1px 1px #ccc
}

.s_btn:active {
	background: #3075dc;
	box-shadow: inset 1px 1px 3px #2964bb;
	-webkit-box-shadow: inset 1px 1px 3px #2964bb;
	-moz-box-shadow: inset 1px 1px 3px #2964bb;
	-o-box-shadow: inset 1px 1px 3px #2964bb
}

#lg {
	display: none
}

#head .headBlock {
	margin: -5px 0 6px 121px
}

#content_left .leftBlock {
	margin-bottom: 14px;
	padding-bottom: 5px;
	border-bottom: 1px solid #f3f3f3
}

.s_ipt_wr {
	border: 1px solid #b6b6b6;
	border-color: #7b7b7b #b6b6b6 #b6b6b6 #7b7b7b;
	background: #fff;
	display: inline-block;
	vertical-align: top;
	width: 539px;
	margin-right: 0;
	border-right-width: 0;
	border-color: #b8b8b8 transparent #ccc #b8b8b8;
	overflow: hidden
}

.s_ipt_wr.ip_short {
	width: 439px;
}

.s_ipt_wr:hover, .s_ipt_wr.ipthover {
	border-color: #999 transparent #b3b3b3 #999
}

.s_ipt_wr.iptfocus {
	border-color: #4791ff transparent #4791ff #4791ff
}

.s_ipt_tip {
	color: #aaa;
	position: absolute;
	z-index: -10;
	font: 16px/22px arial;
	height: 32px;
	line-height: 32px;
	padding-left: 7px;
	overflow: hidden;
	width: 526px
}

.s_ipt {
	width: 526px;
	height: 22px;
	font: 16px/18px arial;
	line-height: 22px\9;
	margin: 6px 0 0 7px;
	padding: 0;
	background: transparent;
	border: 0;
	outline: 0;
	-webkit-appearance: none
}

#kw {
	position: relative;
	display: inline-block;
}

input::-ms-clear {
	display: none
}
/*Error page css*/
.norsSuggest {
	display: inline-block;
	color: #333;
	font-family: arial;
	font-size: 13px;
	position: relative;
}

.norsTitle {
	font-size: 22px;
	font-family: Microsoft Yahei;
	font-weight: normal;
	color: #333;
	margin: 35px 0 25px 0;
}

.norsTitle2 {
	font-family: arial;
	font-size: 13px;
	color: #666;
}

.norsSuggest ol {
	margin-left: 47px;
}

.norsSuggest li {
	margin: 13px 0;
}
</style>
</head>

<body link="#0000cc">
	<div id="wrapper" class="wrapper_l">
		<div id="head">
			<div class="head_wrapper">
				<div class="s_form">
					<div class="s_form_wrapper">
						<a href="/" id="result_logo"><img
							src="http://www.baidu.com/img/baidu_jgylogo3.gif" alt="到百度首页"
							title="到百度首页"></a>
						<form id="form" action="http://127.0.0.1/search/search.html"
							method="post" class="fm">
							<span class="bg s_ipt_wr ip_short"> <input id="kw"
								name="keyword" class="s_ipt" value="" maxlength="255"
								autocomplete="off" autofocus="">
							</span><span class="bg s_btn_wr"> <input type="submit" id="su"
								value="传智一下" class="bg s_btn">
							</span>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="s_tab" id="s_tab">
			<b>网页</b><a
				href="http://news.baidu.com/ns?cl=2&amp;rn=20&amp;tn=news&amp;word="
				wdfield="word">新闻</a><a
				href="http://tieba.baidu.com/f?kw=&amp;fr=wwwt" wdfield="kw">贴吧</a><a
				href="http://zhidao.baidu.com/q?ct=17&amp;pn=0&amp;tn=ikaslist&amp;rn=10&amp;word=&amp;fr=wwwt"
				wdfield="word">知道</a><a
				href="http://music.baidu.com/search?fr=ps&amp;ie=utf-8&amp;key="
				wdfield="key">音乐</a><a
				href="http://image.baidu.com/i?tn=baiduimage&amp;ps=1&amp;ct=201326592&amp;lm=-1&amp;cl=2&amp;nc=1&amp;ie=utf-8&amp;word="
				wdfield="word">图片</a><a
				href="http://v.baidu.com/v?ct=301989888&amp;rn=20&amp;pn=0&amp;db=0&amp;s=25&amp;ie=utf-8&amp;word="
				wdfield="word">视频</a><a
				href="http://map.baidu.com/m?word=&amp;fr=ps01000" wdfield="word">地图</a><a
				href="http://wenku.baidu.com/search?word=&amp;lm=0&amp;od=0&amp;ie=utf-8"
				wdfield="word">文库</a><a href="//www.baidu.com/more/">更多»</a>
		</div>
		<div id="wrapper_wrapper">
			<div id="content_left">
				<div class="nors">
					<div class="norsSuggest">
						<c:forEach items="${resultList}" var="item" varStatus="s">
							<p>这是一条搜索记录:</p>
							<a href="http://www.itcast.cn">${item.title}</a>
							<a href="http://www.itcast.cn">${item.author}</a>
							<a href="http://www.itcast.cn">http://www.itcast.cn</a>
							<p />
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		<div id="foot">
			<span id="help" style="float: left; padding-left: 121px"> <a
				href="http://help.baidu.com/question" target="_blank">帮助</a> <a
				href="http://www.baidu.com/search/jubao.html" target="_blank">举报</a>
				<a href="http://jianyi.baidu.com" target="_blank">给百度提建议</a>
			</span>
		</div>
	</div>
</body>
</html>