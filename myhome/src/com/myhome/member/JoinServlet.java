package com.myhome.member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myhome.controller.MyServlet;
import com.myhome.model.MemberDao;
import com.myhome.model.MemberDto;

public class JoinServlet implements MyServlet{
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");

		MemberDto dto = new MemberDto();
		dto.setId(request.getParameter("id"));
		dto.setNickname(request.getParameter("nickname"));
		dto.setPassword(request.getParameter("password"));
		dto.setType(request.getParameter("type") == null ? 0 : 1);

		MemberDao dao = MemberDao.getInstance();
		// String message = dao.insert(dto) ? "회원가입에 감사드립니다." : "회원가입에 실패하였습니다.";
		boolean result = dao.insert(dto);
		request.setAttribute("result", result);
		
	}
}
