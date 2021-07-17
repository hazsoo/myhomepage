<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:commonLayout>
	<jsp:body>

		<button class="btn btn-light col-2 mb-3"
			onclick="location.href='${pageContext.request.contextPath}/board/list'">목록으로</button>
	
		<h1 class="display-4">${dto.title }</h1>
		<hr class="bg-light" />
  		<div class="row">
  			<div class="col-2">
	  		<span class="font-weight-light">조회수</span>
	  		<strong>${dto.hit }</strong>
	  		</div>
	  		<div class="col-3">
	  		<span class="font-weight-light">작성자</span>
	  		<strong>${dto.nickname }</strong>
	  		</div>
	  		<div class="col-7">
	  		<span class="font-weight-light">등록일자</span>
	  		<strong>${dto.regdate }</strong>
	  		</div>
	  		
  		</div>
  		
  		<hr class="bg-light" />
		
		<div class="row">
			<p class="lead col-12">${dto.content }</p>
		</div>
		
		
		<c:if test="${dto.writerNo == currentMember.no }">
		
			<div class="btn-group btn-group-toggle" data-toggle="buttons">
			
			  <%-- 수정 --%>
			  <label class="btn btn-secondary" onclick="location.href='${pageContext.request.contextPath}/board/modify?no=${dto.no }'">
			    수정
			  </label>
			  
			  <%-- 삭제 --%>
			  <label class="btn btn-danger" onclick="popupModal()">
			    삭제
			  </label>
			</div>
		</c:if>
		
	<!-- 삭제하시겠습니까? modal -->
	<div class="modal" tabindex="-1" role="dialog" id ="exampleModal">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title text-body">글 삭제</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body text-body">
	        <p>정말 삭제하시겠습니까?</p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">안 할래요.</button>
	        <button type="button" class="btn btn-danger" id="yes-option">삭제할래요.</button>
	      </div>
	    </div>
	  </div>
	</div>
		
	<script>
	$(document).ready(function(){
		$("#yes-option").click(function() {
			location.href='${pageContext.request.contextPath}/board/delete?no=${dto.no }';
		});
	});
	
	
	function popupModal() {
		$('#exampleModal').modal('show');
	}
	
	</script>
		
	</jsp:body>
</t:commonLayout>