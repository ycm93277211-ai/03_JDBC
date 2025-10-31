package edu.kh.jdbc.common;

import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Scanner;

public class CreateXMLFile {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = null;
		FileOutputStream fos = null;

		try {

			sc = new Scanner(System.in);

			System.out.print("생성할 파일 이름 : ");
			String fileName = sc.next();

			// Properties(성질,설정,속성) 객체 생성
			Properties prop = new Properties();

			// FileOutputStream 생성
			fos = new FileOutputStream(fileName + ".xml");

			// Properties 객체를 이용해서 XML 파일 생성
			prop.storeToXML(fos, fileName + ".xml 파일!!!");

			System.out.println(fileName + ".xml 파일 생성 완료!!!");

		} catch (Exception e) {
			System.out.println("XML 파일 생성 중 예외 발생");
			e.printStackTrace();
		} finally {

			try {

				if (fos != null)
					fos.close();
				if (sc != null)
					sc.close();

			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}
	}
	}


