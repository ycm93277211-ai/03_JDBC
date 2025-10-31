package edu.kh.jdbc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;  
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Student {
	private int 	stdNo;
	private String  stdName;
	private int		stdAge;
	private String	Major;
	private String 	entDate;
}
