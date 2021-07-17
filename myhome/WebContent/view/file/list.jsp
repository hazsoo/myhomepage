<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:commonLayout>
	<jsp:body>
		
		<c:if test="${not empty result }">
			<script>alert("${result ? '업로드 성공!' : '업로드 실패..'}");</script>
			
			<%-- result 애트리뷰트 삭제 --%>
			<c:remove var="result"/>
		</c:if>
	
		<h1>자료실</h1>
	
		<c:if test="${not empty currentMember }">
		<div align="right">
			<a class="btn btn-outline-light dtn-lg p-2"
				href="${pageContext.request.contextPath}/file/upload">업로드</a>
		</div>
		</c:if>
		
		<table class="table table-hover table-dark">
			<thead>
				<tr>
					<th scope="col" colspan="2">게시자</th>
					<th scope="col" colspan="4">파일명</th>
					<th scope="col" colspan="4">업로드 시간</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="dto" items="${list }">
					<tr onclick="location.href='${pageContext.request.contextPath }/file/download?no=${dto.no }'">
						<td scope="col" colspan="2">${dto.nickname }</td>
						<td scope="col" colspan="4">${dto.filename }</td>
						<td scope="col" colspan="4">${dto.regdate }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		
		
		
	</jsp:body>
</t:commonLayout>