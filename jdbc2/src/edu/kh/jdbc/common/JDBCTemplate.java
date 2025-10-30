package edu.kh.jdbc.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/*
 * JDBCTemplate: JDBC 관련 작업을 위한 코드를 미리 작성해서 제공하는 클래스
 * 
 * - Connection 생성 
 * - AutoCommit false
 * - commit / rollback
 * -- 각종 자원 반환 close()
 *
 * *** 중요 ***
 * 어디서든지 JDBCTemplate 클래스를 
 * 객체로 만들지 않고도 메서드를 사용 할 수 있도록 하기 위해
 * 모든 메서드를 public static 으로 서언
 * 즉, 어디서든지 사용 할 수 있게 모든 메서드를  public static 선언
 * 
 * 
 */
public class JDBCTemplate {
	
	// 필드
	private static Connection conn = null;
	
	// 메서드
	
	/** 호출 시 Connection 객체를 생성하여 호출한 곳으로 반환하는 메서드
	 * + AutoCommit 끄기
	 * @return conn
	 */
	public static Connection getConnection() {
		
		try {
			
			// 이전에 Connection 객체가 만들어져고(존재하고)
			// 아직 close() 된 상태가 아니라면
			// 새로 만들지 않고, 기존 Connection 반환한다
			if(conn != null && !conn.isClosed())return conn;
			
			//  1. properties 객체 생성
			Properties prop = new Properties();
			
			// 2. Properties 가 제공하는 메서드를 이용해서 driver.xml 파일 내용을 읽어오기
			prop.loadFromXML(new FileInputStream("driver.xml"));
			
			// 3. prop에 저장된 값을 이용새서 Connection 객체 생성
			Class.forName(prop.getProperty("driver"));
			
			conn = DriverManager.getConnection( prop.getProperty("url"),
												prop.getProperty("userName"),
												prop.getProperty("password"));
			
			// 4. 만들어진 Connection 에서 AutoCommit 끄기
			conn.setAutoCommit(false);
			
			
			
			
			
			
		} catch (Exception e) {
			System.out.println("커넥션 생성 중 예외 발생");
			e.printStackTrace();;
		}
		return conn;
	}
	
	/** 전달 받은 커넥션에서 수행한 SQL을 commit 하는 메서드
	 * 
	 * 
	 */
	public static void commit(Connection conn) {
		
		try {
			if(conn != null && !conn.isClosed())
				conn.commit();
		} catch (Exception e) {
			System.out.println("커밋 중 예외 발생");
			e.printStackTrace();
		}
	}
	
	/** 전달 받은 커넥션에서 수행한 SQL을 rollback 하는 메서드
	 * @param conn
	 */
	public static void rollback(Connection conn) {
		
		try {
			if(conn != null && !conn.isClosed())
				conn.rollback();
			
		} catch (Exception e) {
			System.out.println("커밋 중 예외 발생");
			e.printStackTrace();
		}
	}
	
	// -------------------------------------------------------------
	
	// Connection, Statement(preparStatement),ResultSet
	
	/** 전달받은 커넥션을 close(자원반환) 하는 메서드
	 */
	public static void close(Connection stmt ) {
		
		try {
			if(stmt  != null && !stmt.isClosed())
				stmt .close();
			
		} catch (Exception e) {
			System.out.println("커밋 중 예외 발생");
			e.printStackTrace();
		}
	}
	
	
	/** 전달 받은 Statement or PreparedStatement 둘다 close() 할 수 있는 메서드
	 * + 다형성의 업캐스팅 적용
	 * -> PreparedStatement 는 Statement의 자식이다
	 */
	public static void close(Statement stmt) {
		
		try {
			if(stmt != null && !stmt.isClosed())
				stmt .close();
			
		} catch (Exception e) {
			System.out.println("Statement stmt() 중 예외 발생");
			e.printStackTrace();
		}
	}
	
	
	/** 전달 받은 ResultSet을 close() 하는 메서드
	 * @param rs
	 */
	public static void close(ResultSet rs) {
		try {
			if(rs != null && !rs.isClosed())
				rs .close();
			
		} catch (Exception e) {
			System.out.println("ResultSet close() 중 예외 발생");
			e.printStackTrace();
		}
	}
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
