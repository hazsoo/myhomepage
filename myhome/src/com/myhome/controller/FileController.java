package com.myhome.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myhome.model.MemberDto;
import com.myhome.model.StorageDao;
import com.myhome.model.StorageDto;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/file/*")
public class FileController extends HttpServlet {
	
	private StorageDao storageDao = StorageDao.getInstance();
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI().replace(request.getContextPath(), "");
		String method = request.getMethod();
		
		switch(requestURI) {
		/*
		 * /file/list : 목록보기
		 *   GET : DB에서 목록 리스트 조회 후 /view/file/list.jsp 로 포워드
		 *   로그인 했을 경우 목록 페이지에 [업로드] 버튼 추가
		 */
		case "/file/list":
				request.setAttribute("list", storageDao.selectAll());
				request.getRequestDispatcher("/view/file/list.jsp").forward(request, response);
			break;
			
		/*
		 * /file/upload : 업로드
		 *   GET : /view/file/upload.jsp 로 포워드
		 *   파일을 /storage 폴더에 저장 
 		 *   storage DB 에 저장 
		 *	 업로드 완료! 혹은 실패! 띄움 [목록으로]
 		 *	 ajax 써도 됨.
		 */
		case "/file/upload":
			switch(method) {
			case "GET":
				request.getRequestDispatcher("/view/file/upload.jsp").forward(request, response);
				break;
			case "POST":
				boolean result = false;
				HttpSession session = request.getSession();
				MemberDto currentMember = (MemberDto)session.getAttribute("currentMember");
				if(currentMember == null) {
					result = false;
				}
				else {
					String directoryPath = request.getServletContext().getRealPath("/storage");
					MultipartRequest mr = new MultipartRequest(
							request,
							directoryPath,
							(int)(4 * 1e+9),
							"utf-8",
							new DefaultFileRenamePolicy());
					File f1, f2 = null;
					f1 = mr.getFile("file1");
					f2 = mr.getFile("file2");
					
					if(f1 == null && f2 == null) {
						result = false;
					}
					if(f1 != null) {
						StorageDto dto = new StorageDto();
						dto.setFilepath(f1.getCanonicalPath());
						dto.setFilename(f1.getName());
						dto.setUploaderNo(currentMember.getNo());
						result = storageDao.insert(dto);
					}
					if(f2 != null) {
						StorageDto dto = new StorageDto();
						dto.setFilepath(f2.getCanonicalPath());
						dto.setFilename(f2.getName());
						dto.setUploaderNo(currentMember.getNo());
						boolean result2 = storageDao.insert(dto);
						result = result || result2;
					}
					
				} // else 끝
				request.setAttribute("result", result);
				request.getRequestDispatcher("/file/list").forward(request, response);
				break;
			}
			break;
		case "/file/download":
			String fileNo = request.getParameter("no");
			if(fileNo == null || fileNo.isEmpty()) {
				request.getRequestDispatcher("/file/list").forward(request, response);
			}
			int no = Integer.parseInt(fileNo);
			StorageDto dto = StorageDao.getInstance().select(no);
			if(dto == null) {
				request.getRequestDispatcher("/file/list").forward(request, response);
			}
			// 스트림 생성
			// 원본 파일과 연결된 스트림
			File file = new File(dto.getFilepath());
			FileInputStream fIn = new FileInputStream(dto.getFilepath());
			
			// 파일 크기와 동일한 길이의 배열 (임시 그릇) 생성
			byte[] bytes = new byte[(int)file.length()];
			
			// 배열에 원본 파일 데이터 모두 담기
			fIn.read(bytes);
			
			// 응답 패킷 준비
			
			// 응답 헤더 초기화
			response.reset(); 
			
			// 바이너리라는 의미의 MIME 타입으로 content-type 헤더 세팅
			response.setContentType("application/octet-stream"); 
			// = response.setHeader("content-type", "application/octet-stream");
			
			// (응답헤더명, 응답헤더값)
			response.setHeader("Content-Disposition", "attachment; filename=" + dto.getFilename());
			
			// 응답 content 넣기
			OutputStream out = response.getOutputStream();
			out.write(bytes);
			out.flush();
			
			fIn.close();
			out.close();
			break;
		default: // 잘못된 요청은 게시판 목록으로 리다이렉트
			response.sendRedirect(request.getContextPath() + "/file/list");
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
