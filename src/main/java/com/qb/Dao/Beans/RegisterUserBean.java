package com.qb.Dao.Beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserBean {

	@NotBlank(message = "First name cannot be empty")
	private String firstName;

	@NotBlank(message = "Last name cannot be empty")
	private String lastName;

	@NotBlank(message = "Contact cannot be empty")
	private String mobile;

	@NotBlank(message = "Email address cannot be empty")
	private String email;

	@NotBlank(message = "DOB cannot be empty")
	private String dob;

	@NotBlank(message = "Gender cannot be empty")
	private String gender;

}
