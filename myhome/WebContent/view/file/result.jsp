<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:commonLayout>
	<jsp:body>
		<h2>
			<c:choose>
				<c:when test="${status eq 'upload' }">업로드 ${result ? '성공!' : '실패..' }</c:when>
			</c:choose>
		</h2>
		<a class="btn btn-light"
				onclick="location.href='${pageContext.request.contextPath}/file/list'">목록으로</a>
	</jsp:body>
</t:commonLayout>