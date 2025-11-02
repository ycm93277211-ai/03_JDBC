package edu.kh.jdbc.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.model.dao.StudentDAO;
import edu.kh.jdbc.model.dto.Student;

public class StudentService {

	// dao로 보내줄 코드
	private StudentDAO dao = new StudentDAO();

	/**
	 * 1. 학생 등록
	 * 
	 * @param student
	 * @return
	 */
	public int insertStudent(Student student) throws Exception {

		Connection conn = JDBCTemplate.getConnection();

		int result = dao.insertStudent(conn, student);

		if (result > 0) {// INSERT 성공
			JDBCTemplate.commit(conn);

		} else {// 실패
			JDBCTemplate.rollback(conn);
		}

		return result;
	}

	/**
	 * 2. 전체 조회
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Student> slsectAll() throws Exception {

		Connection conn = JDBCTemplate.getConnection();

		List<Student> studntList = dao.slsectAll(conn);

		JDBCTemplate.close(conn);

		return studntList;
	}

	/**
	 * 3. 학생 정보 수정
	 * 
	 * @param student
	 * @return
	 */
	public int updateStudent(Student student) throws Exception {

		Connection conn = JDBCTemplate.getConnection();

		 
		int result = dao.updateStudent(conn, student);

		if (result > 0) {// INSERT 성공
			JDBCTemplate.commit(conn);

		} else {// 실패
			JDBCTemplate.rollback(conn);
		}

		return result;
	}

	/**
	 * 4 . 학생 정보 삭제
	 * @param result
	 * @return
	 */
	public int deleteStudent(int result) throws Exception  {
		
		Connection conn = JDBCTemplate.getConnection();
		
		int student = dao.updateStudent(conn, result);
		
		if (student > 0) {// INSERT 성공
			JDBCTemplate.commit(conn);

		} else {// 실패
			JDBCTemplate.rollback(conn);
		}
		
		
		
		
		return  student ;
	}

	/** 5. 특정 전공 조회
	 * 
	 * @param stdMajor
	 * @return
	 */
	public List<Student> majorSelect(String stdMajor) throws Exception {
		
		Connection conn = JDBCTemplate.getConnection();

		List<Student> studntList = dao. majorSelect(conn,stdMajor);

		JDBCTemplate.close(conn);

		return studntList;
	}





}
