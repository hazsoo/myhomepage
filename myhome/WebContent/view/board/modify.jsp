<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:commonLayout>
	<jsp:body>
		<h1 class="display-4 p-2 lead">글 수정</h1>
		<div>
			<form action="${pageContext.request.contextPath }/board/modify" method="post">
				<div class="form-group">
					<label for="title">제목</label>
					<input type="text" class="form-control" id="title" name="title" placeholder="제목을 입력하세요." value="${dto.title }"/>
				</div>
				
				<div class="form-group">
					<label for="content">내용</label>
					<textarea class="form-control" rows="20" id="content" name="content" placeholder="본문을 입력하세요.">${dto.content }</textarea>
				</div>
				<input type = "hidden" name = "no" value = "${dto.no }"/>
				<button type="submit" class="btn btn-primary">수정</button>
				<button type="button" onclick="history.back()" class="btn btn-primary">취소</button>
			</form>
		</div>
		
	</jsp:body>
</t:commonLayout>
