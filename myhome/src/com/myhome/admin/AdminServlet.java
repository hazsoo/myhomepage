package com.myhome.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myhome.controller.MyServlet;
import com.myhome.model.MemberDao;
import com.myhome.model.MemberDto;

public class AdminServlet implements MyServlet  {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 요청한 세션이 관리자가 맞는지
		MemberDto currentDto = (MemberDto)req.getSession().getAttribute("currentMember");
		if(currentDto == null || currentDto.getType() != 1) {
			// 관리자가 아니면, index 페이지로 리다이렉트
			resp.sendRedirect("/myhome");
			return;
		}
		
		// 회원목록 받아옴
		List<MemberDto> list = MemberDao.getInstance().selectAll();
		
		// 회원들 정보 request 바구니 담기
		req.setAttribute("list", list);

	}
}
