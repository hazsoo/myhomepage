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
		
		
		<h1>회원탈퇴 페이지</h1>
		<!-- id, password, nickname 입력 form action="/myhome/user/join"-->
		<form action="delete" method="post" name="signout_form">
		
		  <!-- 아이디 입력란 -->
		  <div class="form-group">
		    <label for="id">Id:</label>
		    <input type="text" class="form-control" placeholder="Enter Id"
					id="id" name="id">
		  </div>
		  
		  <!-- 비밀번호 입력란 -->
		  <div class="form-group">
		    <label for="pwd">Password:</label>
		    <input type="password" class="form-control"
					placeholder="Enter password" id="pwd" name="password">
		  </div>
		  
		  <!--  -->
		  <div class="alert alert-danger" id="error-div"
				style="display: none;">
			  <strong>Wrong!</strong> <p id="error-message" />
		  </div>
		  
		  <button onclick="checkValidation(signout_form)" type="button" class="btn btn-primary">탈퇴</button>
		</form>

	</jsp:body>
</t:commonLayout>
