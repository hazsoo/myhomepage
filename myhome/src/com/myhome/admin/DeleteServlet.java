package com.myhome.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myhome.controller.MyServlet;
import com.myhome.model.MemberDao;

public class DeleteServlet implements MyServlet  {
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int no = Integer.parseInt(request.getParameter("number"));
		
		boolean result = MemberDao.getInstance().delete(no);
		
		// "회원 삭제를 완료하였습니다"
		// "회원 삭제에 실패하였습니다"
		String message = result ? "회원 삭제를 완료하였습니다" : "회원 삭제에 실패하였습니다";
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(message);
		
	}
}
