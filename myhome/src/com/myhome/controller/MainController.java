package com.myhome.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myhome.admin.AdminServlet;
import com.myhome.member.CheckIdServelt;
import com.myhome.member.DeleteServlet;
import com.myhome.member.JoinServlet;
import com.myhome.member.LoginServlet;
import com.myhome.member.LogoutServlet;
import com.myhome.member.ModifyServlet;


@WebServlet({"/member/*", "/admin/*"})
public class MainController extends HttpServlet {
	
	// doGet, doPost 통합
	public void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		
		// /myhome 자르기
		String requestURI = request.getRequestURI().replace(request.getContextPath(), "");
		// http://localhost:8080/myhome/member/login.jsp
		// requestURI : /member/login.jsp
		
		// 분기 처리
		switch(requestURI) {
		/*
		 * 회원 로그인 : /member/login
		 * 	GET - login form 보여줌 (login.jsp)
		 *  POST - 파라미터 받아서 DB 조회 후 결과 페이지(result.jsp)로 이동
		 */
		case "/member/login":{
			if(request.getMethod().equals("GET")) { // GET 요청이면 - login 창
				request.getRequestDispatcher("/view/member/login.jsp").forward(request, response);
			} else { // POST 요청이면 - login 로직 + 결과 뷰
				new LoginServlet().doPost(request, response);
				request.setAttribute("status", "login");
				// login_result.jsp 로 forward (페이지 이동(실행))
				RequestDispatcher rd = request.getRequestDispatcher("/view/member/result.jsp");
				rd.forward(request, response);
			}
			break;
		}
		
		/*
		 * 회원 가입 : /member/join
		 * 	GET - join form 보여줌 (join.jsp)
		 *  POST - 파라미터 받아서 DB 추가 후 결과 페이지(result.jsp)로 이동
		 */
		case "/member/join":{
			switch(request.getMethod()) {
			case "GET":
				request.getRequestDispatcher("/view/member/join.jsp").forward(request, response);
				break;
			case "POST":
				new JoinServlet().doPost(request, response);
				request.setAttribute("status", "join");
				request.getRequestDispatcher("/view/member/result.jsp").forward(request, response);
				break;
			}
			break;
		}
		
		/*
		 * 회원 정보 보기 및 수정: /member/mypage
		 *  GET - 내정보 보기 페이지(modify.jsp) 보여줌 
		 *  POST - 파라미터 받아서 DB 수정 후 결과 메시지 보냄
		 */
		case "/member/mypage":{
			switch(request.getMethod()) {
			case "GET":
				request.getRequestDispatcher("/view/member/modify.jsp").forward(request, response);
				break;
			case "POST":
				new ModifyServlet().doPost(request, response);
				break;
			}
			break;
		}
		
		/*
		 *  회원 탈퇴 : /member/delete
		 *  GET - 회원 탈퇴 form 보여줌 (delete.jsp)
		 *  POST - 파라미터 받아서 DB 조회 및 삭제 후 결과 페이지(result.jsp)로 이동
		 */
		case "/member/delete":{
			switch(request.getMethod()) {
			case "GET":
				request.getRequestDispatcher("/view/member/delete.jsp").forward(request, response);
				break;
			case "POST":
				new DeleteServlet().doPost(request, response);
				request.setAttribute("status", "delete");
				request.getRequestDispatcher("/view/member/result.jsp").forward(request, response);
				break;
			}
			break;
		}
		
		/*
		 * 회원 아이디 중복 확인 : /member/check_id
		 *  POST - 파라미터 받아서 DB 조회 후 결과 메시지 보냄
		 */
		case "/member/check_id":{
			switch(request.getMethod()) {
			case "POST":
				new CheckIdServelt().doPost(request, response);
				break;
			}
			break;
		}
		
		/*
		 * 회원 로그아웃 : /member/logout
		 *  GET - 세션 invalidate() 후 인덱스 페이지(index.jsp)로 이동
		 */
		case "/member/logout":{
			switch(request.getMethod()) {
			case "GET":
				new LogoutServlet().doGet(request, response);
				request.getRequestDispatcher("/").forward(request, response);
				break;
			}
			break;
		}
		
		/*
		 * 관리자 모든 회원 보기 : /admin/manage
		 *  GET - DB 조회 후 관리자 페이지(admin_view.jsp)로 이동
		 */
		case "/admin/manage":{
			switch(request.getMethod()) {
			case "GET":
				new AdminServlet().doGet(request, response);
				request.getRequestDispatcher("/view/admin/admin_view.jsp").forward(request, response);
				break;
			}
			break;
		}
		
		/*
		 * 관리자 회원 강퇴 : /admin/delete
		 *  POST - 파라미터 받아서 DB 삭제 후 메시지 보냄
		 */
		case "/admin/delete":{
			switch(request.getMethod()) {
			case "POST":
				new com.myhome.admin.DeleteServlet().doPost(request, response);
				break;
			}
			break;
		}
		default:{
//			response.sendRedirect("/myhome"); 이름이 바뀔 수도 있으니까
			response.sendRedirect(request.getContextPath()); // Context : 루트
		}
		}
		
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doProcess(request, response);
	}
	
}
