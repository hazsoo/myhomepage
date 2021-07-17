<%@tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
	<title>My Homepage!</title>
	
	<!--  config -->
	<meta charset="UTF-8"/>
	<meta name = "viewport" content="width=device-width, initial-scale=1"/>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	<link rel="stylesheet" href="css/common.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
	<script src="js/common.js"></script>
	
	<style>
	.fakeimg {
		height: 200px;
		background: #aaa;
	}
	</style>
</head>

<body class="container-fluid bg-dark">
	
	<!-- header -->
	<div id="header-container" class="jumbotron text-center bg-dark text-white"
		style="margin-bottom:0">
		
		<h1 class="display-2">My Home!</h1>
		<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
			<a id="current-user" class="navbar-brand">${currentMember == null ? "로그인 하세요." : currentMember.nickname}</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse" data-taget="#nav-items">
				<span class="navbar-toggler-icon"></span>
			</button>
			
			
			<c:choose>
				<c:when test="${currentMember == null}">
					<div class="collapse navbar-collapse" id="nav-items">
						<ul class="navbar-nav">
							<li class="nav-item">
								<a class="nav-link active" href="${pageContext.request.contextPath }/member/login">Login</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="${pageContext.request.contextPath }/member/join">Join</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="${pageContext.request.contextPath }/board/list?page=1">Board</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="${pageContext.request.contextPath }/file/list">Files</a>
							</li>
						</ul>
					</div>
				</c:when>
				<c:when test="${currentMember != null && currentMember.type == 0}">
					<div class="collapse navbar-collapse" id="nav-items">
						<ul class="navbar-nav">
							<li class="nav-item">
								<a class="nav-link" href="${pageContext.request.contextPath }/member/logout">Logout</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="${pageContext.request.contextPath }/member/mypage">MyPage</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="${pageContext.request.contextPath }/board/list?page=1">Board</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="${pageContext.request.contextPath }/file/list">Files</a>
							</li>
						</ul>
					</div>
				</c:when>
				<c:when test="${currentMember != null && currentMember.type == 1}">
					<div class="collapse navbar-collapse" id="nav-items">
						<ul class="navbar-nav">
							<li class="nav-item">
								<a class="nav-link" href="${pageContext.request.contextPath }/member/logout">Logout</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="${pageContext.request.contextPath }/member/mypage">MyPage</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="${pageContext.request.contextPath }/board/list?page=1">Board</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="${pageContext.request.contextPath }/file/list">Files</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="${pageContext.request.contextPath }/admin/manage">Manage</a>
							</li>
						</ul>
					</div>
				</c:when>
			</c:choose>
			
			
			
		</nav>
	</div>
	
	<!-- main -->
	<div class="container-fluid bg-dark text-white" style="margin-top:30px">
		<div class="row">
			<!-- side bar -->
			<div id="sidebar-container" class="col-sm-2">
				<h2>About Me</h2>
				<h5>Photo of me:</h5>
				<div class="fakeimg">Fake Image</div>
				<p>Some text about me in culpa qui officia deserunt mollit
					anim..</p>
				<h3>Some Links</h3>
				<p>Lorem ipsum dolor sit ame.</p>
				<ul class="nav nav-pills flex-column">
					<li class="nav-item"><a class="nav-link active" href="#">Active</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="#">Link</a></li>
					<li class="nav-item"><a class="nav-link" href="#">Link</a></li>
					<li class="nav-item"><a class="nav-link disabled" href="#">Disabled</a>
					</li>
				</ul>
				<hr class="d-sm-none">
			</div>
			
			<!-- main(content) -->
			<div id="main-container" class="col-sm-8"><jsp:doBody/></div>
		</div>
	</div>
	
	<!-- footer -->
	<div id="footer-container" class="footer jumbotron-fluid text-center p-3 bg-dark text-white">
		<div class="container">
			<p>&copy;2021. Jisoo Ha. All Rights Reserved.</p>
		</div>
	
		
	</div>
	

</body>

</html>