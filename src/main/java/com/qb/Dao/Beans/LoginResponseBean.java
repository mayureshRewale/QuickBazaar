package com.qb.Dao.Beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseBean {

	private String name;
	private String mobile;
	private String username;
	private String email;
	private String accessToken;
	private String refreshToken;

}
