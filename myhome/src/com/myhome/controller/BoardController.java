package com.myhome.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myhome.model.BoardDao;
import com.myhome.model.BoardDto;
import com.myhome.model.MemberDao;
import com.myhome.model.MemberDto;


@WebServlet("/board/*")
public class BoardController extends HttpServlet {
	
	private BoardDao boardDao = BoardDao.getInstance();
	
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String requestURI = request.getRequestURI().replace(request.getContextPath(), "");
		String method = request.getMethod();
		MemberDto currentMember = (MemberDto)request.getSession().getAttribute("currentMember"); // 세션 검사 (로그인 여부)
		
		switch(requestURI) {
		/*
		 * /board/list : 목록보기
		 *   GET : DB에서 목록 리스트 조회 후 /view/board/list.jsp 로 포워드
		 *   로그인 했을 경우 목록 페이지에 [글쓰기] 버튼 추가
		 */
		case "/board/list":{
			switch(method) {
			case "GET":
				String sPage = request.getParameter("page");
				int page = sPage != null ? Integer.parseInt(sPage) : 1;
				request.setAttribute("list", boardDao.selectAll(page));
				request.setAttribute("lastPage", boardDao.selectCount() / 10 + 1);
				request.getRequestDispatcher("/view/board/list.jsp").forward(request, response);
				break;
			}
			break;
		}
		/*
		 * /board/read : 본문보기
		 *   GET : 글번호를 파라미터로 받아서 DB 조회 후 /view/board/read.jsp 로 포워드
		 *   본문글쓴이(writer_no)와 로그인 중인 회원의 번호(currentMember.getNo())가 같으면 [삭제하기][수정하기] 버튼 추가
		 */
		case "/board/read":{
			switch(method) {
			case "GET":
				// 글번호 파라미터명 : no
				int no = Integer.parseInt(request.getParameter("no"));
				String cookieName = (currentMember != null ? currentMember.getNo() : "anonymous") + "_" + no;
				
				Cookie[] cookies = request.getCookies();
				boolean find = false;
				for(Cookie cookie : cookies) {
					// "회원번호_글번호" 라는 이름의 쿠키가 있는 지 확인
					if(cookie.getName().equals(cookieName)) {
						find = true;
						break;
					}
				}
				
				// 쿠키가 없었니?
				if(!find) {
					boardDao.updateHit(no);
					Cookie cookie = new Cookie(cookieName, "");
					cookie.setPath("/");
					cookie.setMaxAge(24 * 60 * 60); // 수명 1일
					response.addCookie(cookie);
				}
				request.setAttribute("dto", boardDao.select(no));
				request.getRequestDispatcher("/view/board/read.jsp").forward(request, response);
				break;
			}
			break;
		}
		/*
		 * /board/write : 글쓰기
		 *   (로그인 한 사람만 글쓰기 가능)
		 *   GET : 세션 검사(로그인 여부) 후 로그인을 했다면 /view/board/write.jsp 로 포워드 (글쓰기 form 페이지)
		 *   	   로그인 안했다면 목록페이지로 리다이렉트
		 *   POST : 파라미터를 받아서 Dto에 포장한 후 DB 에 추가하기
		 *   	방법1) AJAX 로 파라미터를 받은 경우
		 *   		"글쓰기 성공!" 혹은 "글쓰기 실패.."를 alert()로 띄우기 
		 *   	방법2) 일반 요청인 경우
		 *   		/view/board/result.jsp 로 포워드
		 *   		이 결과 페이지에서 "글쓰기 성공!" 혹은 "글쓰기 실패.."를 <h2> 로 출력
		 *   		+ [목록으로] 버튼 추가
		 */
		case "/board/write":{
			switch(method) {
			case "GET":
				if(currentMember == null) {
					response.sendRedirect(request.getContextPath() + "/board/list");
					return;
				}
				request.getRequestDispatcher("/view/board/write.jsp").forward(request, response);
				break;
			case "POST":
				// TODO 글쓰기 뷰에서 파라미터 받기
				BoardDto dto = new BoardDto();
				dto.setTitle(request.getParameter("title"));
				dto.setContent(request.getParameter("content").replaceAll("\\n|\\r\\n", "<br/>"));
				dto.setWriterNo(currentMember.getNo());
				boolean result = boardDao.insert(dto);
				request.setAttribute("result", result);
				request.setAttribute("status", "write");
				request.getRequestDispatcher("/view/board/result.jsp").forward(request, response);
				break;
			}
			break;
		}
		/*
		 * /board/delete : 삭제하기
		 *   GET : 세션 검사 후 일치하면 DB 삭제 후 /view/board/result.jsp 로 포워드
		 *   		"삭제 성공" 혹은 "삭제 실패"를 <h2> 로 출력
		 */
		case "/board/delete":{
			switch(method) {
			case "GET":
				// currentMember 의 no 삭제할 글의 writer_no 가 일치하는지
				int no = Integer.parseInt(request.getParameter("no"));
				BoardDto dto = boardDao.select(no);
				if(currentMember.getNo() != dto.getWriterNo()) {
					request.setAttribute("result", false);
				} else {
					request.setAttribute("result", boardDao.delete(no)); 
				}
				request.setAttribute("status", "delete");
				request.getRequestDispatcher("/view/board/result.jsp").forward(request, response);;
				break;
			}
			break;
		}
		/*
		 * /board/modify : 수정하기
		 *	 GET : DB 에서 원본 Dto를 받아온 후 /view/board/modify.jsp 로 포워드
		 *		뷰페이지 : 원본을 기본값으로 보여주되 제목, 본문만 수정 가능
		 *	 POST : <form> 으로부터 수정된 파라미터를 받아 Dto로 포장한 뒤
		 *			DB 수정 실행 후 /view/board/result.jsp 로 포워드
		 *		방법1) AJAX 로 파라미터를 받은 경우
		 *   		"글 수정 성공!" 혹은 "글 수정 실패.."를 alert()로 띄우기 
		 *   	방법2) 일반 요쳥인 경우
		 *   		/view/board/result.jsp 로 포워드
		 *   		이 결과 페이지에서 "글 수정 성공!" 혹은 "글 수정 실패.."를 <h2> 로 출력
		 *   		+ [목록으로] 버튼 추가
		 */
		case "/board/modify":{
			switch(method) {
			case "GET":
				// currentMember 의 no 삭제할 글의 writer_no 가 일치하는지
				int no = Integer.parseInt(request.getParameter("no"));
				BoardDto dto = boardDao.select(no);
				// 글주인이 아니면
				if(currentMember.getNo() != dto.getWriterNo()) { 
					// 본문으로 이동
					response.sendRedirect(request.getContextPath() + "/board/read?no=" + no);
					break;
				}
				// 글주인이 맞으면
				dto.setContent(dto.getContent().replace("<br/>", "\n"));
				request.setAttribute("dto", dto);
				request.getRequestDispatcher("/view/board/modify.jsp").forward(request, response);
				break;
			case "POST":
				BoardDto newDto = new BoardDto();
				// TODO 글수정 뷰에서 파라미터 받기
				newDto.setNo(Integer.parseInt(request.getParameter("no")));
				newDto.setTitle(request.getParameter("title"));
				newDto.setContent(request.getParameter("content").replaceAll("\\n|\\r\\n", "<br/>"));
				newDto.setWriterNo(currentMember.getNo());
				request.setAttribute("result", boardDao.update(newDto));
				request.setAttribute("status", "modify");
				request.getRequestDispatcher("/view/board/result.jsp").forward(request, response);
				break;
			}
			break;
		}
		default: // 잘못된 요청은 게시판 목록으로 리다이렉트
			response.sendRedirect(request.getContextPath() + "/board/list");
		}
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doProcess(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		doProcess(req, resp);
	}
}
