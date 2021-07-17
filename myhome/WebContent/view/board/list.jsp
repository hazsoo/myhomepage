<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:commonLayout>
	<jsp:body>
		
		<style>
		.pagination > li > a, 
		.pagination > li > span {
		  background-color: #2c3e50;
		  border: 1px solid #2c3e50;
		  border-radius: 4px;
		  color: #fff;
		  float: left;
		  font-size: 14px;
		  line-height: 1.42857;
		  margin-right: 5px;
		  padding: 8px 15px;
		  position: relative;
		  text-decoration: none;
		}
		.pagination > li > a.active,
		.pagination > li > a:hover,
		.pagination > li > span:hover,
		.pagination > li > a:focus,
		.pagination > li > span:focus {
		  background-color: #34495e !important;
		  border-color: #2c3e50;
		  color: #fff;
		</style>
	
	
		<h1>게시판</h1>
		
		<div align="right">
		<c:if test="${param.page != 1 }">
			<a class="btn btn-outline-light"
					href="${pageContext.request.contextPath }/board/list?page=${param.page - 1}">이전</a>
		</c:if>
		<c:if test="${param.page != lastPage }">
			<a class="btn btn-outline-light"
					href="${pageContext.request.contextPath }/board/list?page=${param.page + 1}">다음</a>
		</c:if>	
		</div>
		
		<table class="table table-hover table-dark">
			<thead>
				<tr>
					<th scope="col">글번호</th>
					<th scope="col">제목</th>
					<th scope="col">글쓴이</th>
					<th scope="col">작성일</th>
					<th scope="col">조회수</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="dto" items="${list }">
					<tr
						onclick="location.href='${pageContext.request.contextPath }/board/read?no=${dto.no }'">
						<td>${dto.no }</td>
						<td>${dto.title }</td>
						<td>${dto.nickname }</td>
						<td>${dto.regdate }</td>
						<td>${dto.hit }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:if test="${not empty currentMember }">
			<a class="btn btn-light"
				onclick="location.href='${pageContext.request.contextPath}/board/write'">글쓰기</a>
		</c:if>
		
		<nav aria-label="...">
			<ul class="pagination justify-content-center">
				<c:if test="${param.page - 3 gt 0 }">
				<li class="page-item">
					<a class="page-link" href="${pageContext.request.contextPath }/board/list?page=${param.page - 3}">이전</a>
				</li>
				</c:if>
				
				<c:if test="${param.page - 2 gt 0 }">
				<li class="page-item">
					<a class="page-link" href="${pageContext.request.contextPath }/board/list?page=${param.page - 2}">${param.page - 2}</a>
				</li>
				</c:if>
				
				<c:if test="${param.page - 1 gt 0 }">
				<li class="page-item">
					<a class="page-link" href="${pageContext.request.contextPath }/board/list?page=${param.page - 1}">${param.page - 1}</a>
				</li>
				</c:if>
				
				<li class="page-item">
					<a class="page-link active" href="${pageContext.request.contextPath }/board/list?page=${param.page}">${param.page}</a>
				</li>
				
				<c:if test="${param.page + 1 le lastPage }">
				<li class="page-item">
					<a class="page-link" href="${pageContext.request.contextPath }/board/list?page=${param.page + 1}">${param.page + 1}</a>
				</li>
				</c:if>
				
				<c:if test="${param.page + 2 le lastPage }">
				<li class="page-item">
					<a class="page-link" href="${pageContext.request.contextPath }/board/list?page=${param.page + 2}">${param.page + 2}</a>
				</li>
				</c:if>
				
				<c:if test="${param.page + 3 le lastPage }">
				<li class="page-item">
					<a class="page-link" href="${pageContext.request.contextPath }/board/list?page=${param.page + 3}">다음</a>
				</li>
				</c:if>
				
			</ul>
		</nav>
		
	</jsp:body>
</t:commonLayout>