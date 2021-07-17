package com.myhome.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDao implements Dao {
	// 싱글톤 패턴
	private static MemberDao instance;
	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	

	private MemberDao() {}
	
	public static MemberDao getInstance() {
		if(instance == null)
			instance = new MemberDao();
		return instance;
	}
	
	// public MemberDto select(int no)
	// ~> SELECT no, id, password, nickname, type, regdate 
	//	  FROM member
	//    WHERE no = ?
	public MemberDto select(int no) {
		String sql = "SELECT no, id, password, nickname, type, regdate"
					+ " FROM member"
					+ " WHERE no = ?"; 
		MemberDto dto = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, no);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				dto = new MemberDto();
				dto.setNo(resultSet.getInt("no"));
				dto.setId(resultSet.getString("id"));
				dto.setPassword(resultSet.getString("password"));
				dto.setNickname(resultSet.getString("nickname"));
				dto.setType(resultSet.getInt("type"));
				dto.setRegdate(resultSet.getString("regdate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, preparedStatement, resultSet);
		}
		
		return dto;
		
	}
	
	
	// public List<MemberDto> selectAll()
	// ~> SELECT no, id, password, nickname, type, regdate
	//    FROM member
	//    ORDER BY regdate DESC
	public List<MemberDto> selectAll(){
		String sql = "SELECT no, id, password, nickname, type, regdate"
				 + " FROM member"
				 + " ORDER BY regdate DESC";
		List<MemberDto> list = new ArrayList<MemberDto>();
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				MemberDto dto = new MemberDto();
				dto.setNo(resultSet.getInt("no"));
				dto.setId(resultSet.getString("id"));
				dto.setPassword(resultSet.getString("password"));
				dto.setNickname(resultSet.getString("nickname"));
				dto.setType(resultSet.getInt("type"));
				dto.setRegdate(resultSet.getString("regdate"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, preparedStatement, resultSet);
		}
		return list;
	}
	
	
	// public boolean delete(int no)
	// ~> DELETE FROM member
	// 	  WHERE no = ?
	public boolean delete(int no) {
		String sql = "DELETE FROM member"
				+ " WHERE no = ?";
		boolean result = false;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, no);
			result = preparedStatement.executeUpdate() > 0; 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, preparedStatement);
		}
		return result;
	}
	
	
	// public boolean update(MemberDto dto)
	// ~> UPDATE member
	//    SET password = ?, nickname = ? 
	//    WHERE no = ?
	public boolean update(MemberDto dto) {
		String sql = "UPDATE member"
				+ " SET password = ?, nickname = ?"
				+ " WHERE no = ?";
		boolean result = false;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, dto.getPassword());
			preparedStatement.setString(2, dto.getNickname());
			preparedStatement.setInt(3, dto.getNo());
			result = preparedStatement.executeUpdate() > 0; 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, preparedStatement);
		}
		return result;
	}
	
	// public boolean insert(MemberDto dto)
	// ~> INSERT INTO member(id, password, nickname, type)
	//    VALUES(?, ?, ?, ?)
	public boolean insert(MemberDto dto) {
		String sql = "INSERT INTO member(id, password, nickname, type)"
				+ " VALUES(?, ?, ?, ?)";
		boolean result = false;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, dto.getId());
			preparedStatement.setString(2, dto.getPassword());
			preparedStatement.setString(3, dto.getNickname());
			preparedStatement.setInt(4, dto.getType());
			result = preparedStatement.executeUpdate() > 0; 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, preparedStatement);
		}
		return result;
	}
	
	
//	MemberDao 에 login(String id, String pw) 추가하기
//	id, pw 매치되는 회원이 있다면 
//	해당 회원의 모든 정보를 dto 담아서 return
	public MemberDto selectByIdAndPassword(String id, String password) {
		String sql = "SELECT no, id, password, nickname, type, regdate"
				+ " FROM member"
				+ " WHERE id = ? AND password = ?";
		MemberDto dto = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				dto = new MemberDto();
				dto.setNo(resultSet.getInt("no"));
				dto.setId(resultSet.getString("id"));
				dto.setPassword(resultSet.getString("password"));
				dto.setNickname(resultSet.getString("nickname"));
				dto.setType(resultSet.getInt("type"));
				dto.setRegdate(resultSet.getString("regdate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, preparedStatement, resultSet);
		}
		return dto;
	}
	
	public boolean selectById(String id) {
		String sql = "SELECT id FROM member WHERE id = ?";
		boolean result = false;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);
			resultSet = preparedStatement.executeQuery();
			result =resultSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(connection, preparedStatement, resultSet);
		}
		return result;
	}
	
	
}





