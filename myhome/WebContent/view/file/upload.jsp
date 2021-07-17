<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:commonLayout>
	<jsp:body>
		<h1 class="display-4 p-2 lead">파일 업로드</h1>
		<form action="upload" method="post" enctype="multipart/form-data">
			<input type="file" name="file1"><br/>
			<input type="file" name="file2">
			<input type="submit"/>
		</form>
	</jsp:body>
</t:commonLayout>