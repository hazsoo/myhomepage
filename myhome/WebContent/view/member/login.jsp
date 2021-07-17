<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
    
<t:commonLayout>
	<jsp:body>
		<script>
			function checkValidation(form) {
				var id = document.getElementById("id").value;
				var password = document.getElementById("pwd").value;
				
				if (!password || !id) {
					var error = document.getElementById("error-div");
					error.style.display = "block";
					document.getElementById("error-message").innerHTML = "아이디 혹은 패스워드를 입력하세요.";
					return;
				}
				var error = document.getElementById("error-div");
				error.style.display = "none";
				form.submit();
				
			}
		</script>
		
		
		<h1>로그인 페이지</h1>
		<!-- id, password, nickname 입력 form action="/myhome/user/join"-->
		<form action="login" method="post" name="login_form">
			<!--  login : http://localhost:8080/myhome/member/login -->
			<!-- 아이디 입력란 -->
			<div class="form-group">
			  <label for="id">Id:</label>
			  <input type="text" class="form-control" placeholder="Enter Id"
				id="id" name="id" value=${cookie.id.value }>
			</div>
			
			<!-- 비밀번호 입력란 -->
			<div class="form-group">
			  <label for="pwd">Password:</label>
			  <input type="password" class="form-control"
				placeholder="Enter password" id="pwd" name="password">
			</div>
			
			<!-- 아이디 기억하기 -->
			<div class="form-check">
				<input class="form-check-input" type="checkbox" id="check" name="remember-id" value="true"/>
				<label class="form-check-label" for="check">아이디 기억하기</label>
			</div>
			
			
			<!--  -->
			<div class="alert alert-danger" id="error-div"
			style="display: none;">
			 <strong>Wrong!</strong> <p id="error-message" />
			</div>
			
			<button onclick="checkValidation(login_form)" type="button" class="btn btn-primary">로그인</button>
		</form>

	</jsp:body>
</t:commonLayout>
