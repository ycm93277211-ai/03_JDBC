package edu.kh.jdbc.model.dto;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString


public class Student {
	private int 	stdNo;
	private String  stdName;
	private int		stdAge;
	private String	Major;
	private String 	entDate;
	
	
	public Student() {
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "Student [stdNo=" + stdNo + ", stdName=" + stdName + ", stdAge=" + stdAge + ", Major=" + Major
				+ ", entDate=" + entDate + "]";
	}


	public int getStdNo() {
		return stdNo;
	}


	public void setStdNo(int stdNo) {
		this.stdNo = stdNo;
	}


	public String getStdName() {
		return stdName;
	}


	public void setStdName(String stdName) {
		this.stdName = stdName;
	}


	public int getStdAge() {
		return stdAge;
	}


	public void setStdAge(int stdAge) {
		this.stdAge = stdAge;
	}


	public String getMajor() {
		return Major;
	}


	public void setMajor(String major) {
		Major = major;
	}


	public String getEntDate() {
		return entDate;
	}


	public void setEntDate(String entDate) {
		this.entDate = entDate;
	}


	public Student(int stdNo, String stdName, int stdAge, String major, String entDate) {
		super();
		this.stdNo = stdNo;
		this.stdName = stdName;
		this.stdAge = stdAge;
		Major = major;
		this.entDate = entDate;
	}
	
	
	
}
