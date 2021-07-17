package com.myhome.member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myhome.controller.MyServlet;
import com.myhome.model.MemberDao;
import com.myhome.model.MemberDto;

// http://localhost:8080/myhome/member/login url 부여
public class LoginServlet implements MyServlet{

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//id, password 파라미터 받기
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		// id 기억 하는지
		Cookie cookie = new Cookie("id", id);
		if(request.getParameter("remember-id") != null) {
			cookie.setMaxAge(365 * 24 * 60 * 60); // 1년
		} else { // '아이디 기억하기' 체크 안할때
			// 혹시나 있을 수 있는 cookie 삭제
			cookie.setMaxAge(0);
		}
		response.addCookie(cookie);
		
		// message 변수 준비
		String message = "아이디 혹은 비밀번호를 확인해주세요.";
		
		try{
			// Dao 얻어오기
			MemberDao dao = MemberDao.getInstance();
			MemberDto dto = dao.selectByIdAndPassword(id, password);
			if(dto != null){
				message = "어서오세요!" + dto.getNickname() + "님!";
				
				// session 객체 : jsp에는 내장객체로 존재함
				// 서블릿에서는 request 객체를 통해 얻어온다.
				HttpSession session = request.getSession();
				session.setAttribute("currentMember", dto);
				
				// 5분 동안 활동(요청)이 없으면 자동으로 세션 만료(갱신)
				session.setMaxInactiveInterval(5 * 60); 
			}
		} catch(Throwable e){
			e.printStackTrace();
		}
		
		// message 값을 "msg" 라는 이름의 데이터로
		// (request 바구니에 담는다. request scope : 이 페이지(서블릿)와 포워드되는 다음페이지)
		request.setAttribute("msg", message);
		
		
	}
}
