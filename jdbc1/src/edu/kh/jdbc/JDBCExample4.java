package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample4 {

	public static void main(String[] args) {

		// 부서명을 입력받아
		// 해당 부서에 근무하는 사원의
		// 사번, 이름, 부서명, 직급명을
		// 직급코드 오름차순 조회

		// [실행화면]
		// 부서명 입력 : 총무부
		// 200 / 선동일 /총무부 /대표

		// 부서명 입력 : 개발팀
		// 일치하는 부서가 없습니다!

		// 힌트 : SQL 에서 ''필요
		// ex) 총무부 입력 -> '청무부'

		// 1. JDBC 객체 참조변수 선언
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		Scanner sc = null;

		try {

			// 2. DriverManager 객체 이용해서 Connection 객체 생성
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:XE";

			String userName = "kh_ycm"; // 사용자 계정명
			String password = "kh1234"; // 계정 비밀번호

			conn = DriverManager.getConnection(url, userName, password);

			// 3. SQl 작성
			// 부서명을 입력받아
			// 해당 부서에 근무하는 사원의
			// 사번, 이름, 부서명, 직급명을
			// 직급코드 오름차순 조회

			sc = new Scanner(System.in);

			System.out.print("부서명 입력 : ");
			String job = sc.next();

			String sql = "SELECT EMP_ID, EMP_NAME,DEPT_TITLE,JOB_NAME " + "FROM EMPLOYEE "
					+ "JOIN  DEPARTMENT ON (DEPT_ID = DEPT_CODE) " 
					+ "JOIN  JOB USING (JOB_CODE) "
					+ "WHERE DEPT_TITLE = " + "'" + job + "' " + "ORDER BY JOB_CODE";

			// 4. Statement 객체 생성
			stmt = conn.createStatement();

			// 5. Statement 객체를 이용하여 SQL 수행 후 결과 반환 받기
			rs = stmt.executeQuery(sql);

			// 6. 조회 결과가 담겨있는 ResultSet을

			int flag = -1;

			while (rs.next()) {
				flag = 1;
				
				String empId = rs.getString("EMP_ID");
				String empName = rs.getString("EMP_NAME");
				String deptTitle = rs.getString("DEPT_TITLE");
				String jobName = rs.getString("JOB_NAME");

				System.out.printf(" %s /  %s /  %s / %s \n", empId, empName, deptTitle, jobName);
			}
			if (flag == -1) {
				System.out.println("일치하는 부서가 없습니다.");
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
