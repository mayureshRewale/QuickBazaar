package com.qb.QBAuthentication.Service;

import com.qb.Dao.Beans.LoginRequestBean;
import com.qb.Dao.Beans.RegisterUserBean;
import com.qb.Dao.Beans.ServiceResponseBean;

public interface AuthenticationService {
	
	ServiceResponseBean getOtp(String contact, String otpType);
	
	ServiceResponseBean verifyOtp(String contact, String otp, String otpType);
	
	ServiceResponseBean registerUser(RegisterUserBean registerUserDTO);

	ServiceResponseBean setMpin(String contact, String mpin);

	ServiceResponseBean login(LoginRequestBean loginRequestBean);
}
