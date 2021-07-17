<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:commonLayout>
	<jsp:body>
		<h1 class="display-4 p-2 lead">글 작성</h1>
		<div>
			<form action="${pageContext.request.contextPath }/board/write" method="post" name="write_form">
				<div class="form-group">
					<label for="title">제목</label>
					<input type="text" class="form-control" id="title" name="title" placeholder="제목을 입력하세요."/>
				</div>
				
				<div class="form-group">
					<label for="content">내용</label>
					<textarea class="form-control" rows="20" id="content" name="content" placeholder="본문을 입력하세요."></textarea>
				</div>
				<button type="submit" class="btn btn-primary">작성</button>
			</form>
		</div>
		
	</jsp:body>
</t:commonLayout>