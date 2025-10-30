package edu.kh.jdbc.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// import static : 지정된 경로에 존재하는 static 구문을 모두 얻어와
// 클래스명.메서드명() 이 아닌 메서드명() 만 작성해도 호출 가능하게 함.
import static edu.kh.jdbc.common.JDBCTemplate.*; // 호출하지 않고 메서드만 써도 가능!
import edu.kh.jdbc.model.dto.User;

// (Model 중 하나) DAO (Data Access Object : 데이터 접근 객체)
// 데이터가 저장된 곳(DB)에 접근하는 용도의 객체
// -> DB에 접근하여 JAVA에서 원하는 결과를 얻기 위해
// SQL을 수행한 결과를 반환받는 역할
public class UserDAO {

	// 필드
	// - DB 접근 관현 JDBC 객체 참조 변수 선언
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	/**
	 * 1. User 등록 DAO
	 * 
	 * @param conn : DB 연결 정보가 담겨있는 Connection 객체
	 * @param user : 입력받은 id,pw,name 이 세팅된 User 객체
	 * @return : INSERT 결과 행의 갯수
	 */
	public int insertUser(Connection conn, User user) throws Exception {

		// 1. 결과 저장용 변수 선언
		int result = 0;

		try {
			// 2.SQl 작성
			String sql = """
					INSERT INTO TB_USER
					VALUES(SEQ_USER_NO.NEXTVAL,?,?,?,DEFAULT)
					""";

			// 3. PrepardStatement 객체 생성
			pstmt = conn.prepareStatement(sql);

			// 4. ? 위치홀더에 알맞은 값 대입
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getUserPw());
			pstmt.setString(3, user.getUserName());

			// 5. SQL(INSERT) 수행 후(executeUpdate()) 결과(삽입된 행의 갯수) 반환 받기
			result = pstmt.executeUpdate();

		} finally {

			// 6. 사용한 jdbc 객체 자원 반환
			// JDBCTemplate.close(pstmt);
			close(pstmt);
		}

		// 결과 저장용 변수에 저장된 최종 값 반환
		return result;
	}

	/**
	 * 2. User 전체 조회 DAO
	 * 
	 * @param conn
	 * @return : List<User> userList
	 * @throws Exception
	 */
	// 실제로 DB에 접근해서 SQL을 실행하고, 결과를 자바 객체(User)로 변환해서 반환하는 역할입니다.
	// SQL 구문 작성, PreparedStatement 생성, 실행, ResultSet 순회, 자원 반환(닫기) 등이 여기 들어갑니다.

	public List<User> selectAll(Connection conn) throws Exception {

		// 1. 결과 저장용 변수 선언
		List<User> userList = new ArrayList<User>();

		try {
			// 2. SQL 작성
			String sql = """
					SELECT USER_NO,USER_ID,USER_PW,USER_NAME,
					TO_CHAR(ENROLL_DATE,'YYYY"년"MM"월"DD"일"') ENROLL_DATE
					FROM TB_USER
					ORDER BY USER_NO
					""";

			// 3. PreparedStatement 생성
			pstmt = conn.prepareStatement(sql);

			// 4. 우티홀더에 알맞은 값 대입 (없으면 패스)

			// 5. SQL (SELECT) 수행(executeQuery()) 후 결과 반환(ResultSet) 받기
			rs = pstmt.executeQuery();

			// 6. 조회 결과(rs) 를 1행씩 접근하여 컬럼 값 얻어오기
			// 몇 행이 조회 될지 모른다 -> while
			// 무조건 1행만 조회된다 -> if

			while (rs.next()) {

				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");
				// java.sql.Dat 타입으로 값을 저장하지 않은 이유
				// -> SELECT 문에서 TO_CHAR()를 이용하여 문자열 형태로
				// 변환해 조회해왔기 때문.

				// User 객체 새로 생성하여 DB에서 얻어온 컬럼값 필드로 세팅
				User user = new User(userNo, userId, userPw, userName, enrollDate);

				userList.add(user);

			}
		} finally {
			// 7 . 사용한 자원 반환
			close(rs);
			close(pstmt);
		}

		// 조회 결과가 담김 List 반환
		return userList;
	}

	/**
	 * 3. User 중 이름에 검색어가 포함된 회원 조회용 DAO
	 * 
	 * @param conn
	 * @param keyword
	 * @return : searchList
	 */
	public List<User> selectName(Connection conn, String keyword) throws Exception {

		// 결과 저용 변수 서언
		List<User> searchList = new ArrayList<User>();

		try {
			// SQL 작성
			String sql = """
					SELECT USER_NO,USER_ID,USER_PW,USER_NAME,
					TO_CHAR(ENROLL_DATE,'YYYY"년"MM"월"DD"일"') ENROLL_DATE
					FROM TB_USER
					WHERE USER_NAME LIKE '%' || ? || '%'
					ORDER BY USER_NO
					""";

			pstmt = conn.prepareStatement(sql);

			// ? 에 알맞은 값 세팅
			pstmt.setString(1, keyword);

			// DB 수행 후 반환 받기
			rs = pstmt.executeQuery();

			while (rs.next()) {

				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");

				// User 객체 새로 생성하여 DB에서 얻어온 컬럼값 필드로 세팅
				User user = new User(userNo, userId, userPw, userName, enrollDate);

				searchList.add(user);

			}

		} finally {
			close(rs);
			close(pstmt);
		}

		return searchList;
	}

	/**
	 * 4.
	 * 
	 * @param conn
	 * @param userNo
	 * @return
	 */
	public User selectUser(Connection conn, int userNo) throws Exception {

		// 저장용 객체
		User user = null;

		try {

			String sql = """
					SELECT USER_NO,USER_ID,USER_PW,USER_NAME,
					TO_CHAR(ENROLL_DATE,'YYYY"년"MM"월"DD"일"') ENROLL_DATE
					FROM TB_USER
					WHERE USER_NO  = ?
					ORDER BY USER_NO
					""";

			// 3. PrepardStatement 객체 생성
			pstmt = conn.prepareStatement(sql);

			// 4. ? 위치홀더에 알맞은 값 대입
			pstmt.setInt(1, userNo);

			// 5. SQL (SELECT) 수행(executeQuery()) 후 결과 반환(ResultSet) 받기
			rs = pstmt.executeQuery();

			if (rs.next()) {

				int userNo1 = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");
				// java.sql.Dat 타입으로 값을 저장하지 않은 이유
				// -> SELECT 문에서 TO_CHAR()를 이용하여 문자열 형태로
				// 변환해 조회해왔기 때문.

				// User 객체 새로 생성하여 DB에서 얻어온 컬럼값 필드로 세팅
				user = new User(userNo, userId, userPw, userName, enrollDate);

			}

		} finally {
			close(rs);
			close(pstmt);
		}

		return user;
	}

	/**
	 * 5
	 * 
	 * @param conn
	 * @param userNo
	 * @return
	 */
	public int deleteUser(Connection conn, int userNo) throws Exception {

		// 저장용 객체
		int user = 0;

		try {
			String sql = """
					DELETE FROM  TB_USER
					WHERE USER_NO = ?
					""";

			// 3. PrepardStatement 객체 생성
			pstmt = conn.prepareStatement(sql);

			// 4. ? 위치홀더에 알맞은 값 대입
			pstmt.setInt(1, userNo);

			// 5. SQL(INSERT) 수행 후(executeUpdate()) 결과(삽입된 행의 갯수) 반환 받기
			user = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}

		return user;
	}

}
