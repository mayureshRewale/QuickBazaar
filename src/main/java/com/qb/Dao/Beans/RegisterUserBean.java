package com.qb.Dao.Beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserBean {
	
	private String firstName;
	
//	private String middleName;
	
	private String lastName;
	
	private String mobile;
	
	private String email;

	private String dob;

	private String gender;

}
