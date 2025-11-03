package edu.kh.jdbc.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.chrono.JapaneseChronology;
import java.util.ArrayList;
import java.util.List;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.model.dto.Student;

public class StudentDAO {

	// 필드
	// - DB 접근 관현 JDBC 객체 참조 변수 선언
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	/**
	 * 1. 새로운 학생 정보 삽입
	 * 
	 * @param conn
	 * @param student
	 * @return
	 * @throws SQLException
	 */
	public int insertStudent(Connection conn, Student student) throws Exception {

		int result = 0;

		try {

			String sql = """
					INSERT INTO KH_STUDENT
					VALUES(SEQ_STD_NO.NEXTVAL,?,?,?,DEFAULT)
					""";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, student.getStdName());
			pstmt.setInt(2, student.getStdAge());
			pstmt.setString(3, student.getMajor());

			result = pstmt.executeUpdate();

		} finally {
			JDBCTemplate.close(pstmt);
		}

		return result;
	}

	/**
	 * 2. 전체 조회
	 * 
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	public List<Student> slsectAll(Connection conn) throws Exception {

		List<Student> studentList = new ArrayList<>();

		try {

			String sql = """
					SELECT  * FROM KH_STUDENT
					""";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				int stdNo = rs.getInt("STD_NO");
				String stdName = rs.getString("STD_NAME");
				int stdAge = rs.getInt("STD_AGE");
				String Major = rs.getString("MAJOR");
				String entDate = rs.getString("ENT_DATE");

				Student student = new Student(stdNo, stdName, stdAge, Major, entDate);

				studentList.add(student);
			}

		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}

		return studentList;
	}

	/**
	 * 3. 학생 정보 수정
	 * 
	 * @param conn
	 * @param student
	 * @return
	 */
	public int updateStudent(Connection conn, Student student) throws Exception {

		int result = 0;

		try {

			String sql = """
					UPDATE KH_STUDENT
					SET STD_NAME = ?,
					STD_AGE = ?,
					MAJOR = ?
					WHERE STD_NO = ?
					""";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, student.getStdName());
			pstmt.setInt(2, student.getStdAge());
			pstmt.setString(3, student.getMajor());
			pstmt.setInt(4, student.getStdNo());

			result = pstmt.executeUpdate();

		} finally {
			JDBCTemplate.close(pstmt);
		}

		return result;
	}

	/**
	 * 4. 학생 정보 삭제
	 * 
	 * @param conn
	 * @param result
	 * @return
	 * @throws Exception
	 */
	public int updateStudent(Connection conn, int result) throws Exception {

		int student = 0;

		try {
			String sql = """
					DELETE FROM KH_STUDENT
					WHERE STD_NO = ?
					""";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, result);

			student = pstmt.executeUpdate();

		} finally {
			JDBCTemplate.close(pstmt);
		}

		return student;
	}

	/**
	 * 5. 특정 전공 조회
	 * 
	 * @param conn
	 * @param stdMajor
	 * @return
	 */
	public List<Student> majorSelect(Connection conn, String stdMajor) throws Exception {

		List<Student> studentList = new ArrayList<>();

		try {

			String sql = """
					SELECT  STD_NO, STD_NAME, STD_AGE, MAJOR, ENT_DATE
					FROM KH_STUDENT
					WHERE MAJOR = ?
					""";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, stdMajor);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				int stdNo = rs.getInt("STD_NO");
				String stdName = rs.getString("STD_NAME");
				int stdAge = rs.getInt("STD_AGE");
				String Major = rs.getString("MAJOR");
				String entDate = rs.getString("ENT_DATE");

				Student student = new Student(stdNo, stdName, stdAge, Major, entDate);

				studentList.add(student);

			}

		} finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);
		}

		return studentList;
	}
}