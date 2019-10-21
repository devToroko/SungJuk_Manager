package com.practice.swing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//DB의 Connection을 얻기 위한 클래스입니다.
public class DBConnection {
	
	public static Connection getConnection() {
		Connection conn= null;
		try {
			
			String user = "";
			String pw = "";
			// 저는 오라클 11XE를 사용했습니다. 혹시 본인의 DBMS와 맞지 않다면 수정 바랍니다. MySQL에 대한 테스트는 안해봐서
			// 잘될지는 확신이 없습니다.
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			
			String driver = "oracle.jdbc.driver.OracleDriver";
			Class.forName(driver);
			conn = DriverManager.getConnection(url,user,pw);
			
			System.out.println("DataBase에 연결되었습니다");
			
		} catch (ClassNotFoundException e) {
			System.out.println("DB 드라이버 로딩 실패 :");
		} catch (SQLException e) {
			System.out.println("DB 접속 실패: ");
		} catch (Exception e) {
			System.out.println("Unknown Error");
			e.printStackTrace();
		}
		return conn;
	}
	
}
