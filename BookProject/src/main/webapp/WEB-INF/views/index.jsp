<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
<%@include file="include/head.jsp"%>
	<title>INDEX</title>
</head>
<body>
<%@include file="login/loginModal.jsp"%>
<script type="text/javascript">
	$(document).ready(function(){
		$('#loginTag').click(function(){
			$('#loginModal').modal("show");
		});
	});
</script>
	<br>
	<div class="text-center">
		<c:if test="${mem_id == null}">
			<a href="javascript:void(0);" id="loginTag">로그인</a>
		</c:if>
		<c:if test="${mem_id != null}">
			<a>로그아웃</a>
		</c:if>
	</div>
</body>
</html>