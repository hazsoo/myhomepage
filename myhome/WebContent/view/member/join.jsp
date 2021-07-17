<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:commonLayout>
	<jsp:body>
		<script>
			function checkValidation(form) {
				var password1 = document.getElementById("pwd").value;
				var password2 = document.getElementById("pwd2").value;
				console.log("pwd1 : " + password1);
				console.log("pwd2 : " + password2);
				if (password1 == '' || password2 == '') {
					var error = document.getElementById("error-div");
					error.style.display = "block";
					document.getElementById("error-message").innerHTML = "패스워드를 입력하세요.";
					return;
				}

				if (password1 != password2) {
					var error = document.getElementById("error-div");
					error.style.display = "block";
					document.getElementById("error-message").innerHTML = "두 패스워드가 일치하지 않습니다.";
					return;
				} 
				var error = document.getElementById("error-div");
				error.style.display = "none";
				form.submit();
				
			}
		</script>
		
		
		<h1>회원가입 페이지</h1>
		<!-- id, password, nickname 입력 form action="/myhome/user/join"-->
		<form action="join" method="post" name="join_form">
		
			<!-- 아이디 입력란 -->
			<div class="row">
			<div class="form-group col-9">
			  <label for="id">Id:</label>
			  <input type="text" class="form-control " placeholder="Enter Id"
					id="id" name="id">
			</div>
			<div class = "form-group col-3">
				<label for="check-id">중복확인:</label>
				<button id="check-id" class="btn btn-light btn-block" type="button" onclick="checkId()">중복확인</button>
			</div>
			</div>
			 
			 
			 <!-- 닉네임 입력란 -->
			 <div class="form-group">
			   <label for="nickname">Name:</label>
			   <input type="text" class="form-control"
					placeholder="Enter Your Name" id="nickname" name="nickname">
			 </div>
		  
		  
			<!-- 이메일 입력란 
			<div class="form-group">
			 <label for="email">Email address:</label>
			 <input type="email" class="form-control" placeholder="Enter email"
			id="email" name="email">
			</div>-->
			
			<!-- 비밀번호 입력란 -->
			<div class="form-group">
			  <label for="pwd">Password:</label>
			  <input type="password" class="form-control"
				placeholder="Enter password" id="pwd" name="password">
			</div>
			
			<!--  비밀번호 재입력란 -->
			<div class="form-group">
			  <label for="pwd2">Check Password:</label>
			  <input type="password" class="form-control"
				placeholder="Enter password" id="pwd2" name="password2">
			</div>
			
			<!-- 관리자 체크란 -->
			<div class="form-group form-check">
			  <label class="form-check-label">
			    <input class="form-check-input" type="checkbox" name="type"
				value="admin"> 관리자입니다.
			  </label>
			</div>
			
			<!--  -->
			<div class="alert alert-danger" id="error-div"
			style="display: none;">
			 <strong>Wrong!</strong> <p id="error-message" />
			</div>
			
			<div class="alert alert-success" id="success-div"
			style="display: none;">
			 <strong>Wrong!</strong> <p id="success-message" />
			</div>
			
			<!-- 전송 -->
			<button onclick="checkValidation(join_form)" type="button" class="btn btn-primary">가입</button>
		</form>

		<script>
		function checkId() {
			// id 입력란에 있는 텍스트 가져오기 
			// var id = document.getElementById("id").value;
			var id = $('#id').val();
			if (id == ""){
				alert("중복 확인할 아이디를 입력해주세요.");
				$('#id').focus();
				return;
			}
			
			// 텍스트 있으면 비동기 요청 보내기
			$.ajax({
				type:"POST", 
				url:"${pageContext.request.contextPath }/member/check_id",
				data:{id:id},
				success: function (data) { // "true" / "false"
					if(data === "true"){
						// alert("사용 가능한 아이디입니다.");
						var success = document.getElementById("success-div");
						success.style.display = "block";
						document.getElementById("success-message").innerHTML = "사용 가능한 아이디입니다.";
						
						
						var error = document.getElementById("error-div");
						error.style.display = "none";
					}
					else {
						// alert("이미 가입된 아이디입니다.");
						var error = document.getElementById("error-div");
						error.style.display = "block";
						document.getElementById("error-message").innerHTML = "이미 가입된 아이디입니다.";
						
						var success = document.getElementById("success-div");
						success.style.display = "none";
					}
				},
				error: function (data) {
					alert("통신 중 에러 발생..");
				}
			});
		}
		</script>
		


	</jsp:body>
</t:commonLayout>
