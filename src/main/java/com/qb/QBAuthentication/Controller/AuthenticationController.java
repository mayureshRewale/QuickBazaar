package com.qb.QBAuthentication.Controller;

import com.qb.Dao.Beans.LoginRequestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qb.Dao.Beans.RegisterUserBean;
import com.qb.Dao.Beans.ServiceResponseBean;
import com.qb.QBAuthentication.Service.AuthenticationService;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
	
	@Autowired
	AuthenticationService authService;
	
	/* Registration Flow
	 * 1. get-otp		-> generate OTP for contact
	 * 2. verify-otp 	-> verify OTP for contact
	 * 3. register 		-> register user details
	 * 4. set-mpin 		-> set mpin for contact
	 * 5. authenticate 	-> Login with mobile and mpin
	 *
	 * use /swagger-ui/index.html to access swaager-ui
	 */
	
	@GetMapping("/get-otp")
	public ServiceResponseBean getOtp(@RequestParam("contact") String contact, @RequestParam("otp_type") String otpType) {
		ServiceResponseBean serviceResponse = authService.getOtp(contact, otpType);
		return serviceResponse;		
	}
	
	@GetMapping("/verify-otp")
	public ServiceResponseBean verifyOtp(@RequestParam("contact") String contact, @RequestParam("otp") String otp, @RequestParam("otp_type") String otpType) {
		ServiceResponseBean serviceResponse = authService.verifyOtp(contact, otp, otpType);
		return serviceResponse;		
	}
	
	@PostMapping("/register")
	public ServiceResponseBean registerUser(@RequestBody RegisterUserBean registerUserDTO) {
		ServiceResponseBean serviceResponse = authService.registerUser(registerUserDTO);
		return serviceResponse;		
	}
	
	@PutMapping("/set-mpin")
	public ServiceResponseBean setMpin(@RequestParam("contact") String contact, @RequestParam("mpin") String mpin) {
		ServiceResponseBean serviceResponse = authService.setMpin(contact, mpin);
		return serviceResponse;		
	}

	@PostMapping("/login")
	public ServiceResponseBean login(@RequestBody LoginRequestBean loginRequestBean) {
		ServiceResponseBean serviceResponse = authService.login(loginRequestBean);
		return serviceResponse;
	}
	
}
