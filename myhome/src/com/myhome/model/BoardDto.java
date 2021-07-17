package com.myhome.model;

/*
 * CREATE TABLE `board` (
 no INT PRIMARY KEY AUTO_INCREMENT, -- 게시판 번호
 title VARCHAR(255) NOT NULL, -- 글 제목
 content TEXT NOT NULL, -- 글 본문
 writer_no INT, -- 작성자의 회원번호 <-- 외래키
 hit INT DEFAULT 0 NOT NULL, -- 조회수
 regdate DATETIME DEFAULT CURRENT_TIMESTAMP -- 등록일자
);
 */

public class BoardDto {
	private int no;
	private String title;
	private String content;
	private int writerNo;
	private int hit;
	private String regdate;
	private String nickname; // 사용자의 닉네임 DB (x)
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getWriterNo() {
		return writerNo;
	}
	public void setWriterNo(int writerNo) {
		this.writerNo = writerNo;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
}
