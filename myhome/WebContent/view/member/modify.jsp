<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<t:commonLayout>
	<jsp:body>

	<script>
		function checkValidation(form) {
			var password1 = $("#pwd").val();
			var password2 = $("#pwd2").val();
			var nickname = $("nickname").val();
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
			
			if (nickname == '') {
				var error = document.getElementById("error-div");
				error.style.display = "block";
				document.getElementById("error-message").innerHTML = "닉네임을 입력하세요.";
				return;
			} 
			var error = document.getElementById("error-div");
			error.style.display = "none";

			// ajax 실행
			$.ajax({
				type:"POST", 
				url:"${pageContext.request.contextPath }/member/mypage",
				data:$("form").serialize(),
				datatype:"json", // 응답받을 결과의 타입이 json형이다
				success: function (data) { // "true" / "false"
					json = JSON.parse(data); // json을 object로
					console.log(json);
					console.log(json['result']);
					if(json['result']){
						alert("수정 완료!");
						$("#current-user").text(json['new-nickname'])
					}
					else {
						alert("수정 실패..");
					}
				},
				error: function (data) {
					alert("통신 중 에러 발생..");
				}
			});
		}
	</script>
	
	
	<h1>회원정보</h1>
	
	<form action="modify" method="post" name="modify_form">
		<!-- 아이디 수정X -->
		<div class="form-group">
			<label for="id">Id:</label>
			  <input type="text" class="form-control " placeholder="Enter Id"
					id="id" name="id" value="${currentMember.id }" readonly="readonly">
		</div>
		
		<!-- 닉네임 -->
		<div class="form-group">
		   <label for="nickname">Nickname :</label>
		   <input type="text" class="form-control" placeholder="Change Your Name"
		   		 id="nickname" name="nickname" value="${currentMember.nickname }">
		</div>
		
		<!-- 비밀번호 -->
		<div class="form-group">
			<label for="pwd">Password:</label>
			<input type="password" class="form-control" placeholder="Change password" id="pwd" name="password">
		</div>
		
		<div class="form-group">
		  <label for="pwd2">Check Password:</label>
		  <input type="password" class="form-control" placeholder="Check password" id="pwd2" name="password2">
		</div>
		 
		<div class="alert alert-danger" id="error-div" style="display: none;">
		 <strong>Wrong!</strong> <p id="error-message" />
		</div>
		 
		<button onclick="checkValidation(modify_form)" type="button" class="btn btn-primary">수정</button>
		<button onclick="location.href='${pageContext.request.contextPath }'" type="button" class="btn btn-primary">취소</button>
	</form>
	
</jsp:body>
</t:commonLayout>