package com.myhome.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface Dao {
	
	String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
	String DB_URL = "jdbc:mysql://localhost:3306/testDB?useUnicode=true&characterEncoding=utf8";
	String USERNAME = "root";
	String PASSWORD = "1234";
	
	default Connection getConnection() throws SQLException {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
	}
	default void close(Connection conn, PreparedStatement ps) {
		close(conn, ps, null);
	}
	default void close(Connection conn, PreparedStatement ps, ResultSet rs) {
		try {
			if (conn != null)
				conn.close();
			if (ps != null)
				ps.close();
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
