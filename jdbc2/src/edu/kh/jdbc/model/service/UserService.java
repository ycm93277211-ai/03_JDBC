package edu.kh.jdbc.model.service;

import java.sql.Connection;
import java.util.List;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.model.dao.UserDAO;
import edu.kh.jdbc.model.dto.User;
import lombok.extern.jbosslog.JBossLog;

// (Model 중 하나) Service : 비즈니스 로직을 처리하는 계층,
// 데이터를 가공하고 트랜잭션(commit, rollback) 관리 수행

public class UserService {

	// 필드
	private UserDAO dao = new UserDAO();

	/**
	 * 1. User 등록 서비스
	 * 
	 * @param user : 입력받은 id, pw, name 이 세팅된 객체
	 * @return : insert 된 결과 행의 갯수
	 * @throws Exception
	 */
	public int insertUser(User user) throws Exception {

		// 1. 커넥션 생성
		Connection conn = JDBCTemplate.getConnection();

		// 2. 데이터 가공(할 거 없으면 생략)

		// 3. DAO 메서드 호출 후 결과 반환받기
		int result = dao.insertUser(conn, user);

		// 4. DML(INSERT) 수행 결과에 따라 트랜잭션 제어 처리
		if (result > 0) {// INSERT 성공
			JDBCTemplate.commit(conn);

		} else {// 실패
			JDBCTemplate.rollback(conn);
		}

		// 5. Connection 반환하기
		JDBCTemplate.close(conn);

		// 6. 결과 반환
		return result;
	}

	/**
	 * 2. User 전체 조회 서비스
	 * 
	 * @return : 조회된 User 들이 담긴 List
	 * @throws Exception
	 */
	public List<User> selectAll() throws Exception {

		// 1. 커넥션 생성 (먼저 DB 연결을 얻고)
		Connection conn = JDBCTemplate.getConnection();

		// 2. 데이터 가공 (없음)

		// 3. DAO 메서드 호출 (SELECT) 후 결과반한(List<User>) 받기 (DAO를 호출해 실제 데이터를 조회하고)
		List<User> userList = dao.selectAll(conn);

		// 4. Connection 반환(조회가 끝나면 DB 연결(커넥션)을 닫고)
		JDBCTemplate.close(conn);

		// 5. 결과 반환(조회된 데이터를 View에게 반환합니다)
		return userList;
	}

	/**
	 * 3. User 중 이름에 검색어가 포함된 회원 조회 서비스
	 * 
	 * @param keyword : 입력받은 키워드
	 * @return searchList : 조회된 회원 리스트
	 */
	public List<User> selectName(String keyword) throws Exception {

		Connection conn = JDBCTemplate.getConnection();

		List<User> searchList = dao.selectName(conn, keyword);

		JDBCTemplate.close(conn);

		return searchList;
	}

	public User selectUser(int userNo) throws Exception {

		Connection conn = JDBCTemplate.getConnection();

		User user = dao.selectUser(conn, userNo);

		return user;
	}

	public int deleteUser(int userNo) throws Exception {
		// DB 통로
		Connection conn = JDBCTemplate.getConnection();
		
		
		JDBCTemplate.rollback(conn);
		
		// dao 값 반환 받기
		int user = dao.deleteUser(conn, userNo);

		// 4. DML(INSERT) 수행 결과에 따라 트랜잭션 제어 처리
		if (user > 0) {// INSERT 성공
			JDBCTemplate.commit(conn);

		} else {// 실패
			JDBCTemplate.rollback(conn);
		}

		// 5. Connection 반환하기
		JDBCTemplate.close(conn);

		return user;
	}

}
