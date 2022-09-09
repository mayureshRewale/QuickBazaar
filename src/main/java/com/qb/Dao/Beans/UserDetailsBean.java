package com.qb.Dao.Beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class UserDetailsBean {
	
	private long id;

	private String userName;
	
	private String password;

	private String mobile;

	private String email;
	
	private String userId;

	private String firstName;

	private String middleName;

	private String lastName;
	
	private List<String> roles;

}
