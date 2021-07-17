package com.myhome.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDao implements Dao {
	// 싱글톤 패턴
	private static BoardDao instance;
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;

	private BoardDao() {}
	
	public static BoardDao getInstance() {
		if(instance == null)
			instance = new BoardDao();
		return instance;
	}
	
	/**
	 * @param dto (게시글 DTO)
	 * @return 저장이 잘 되면 true, 실패하면 false
	 */
	public boolean insert(BoardDto dto) {
		String sql = "INSERT INTO board(title, content, writer_no)"
				+ " VALUES(?, ?, ?)";
		boolean result = false;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getTitle());
			ps.setString(2, dto.getContent());
			ps.setInt(3, dto.getWriterNo());
			result = ps.executeUpdate() > 0; 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, ps);
		}
		return result;
	}
	
	/**
	 * dto 에 담긴 no 번 게시글의
	 * dto 에 담긴 title(제목), content(본문) 으로 update
	 * @param dto (게시글 DTO)
	 * @return 완료는 true, 실패는 false
	 */
	public boolean update(BoardDto dto) {
		String sql = "UPDATE board "
				+ " SET title = ?, content = ?"
				+ " WHERE no = ?";
		boolean result = false;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getTitle());
			ps.setString(2, dto.getContent());
			ps.setInt(3, dto.getNo());
			result = ps.executeUpdate() > 0; 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, ps);
		}
		return result;
	}
	
	/**
	 * no 번 게시글 삭제
	 * @param no 삭제할 게시글 번호
	 * @return 완료는 true, 실패는 false
	 */
	public boolean delete(int no) {
		String sql = "DELETE FROM board"
				+ " WHERE no = ?";
		boolean result = false;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			result = ps.executeUpdate() > 0; 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, ps);
		}
		return result;
	}
	
	/**
	 * no 번 게시글의 모든 내용을 BoardDto 객체에 담아서 return
	 * @param no
	 * @return 게시글 내용이 담긴 BoardDto 인스턴스
	 */
	public BoardDto select(int no) {
		String sql = "SELECT board.no, board.title, board.content, board.writer_no, board.hit, board.regdate, member.nickname"
				+ " FROM board"
				+ " INNER JOIN member"
				+ " ON board.writer_no = member.no"
				+ " WHERE board.no = ?";
		BoardDto dto = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			rs = ps.executeQuery();
			if(rs.next()) {
				dto = new BoardDto();
				dto.setNo(rs.getInt("no"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setWriterNo(rs.getInt("writer_no"));
				dto.setHit(rs.getInt("hit"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setNickname(rs.getString("nickname"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, ps, rs);
		}
		return dto;
	}
	
	/**
	 * 모든 게시글의 모든 정보(BoardDto)를 ArrayList에 담아 return
	 * (본문 제외)
	 * @return ArrayList
	 */
	/*
SELECT board.no, board.title, board.content, board.writer_no, board.hit, board.regdate, member.nickname 
FROM board 
INNER JOIN member 
ON board.writer_no = member.no 
ORDER BY board.regdate DESC;
	 */
	public List<BoardDto> selectAll(int page){
		String sql = "SELECT brd.*, wr.nickname"
				+ " FROM ("
				+ "	SELECT b.no, b.title, b.writer_no, b.regdate, b.hit"
				+ "	FROM board AS b"
				+ "	JOIN ("
				+ "		SELECT no FROM board"
				+ "		ORDER BY regdate DESC"
				+ "		LIMIT ?, 10"
				+ "	) AS tmp"
				+ "	ON tmp.no = b.no) AS brd"
				+ " JOIN ("
				+ "	SELECT no, nickname FROM member"
				+ " ) AS wr"
				+ " ON brd.writer_no = wr.no";
		List<BoardDto> list = new ArrayList<BoardDto>();
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, (page - 1) * 10); // 1 -> 0 / 2 -> 10 / 3 -> 20
			rs = ps.executeQuery();
			while(rs.next()) {
				BoardDto dto = new BoardDto();
				dto.setNo(rs.getInt("no"));
				dto.setTitle(rs.getString("title"));
				dto.setWriterNo(rs.getInt("writer_no"));
				dto.setHit(rs.getInt("hit"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setNickname(rs.getString("nickname"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, ps, rs);
		}
		return list;
	}
	
	/**
	 * no 번 게시글의 조회수(hit)를 1 증가
	 * @param no 게시글 번호
	 */
	public void updateHit(int no) {
		String sql = "UPDATE board"
				+ " SET hit = hit + 1"
				+ " WHERE no = ?";
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, ps);
		}
	}
	
	/**
	 * 
	 * @return 전체 글 개수
	 */
	public int selectCount() {
		String sql = "SELECT COUNT(*) FROM board";
		int count = 0;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, ps, rs);
		}
		return count;
	}
	
}
