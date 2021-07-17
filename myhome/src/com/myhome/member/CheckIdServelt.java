package com.myhome.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myhome.controller.MyServlet;
import com.myhome.model.MemberDao;

public class CheckIdServelt implements MyServlet{
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id"); 
		
		// id가 있으면 true, 없으면 false
		boolean result = MemberDao.getInstance().selectById(id);
		
		
		// id가 있으면 false (이미 사용중), 없으면 true (사용가능한 아이디)
		response.getWriter().write(String.valueOf(!result));
		
		
		
		
		
//		String id = request.getParameter("userid");
//		
//		boolean result = MemberDao.getInstance().selectById(id);
//		String message;
//		if(result == true) {
//			message = "사용 가능한 아이디입니다";
//		} else {
//			message = "이미 사용된 아이디입니다";
//			//
//		}
//		response.setCharacterEncoding("UTF-8");
//		response.getWriter().write(message);
	}
}
