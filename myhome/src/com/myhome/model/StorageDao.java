package com.myhome.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StorageDao implements Dao {
	
	private static StorageDao instance;
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	
	private StorageDao() {}
	
	public static StorageDao getInstance() {
		if(instance == null) {
			instance = new StorageDao();
		}
		return instance;
	}
	
	/**
	 * 모든 게시글의 모든 정보(StorageDto)를 ArrayList에 담아 return
	 * @return Arraylist
	 */
	public List<StorageDto> selectAll(){
		String sql = "SELECT storage.no, storage.filepath, storage.filename, storage.uploader_no, storage.regdate, member.nickname"
				+ " FROM storage"
				+ " JOIN member"
				+ " ON storage.uploader_no = member.no"
				+ " ORDER BY regdate DESC";
		List<StorageDto> list = new ArrayList<StorageDto>();
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				StorageDto dto = new StorageDto();
				dto.setNo(rs.getInt("no"));
				dto.setFilepath(rs.getString("filepath"));
				dto.setFilename(rs.getString("filename"));
				dto.setUploaderNo(rs.getInt("uploader_no"));
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
	 * no 번 파일의 모든 내용을 StorageDto 객체에 담아서 return
	 * @param no
	 * @return 파일이 담긴 StorageDto 인스턴스
	 */
	public StorageDto select(int no) {
		String sql = "SELECT storage.no, storage.filepath, storage.filename, storage.uploader_no, storage.regdate, member.nickname"
				+ " FROM storage"
				+ " JOIN member"
				+ " ON storage.uploader_no = member.no"
				+ " WHERE storage.no = ?";
		StorageDto dto = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			rs = ps.executeQuery();
			if(rs.next()) {
				dto = new StorageDto();
				dto.setNo(rs.getInt("no"));
				dto.setFilepath(rs.getString("filepath"));
				dto.setFilename(rs.getString("filename"));
				dto.setUploaderNo(rs.getInt("uploader_no"));
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
	 * 
	 * @param dto (파일 DTO)
	 * @return 저장이 잘 되면 true, 실패하면 false
	 */
	public boolean insert(StorageDto dto) {
		String sql = "INSERT INTO storage(filepath, filename, uploader_no)"
				+ " VALUES(?, ?, ?)";
		boolean result = false;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getFilepath());
			ps.setString(2, dto.getFilename());
			ps.setInt(3, dto.getUploaderNo());
			result = ps.executeUpdate() > 0; 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, ps);
		}
		return result;
	}
	
	
}
