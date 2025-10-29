package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample3 {

	public static void main(String[] args) {

		// 입력받은 최소 급여 이상
		// 입력받은 최대 급여 이하를 받는
		// 사원의 사번, 이름 급여를,
		// 급여 내림차순으로 조회
		// -> 이클립스 콘솔 출력

		// [실행화면]
		// 최소 급여 :
		// 최대 급여 :
		// 사번/이름/급여

		// 1. JDBC 객체 참조변수 선언
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		Scanner sc = null;

		try {
			// 2. DriverManager 객체 이용해서 Connection 객체 생성

			Class.forName("oracle.jdbc.driver.OracleDriver");

			String result = "jdbc:oracle:thin:@localhost:1521:XE";

			String userName = "kh_ycm"; // 사용자 계정명
			String password = "kh1234"; // 계정 비밀번호
			conn = DriverManager.getConnection(result, userName, password);

			// 3. SQl 작성
			// 입력받은 최소 급여 이상
			// 입력받은 최대 급여 이하를 받는
			// 사원의 사번, 이름 급여를,
			// 급여 내림차순으로 조회

			sc = new Scanner(System.in);

			System.out.print("최소 급여: ");
			int input1 = sc.nextInt();
			
			System.out.print("최대 급여: ");
			int input2 = sc.nextInt();

			String sql = "SELECT EMP_ID, EMP_NAME, SALARY" + " FROM EMPLOYEE " + " WHERE SALARY BETWEEN "
					+ input1 + " AND "
					+ input2 + " ORDER BY SALARY DESC";
			// JAVA 13 이상부터 지원하는 TEXT BLOCK(""")문법
//			String sql = """
//						SELECT EMP_ID, EMP_NAME, SALARY
//						FROM EMPLOYEE  
//						WHERE SALARY BETWEEN  """+ input1 +  " AND " + input2 +  " ORDER BY SALARY DESC";
			
			
			

			// 4. Statement 객체 생성
			stmt = conn.createStatement();

			// 5. Statement 객체를 이용하여 SQL 수행 후 결과 반환 받기
			rs = stmt.executeQuery(sql);

			// 6. 조회 결과가 담겨있는 ResultSet을

			while (rs.next()) {

				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				int salary = rs.getInt("SALARY");
				
				System.out.printf(" %s /  %s /  %d원 \n", empId, empName, salary);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 7.사용 완료된 JDBC객체 자원 반환(close)
			// 역순으로 닫아라
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
				if (sc != null)
					sc.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}
