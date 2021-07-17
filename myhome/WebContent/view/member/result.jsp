<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:commonLayout>
	<jsp:body>
	
		<%-- 회원가입? --%>
		<c:if test="${status eq 'join' }">
			<c:if test="${result }">
				<script>alert('회원가입에 감사드립니다.');</script>
				<a class="btn btn-outline-light" href="login">로그인하러 가기!</a>
			</c:if>
			<c:if test="${!result }">
				<script>alert('회원가입에 실패하였습니다.');</script>
				<button class="btn btn-outline-light" onclick="history.back">뒤로가기!</button>
			</c:if>
		</c:if>
		
		<%-- 로그인? --%>
		<c:if test="${status eq 'login' }">
			<h1 class="display-6">${msg }</h1>
		
			<c:if test="${currentMember != null }">
				<a class="btn btn-outline-light" href="delete">회원탈퇴</a>
			</c:if>
		</c:if>
		
		<%-- 탈퇴? --%>
		<c:if test="${status eq 'delete' }">
			<h1 class="display-6">${msg }</h1>
		</c:if>
		
	</jsp:body>
</t:commonLayout>
