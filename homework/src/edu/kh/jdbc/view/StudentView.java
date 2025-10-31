package edu.kh.jdbc.view;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.model.dto.Student;
import edu.kh.jdbc.model.service.StudentService;

public class StudentView {

	private StudentService service = new StudentService();
	private Scanner sc = new Scanner(System.in);

	public void mainMenu() {

		int input = 0;

		do {
			try {
				System.out.println("\n === 학생 관리 프로그램 ===\n");
				System.out.println("1. 학생등록");
				System.out.println("2. 전체 학생 조회");
				System.out.println("3. 학생 정보 수정");
				System.out.println("4. 학생 삭제");
				System.out.println("5. 전공별 학생 조회");
				System.out.println("0. 프로그램 종료");
				System.out.print("\n번호를 입력하세요 : ");
				input = sc.nextInt();
				sc.nextLine();

				switch (input) {

				case 1:
					insertStudent();
					break;
				case 2:
					slsectAll();
					break;
				case 3:
					updateStudent();
					break;
				case 4:
					deleteStudent();
					break;
				case 5:
					majorSelect();
					break;
				case 0:
					System.out.println("관리 종료");
					break;

				}
				System.out.println("\n-------------------------------------\n");
			} catch (Exception e) {
				e.printStackTrace();
			}

		} while (input != 0);

	}

	/**
	 * 1. 새로운 학생 정보 삽입 (UPDATE)
	 * 
	 */
	private void insertStudent() throws Exception {

		System.out.println("\n ===== 새로운 학생 정보 등록 ===== \n");

		System.out.print("NAME: ");
		String stdName = sc.next();

		System.out.print("AGE: ");
		int stdAge = sc.nextInt();

		System.out.print("MAJOR: ");
		String stdMajor = sc.next();

		Student student = new Student();

		student.setStdName(stdName);
		student.setStdAge(stdAge);
		student.setMajor(stdMajor);

		int result = service.insertStudent(student);

		if (result > 0) {
			System.out.println("학생 등록을 성공했습니다");
		} else {
			System.out.println("학생 등록이 실패했습니다");
		}

	}

	/**
	 * 2. 모든 학생 정보 조회 (SELECT)
	 * 
	 */
	private void slsectAll() throws Exception {

		System.out.println("\n ==== 학생 정보 전체 조회==== \n");

		List<Student> studentList = new ArrayList<>();

		studentList = service.slsectAll();

		for (Student std : studentList) {

			System.out.println(std);
		}

	}

	/** 3. 이름,나이,전공 변경 가능 (UPDATE)
	 * 
	 */
	private void updateStudent() throws Exception{
		System.out.println("\n === 수정 할 학생 번호를 입력해주세요\n");
		
		
		System.out.print("수정할 학생 번호 입력 : ");
		int stdNo = sc.nextInt();
		sc.nextLine();
			
		
		System.out.print("수정할 이름: ");
		String stdName = sc.next();
		
		System.out.print("수정할 나이: ");
		int stdAge = sc.nextInt();
		
		System.out.print("수정할 학과: ");
		String Major = sc.next();
		
		Student student = new Student();
		student.setStdNo(stdNo);
		student.setStdName(stdName);
		student.setStdAge(stdAge);
		student.setMajor(Major);
		
		int result = service.updateStudent(student);
		
		
		if(result>0) {
			System.out.println("수정 성공");
		}else {
			System.out.println("수정 실패");
		}
		
		
	}

	/**
	 * 4. 학번 기준 삭제 (DELETE)
	 * 
	 */
	private void deleteStudent() {
		// TODO Auto-generated method stub

	}

	/**
	 * 5. 특정 전공 학생만 필터링 조회 (SELECT)
	 * 
	 */
	private void majorSelect() {
		// TODO Auto-generated method stub

	}

}
