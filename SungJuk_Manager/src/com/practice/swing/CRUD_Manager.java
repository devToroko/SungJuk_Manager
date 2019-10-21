package com.practice.swing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.practice.swing.Check.TextPatternError;

//기본적인 데이터베이스에 대한 CRUD를 수행하는 클래스입니다.
public final class CRUD_Manager {
	
	private CRUD_Manager() {}//객체 생성 방지

	static Connection conn = DBConnection.getConnection();
	
	private static boolean isConnectionNull(Connection conn) {
		if(conn == null) {
			System.out.println("Connection이 Null입니다. 연산을 수행할 수 없습니다.");
			return true;
		}
		return false;
	}
	
	// 조회 (전체)
	public static ArrayList<Student> readAll() {
		
		if(isConnectionNull(conn)) return null;
		
		ArrayList<Student> students = new ArrayList<Student>(20);
		String query = "select * from sungjuk order by id";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				String id = rs.getString(1);
				String name = rs.getString(2);
				int grade = rs.getInt(3);
				int language = rs.getInt(4);
				int english = rs.getInt(5);
				int math = rs.getInt(6);
				int total = rs.getInt(7);
				double avg = rs.getDouble(8);
				
				students.add(new Student(id, name, grade, language, english, math, total, avg));
				
			}
			
		} catch (SQLException e) {
			students = null;
			e.printStackTrace();
		} finally {
			if(rs != null) {try {rs.close();} catch (SQLException e) {/*ignored*/}}
			if(pstmt != null) {try {pstmt.close();} catch (SQLException e) {/*ignored*/}}
		}
		
		return students;
	}
	
	//특정 학생 검색
	public static Student readOne(String id) {
		
		if(isConnectionNull(conn)) return null;
		Student student = null;
		
		String query = "select * from sungjuk where id = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			student = new Student();
			student.setName("Not Found");//만약 rs.next가 하나도 없으면 해당하는 사람이 없다는 뜻이다.
			while(rs.next()) {
				student.setId(rs.getString(1));
				student.setName(rs.getString(2));
				student.setGrade(rs.getInt(3));
				student.setLanguage(rs.getInt(4));
				student.setEnglish(rs.getInt(5));
				student.setMath(rs.getInt(6));
				student.setTotal(rs.getInt(7));
				student.setAvg(rs.getDouble(8));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {try {rs.close();} catch (SQLException e) {/*ignored*/}}
			if(pstmt != null) {try {pstmt.close();} catch (SQLException e) {/*ignored*/}}
		}
		
		return student;//예외면 null, 아무도 못찾으면 name="Not Found" , 제대로 찾으면 student 객체 반환
	
	}
	
	
	// 삽입 (아이디,이름,학년,국어,영어,수학) - 총점과 평균은 Trigger로 만들어 놔서 굳이 계산 안해도 된다.
	public static ResultInfo insert(String id,String name,String grade,String language,String english,String math) {
		if(isConnectionNull(conn)) return new ResultInfo();
		
		String query ="insert into sungjuk(id,name,grade,language,english,math) values(?,?,?,?,?,?)";
		PreparedStatement pstmt = null;
		ResultInfo result = null;
		
		try {
			
			//JTextField에 내용이 정확히 써져있는지 확인을 먼저한다. 맞지 않게 쓰면 TextPatternError를 발생시킨다.
			String[] scores = Check.insertParameter(id, name, grade, language, english, math);
			//평소의 JDBC 작업을 합니다.
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.setInt(3, Integer.parseInt(grade));
			pstmt.setInt(4, Integer.parseInt(scores[0]));
			pstmt.setInt(5, Integer.parseInt(scores[1]));
			pstmt.setInt(6, Integer.parseInt(scores[2]));
			
			int row = pstmt.executeUpdate();

			result = new ResultInfo(true,row);
			
		} catch (TextPatternError e) {	//입력 패턴 오류시

			result = new ResultInfo(false,e.getErrorCode(),e.getMessage());
			
		} catch (SQLException e) {		//기본키, 도메인 무결성 제약조건 등과 같은 DB와 관련된 에러를 잡는다.
			
			result = new ResultInfo(false,e.getErrorCode(),e.getMessage());
			
		} finally {
			if(pstmt != null) {try {pstmt.close();} catch (SQLException e) {/*ignored*/}}
		}
		
		return result;
		
	}
	
	
	// 수정 - 총점과 평균은 Trigger로 미리 연산하도록 해놨다.
	public static ResultInfo update(String id,String language,String english, String math) {
		if(isConnectionNull(conn)) return new ResultInfo();
		
		ResultInfo result = null;
		
		String query = "update sungjuk set LANGUAGE = ? , ENGLISH = ?, MATH = ?  where ID = ?";
		PreparedStatement pstmt = null;
		
		try {
			
			Check.updateParamter(id, language, english, math);
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(language));
			pstmt.setInt(2, Integer.parseInt(english));
			pstmt.setInt(3, Integer.parseInt(math));
			pstmt.setString(4, id);
			
			int row = pstmt.executeUpdate();
			result = new ResultInfo(true,row);
			
		} catch (TextPatternError e) {
			result = new ResultInfo(false,e.getErrorCode(),e.getMessage());
		} catch (SQLException e) {

			result = new ResultInfo(false,e.getErrorCode(),e.getMessage());
			
		}  finally {
			if(pstmt != null) {try {pstmt.close();} catch (SQLException e) {/*ignored*/}}
		}
		
		return result;
		
	}
	
	// 삭제
	public static ResultInfo delete(String id) {
		
		if(isConnectionNull(conn)) return new ResultInfo();

		ResultInfo result = null;
		
		String query = "delete from sungjuk where id = ?";
		
		PreparedStatement pstmt = null;
		
		try {
			
			Check.deleteParameter(id);
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			int row = pstmt.executeUpdate();
			
			result = new ResultInfo(true, row);
			
		} catch (TextPatternError e) {
			
			result = new ResultInfo(false,e.getErrorCode(),e.getMessage());
			
		} catch (SQLException e) {
			
			result = new ResultInfo(false,e.getErrorCode(),e.getMessage());
			
		} finally {
			if(pstmt != null) {try {pstmt.close();} catch (SQLException e) {/*ignored*/}}
		}
		
		return result;
	}
	
	//테스트
	public static void main(String[] args) {
		//읽어오기
//		ArrayList<Student> s = readAll();
//		System.out.println(s);
		
		//삽입하기
//		System.out.println(insert("Z0006", "김홍도","3","97","95","92"));

		//수정하기
//		System.out.println(update("Z0006", "100", "98", "92"));
		
		//삭제하기
//		System.out.println(delete("Az005"));
		
		
	}
}
