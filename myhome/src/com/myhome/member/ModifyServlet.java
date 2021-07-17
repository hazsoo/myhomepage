package com.myhome.member;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;
import com.myhome.controller.MyServlet;
import com.myhome.model.MemberDao;
import com.myhome.model.MemberDto;

public class ModifyServlet implements MyServlet{
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		MemberDto currentMember = (MemberDto)session.getAttribute("currentMember");
		
		String id = request.getParameter("id");
		Writer out = null;
		
		response.setCharacterEncoding("utf-8");
		out = response.getWriter();
		
		if(currentMember == null || id == null || !id.equals(currentMember.getId())) {
			out.write("{result:false}");
			return;
		}
		MemberDto dto = new MemberDto();
		dto.setNo(currentMember.getNo());
		dto.setId(id);
		dto.setNickname(request.getParameter("nickname"));
		dto.setPassword(request.getParameter("password"));
		boolean result = MemberDao.getInstance().update(dto);
		
		JsonObject object = new JsonObject();
		if(result) {
			object.addProperty("result", true);
			object.addProperty("new-nickname", dto.getNickname());
		} else {
			object.addProperty("result", false);
		}
		out.write(object.toString());
	}
}




