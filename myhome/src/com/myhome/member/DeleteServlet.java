package com.myhome.member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myhome.controller.MyServlet;
import com.myhome.model.MemberDao;
import com.myhome.model.MemberDto;

public class DeleteServlet implements MyServlet {
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//id, password 파라미터 받기
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		// message 변수 준비
		String message = "회원 탈퇴에 실패하였습니다.";
		
		try{
			// Dao 얻어오기
			MemberDao dao = MemberDao.getInstance();
			MemberDto dto = dao.selectByIdAndPassword(id, password);
			if(dto == null){
				message = "아이디 혹은 비밀번호를 다시 확인해주세요.";
			} else{ // 아이디, 비밀번호 맞으면
				boolean result = dao.delete(dto.getNo());
				message = result ? "회원 탈퇴를 완료하였습니다." : "회원 탈퇴에 실패하였습니다.";
			}
		} catch(Throwable e){
			e.printStackTrace();
		}
		
		// message 값을 "msg" 라는 이름의 데이터로
		// request 바구니에 담는다.
		request.setAttribute("msg", message);
		
		// session 갱신 (새로운 세션으로)
		HttpSession session = request.getSession();
		session.invalidate();
	}
}
