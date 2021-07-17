<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:commonLayout>
	<jsp:body>
		<h3>회원 목록</h3>
		
		<table class="table table-dark">
			<thead>
				<tr>
					<th scope="col">No</th>
					<th scope="col">Id</th>
					<th scope="col">Nickname</th>
					<th scope="col">Type</th>
					<th scope="col">Registration Date</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="dto" items="${list }">
					<tr>
						<td>${dto.no }</td>
						<td>${dto.id }</td>
						<td>${dto.nickname }</td>
						<td>${dto.type == 0 ? "일반" : "관리자" }</td>
						<td>${dto.regdate }</td>
						<th><button class="btn btn-light" onclick="removeMember(${dto.no})">탈퇴</button></th>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
		<script>
		function removeMember(no){
			$.ajax({
				type : "POST",
				url : "${pageContext.request.contextPath }/admin/delete",
				data : {number:no},
				success : function(data){
					alert(data);
					location.reload();
				},
				error : function(request, status, data){
					alert(data);
				}
			});
		}
		</script>
		
	</jsp:body>
</t:commonLayout>