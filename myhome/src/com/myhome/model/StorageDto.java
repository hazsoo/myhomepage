package com.myhome.model;

/*
CREATE TABLE storage (
 no int PRIMARY KEY AUTO_INCREMENT,
 filepath VARCHAR(255) NOT NULL,
 filename VARCHAR(255) NOT NULL,
 uploader_no INT,
 regdate DATETIME DEFAULT CURRENT_TIMESTAMP,
 FOREIGN KEY (uploader_no) REFERENCES member(no) ON DELETE CASCADE
);
 */
public class StorageDto {
	private int no;
	private String filepath;
	private String filename;
	private int uploaderNo;
	private String regdate;
	private String nickname;
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public int getUploaderNo() {
		return uploaderNo;
	}
	public void setUploaderNo(int uploaderNo) {
		this.uploaderNo = uploaderNo;
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
