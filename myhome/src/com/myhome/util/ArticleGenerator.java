package com.myhome.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myhome.model.BoardDao;
import com.myhome.model.BoardDto;
import com.myhome.model.MemberDao;
import com.myhome.model.MemberDto;

@WebServlet("/gen")
public class ArticleGenerator extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<MemberDto> mList = MemberDao.getInstance().selectAll();
		
		for(int i = 0; i < 200; ++i) {
			MemberDto randomMember = mList.get((int)(Math.random() * mList.size()));
			BoardDto dto = new BoardDto();
			dto.setTitle("나는 " + randomMember.getNickname() + "이다!! (" + i + ")");
			dto.setContent("ㅎㅇㅎㅇ<br/>ㅂㅇㅂㅇ<br/>");
			dto.setWriterNo(randomMember.getNo());
			BoardDao.getInstance().insert(dto);
		}
	}
}
