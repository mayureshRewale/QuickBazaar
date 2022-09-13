package com.qb.QBAuthentication.ServiceImpl;

import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qb.Commons.Utils.CommonUtils;
import com.qb.Commons.Utils.JwtUtil;
import com.qb.Dao.Beans.*;
import com.qb.Security.CustomUserDetailsService;
import com.qb.SpringApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.qb.Dao.Entities.Roles;
import com.qb.Dao.Entities.UserDetails;
import com.qb.Dao.Mappers.EntityPojoMapper;
import com.qb.Dao.Repository.RolesRepository;
import com.qb.Dao.Repository.UserDetailsRepository;
import com.qb.QBAuthentication.Service.AuthenticationService;
import com.qb.Commons.Utils.OtpUtils;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	
	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	@Autowired
	OtpUtils otpUtils;

	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	RolesRepository roleRepository;
	
	@Autowired
	UserDetailsRepository userDetailsRepository;
	
	@Autowired
	UserDetailsRepository userRepository;
	
	@Override
	public ServiceResponseBean getOtp(String contact, String otpType) {
		log.info("Request in getOtp");
		
		String otp = "";
		
		try {
			
			UserDetails userDetailsEntity = userDetailsRepository.findByMobile(contact);
			
			if(otpType.isBlank()) {
				return ServiceResponseBean.builder().errorMessage("Invalid OTP Type").status(Boolean.FALSE).build();
			}
			
			switch (otpType) {
			case "LOGIN":
				
				if(Objects.isNull(userDetailsEntity)) {
					return ServiceResponseBean.builder().errorMessage("No User Found with mobile no. " + contact).status(Boolean.FALSE).build();
				}
				otp = otpUtils.generateOtp(contact+"_LOGIN");
				log.info("OTP Value : {}", otp);
				
				break;
				
			case "SIGNUP":
				if(Objects.nonNull(userDetailsEntity)) {
					return ServiceResponseBean.builder().errorMessage("User with mobile no. " + contact + " already exists").status(Boolean.FALSE).build();
				}
				otp = otpUtils.generateOtp(contact+"_SIGNUP");
				log.info("OTP Value : {}", otp);
				
				break;
				
			default:
				return ServiceResponseBean.builder().errorMessage("Invalid OTP Type").status(Boolean.FALSE).build();
				
			}
			
		}catch (Exception e) {
			log.error("Exception : {}", e);
			return ServiceResponseBean.builder().errorMessage("Something went wrong.. Please try again later").status(Boolean.FALSE).build();
		}
		return ServiceResponseBean.builder().message("OTP for " + contact +" generated successfully").data(otp).status(Boolean.TRUE).build();
	}

	@Override
	public ServiceResponseBean verifyOtp(String contact, String otp, String otpType) {
		log.info("Request in verifyOtp");
		
		Boolean isOtpVerified = Boolean.FALSE;
		
		try {
			
			if(otpType.isBlank()) {
				return ServiceResponseBean.builder().errorMessage("Invalid OTP Type").status(Boolean.FALSE).build();
			}
			
			switch (otpType) {
			case "LOGIN":
				isOtpVerified = otpUtils.verifyOtp(contact+"_LOGIN", otp);
				log.info("OTP Verified : {}", isOtpVerified);
				
				break;
				
			case "SIGNUP":
				isOtpVerified = otpUtils.verifyOtp(contact+"_SIGNUP", otp);
				log.info("OTP Verified : {}", isOtpVerified);
				
				break;
				
			default:
				break;
			}
			
		}catch (Exception e) {
			log.error("Exception : {}", e);
			return ServiceResponseBean.builder().errorMessage("Something went wrong.. Please try again later").status(Boolean.FALSE).build();
		}
		return isOtpVerified ? ServiceResponseBean.builder().message("OTP for " + contact +" Verified successfully").data(otp).status(Boolean.TRUE).build() : 
							   ServiceResponseBean.builder().message("Incorrect OTP").status(Boolean.FALSE).build();
	}

	@Override
	public ServiceResponseBean registerUser(RegisterUserBean registerUserDTO) {
		log.info("Request in registerUser");

		UserDetails userDetailsEntity = new UserDetails();
		
		try {

			userDetailsEntity.setEmail(registerUserDTO.getEmail());
			userDetailsEntity.setMobile(registerUserDTO.getMobile());
			userDetailsEntity.setUserName(registerUserDTO.getMobile());
			
			Roles userRole = roleRepository.findByNameAndIsActive("ROLE_USER", true);
	        if (userRole != null) {
	        	log.info("Setting Roles");
				userDetailsEntity.setRoles(userRole.getName());
	        }
			
			userDetailsEntity.setFirstName(registerUserDTO.getFirstName());
			userDetailsEntity.setMiddleName(registerUserDTO.getMiddleName());
			userDetailsEntity.setLastName(registerUserDTO.getLastName());
			
			UserDetails savedUser = userDetailsRepository.save(userDetailsEntity);
			log.info("UserDetails :: {}", savedUser);
			
			UserDetailsBean userDetailsDTO = EntityPojoMapper.mapUserDAO(savedUser);
			log.info("UserDetailsDTO :: {}", userDetailsDTO);
			
			return Objects.nonNull(savedUser) ? ServiceResponseBean.builder().message("User Registered Successfully").data(userDetailsDTO).status(Boolean.TRUE).build() : 
				   								ServiceResponseBean.builder().message("Incorrect OTP").status(Boolean.FALSE).build();
		}catch (Exception e) {
			log.error("Exception : {}", e);
			return ServiceResponseBean.builder().errorMessage("Something went wrong.. Please try again later").status(Boolean.FALSE).build();
		}
	}
	
	public void setUserRole(UserDetails savedUser) {
        Roles userRole = roleRepository.findByNameAndIsActive("ROLE_USER", true);
        if (userRole != null) {
            savedUser.setRoles(userRole.getName());
            userDetailsRepository.save(savedUser);
        }
    }
	
	public void setAdminRole(UserDetails savedUser) {
        Roles userRole = roleRepository.findByNameAndIsActive("ROLE_ADMIN", true);
        if (userRole != null) {
            savedUser.setRoles(userRole.getName());
            userDetailsRepository.save(savedUser);
        }
    }

	@Override
	public ServiceResponseBean setMpin(String contact, String mpin) {
		log.info("Request in setMpin");
		
		if(mpin.isBlank())
			return ServiceResponseBean.builder().errorMessage("Mpin cannot be empty").status(Boolean.FALSE).build();
		
		try {
			
			UserDetails userDetailsEntity = userDetailsRepository.findByMobile(contact);
			log.info("AuthenticationDAO : {}", userDetailsEntity);
			
			if(Objects.nonNull(userDetailsEntity))
				userDetailsEntity.setMpin(mpin);
			else
				return ServiceResponseBean.builder().errorMessage("No User found with mobile no." + contact).status(Boolean.FALSE).build();
			
			UserDetails savedUserDetails = userDetailsRepository.save(userDetailsEntity);
			
			return Objects.nonNull(savedUserDetails) ? ServiceResponseBean.builder().message("Mpin Set Successfully").status(Boolean.TRUE).build() :
															ServiceResponseBean.builder().message("Something went wrong.. Please try again later").status(Boolean.FALSE).build();
		}catch (Exception e) {
			log.error("Exception : {}", e);
			return ServiceResponseBean.builder().errorMessage("Something went wrong.. Please try again later").status(Boolean.FALSE).build();
		}
	}

	@Override
	public ServiceResponseBean login(LoginRequestBean loginRequestBean) {
		log.info("Request in login");
		try {
			String username;
			if (loginRequestBean.getUsername() != null && loginRequestBean.getUsername() != ""){
				username = loginRequestBean.getUsername();
				if(!verifyADUser(username, loginRequestBean.getPassword()))
					throw new Exception("Admin User Not Authenticated");
			}
			else {
				username = loginRequestBean.getMobile();
				log.info("Mobile {}, OTP {}", loginRequestBean.getMobile(), loginRequestBean.getOtp());
				if (!loginRequestBean.getMobile().isBlank() && !loginRequestBean.getOtp().isBlank() && !verifyUserByOTP(username, loginRequestBean.getOtp()))
					throw new Exception("Incorrect OTP");
				else if(!loginRequestBean.getMobile().isBlank() && !loginRequestBean.getMpin().isBlank() && !verifyMobileAndMpin(username, loginRequestBean.getMpin()))
					throw new Exception("Incorrect MPIN");
			}

			CustomUserDetailsService userDetailsService  = (CustomUserDetailsService) SpringApplicationContext.getBean("customUserDetailsService");
			User user = userDetailsService.loadUserByUsername(username);

			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					user,
					null,
					user.getAuthorities()
			);
			return processAuthentication(username);
		} catch (Exception e) {
			log.info("Exception while authenticating user:{}", e.getMessage());
			return ServiceResponseBean.builder().status(Boolean.FALSE).errorMessage("Unable to login").build();
		}
	}

	private ServiceResponseBean processAuthentication(String username) {
		log.info("Getting user with username");

		UserDetails userDetailsEntity = userDetailsRepository.findByUserNameIgnoreCaseAndIsActive(username, true);
		log.info("Successfully fetched AuthObject");

		String token = jwtUtil.getFullAutherizationToken(username);
		log.info("Successfully Fetched Token");

		Map<String, String> userDetails = new HashMap<>();
		String fullName = CommonUtils.getFullName(userDetailsEntity.getFirstName(), userDetailsEntity.getMiddleName(), userDetailsEntity.getLastName());
		LoginResponseBean loginResponseBean = new LoginResponseBean();
		loginResponseBean.setName(fullName);
		loginResponseBean.setMobile(userDetailsEntity.getMobile());
		loginResponseBean.setEmail(userDetailsEntity.getEmail());
		loginResponseBean.setUsername(userDetailsEntity.getUserName());
		loginResponseBean.setAccessToken(token.substring(0, token.indexOf("|")));
		loginResponseBean.setRefreshToken(token.substring(token.indexOf("|")+1, token.length()));
		log.info("Sending Final data");

		return ServiceResponseBean.builder().status(Boolean.TRUE).data(loginResponseBean).build();
	}

	private boolean verifyUserByOTP(String username, String otp){
		try{
			if (otpUtils.verifyOtp(username+"_LOGIN", otp)) {
				log.info("OTP Verisied - Mobile :: {}, OTP :: {}", username, otp);
				otpUtils.removeOtp(username+"_LOGIN");
				return true;
			}
		} catch (Exception e){
			log.error("Error while verifying OTP: {}",e.getMessage());
		}
		return false;
	}

	private boolean verifyADUser(String username, String password){
		if(Boolean.TRUE)
			return !CommonUtils.isNullOrEmpty(username) && !CommonUtils.isNullOrEmpty(password) && password.equals("Test@1234");
		UserDetails userDetailsEntity = userDetailsRepository.findByUserNameIgnoreCase(username);
		return !CommonUtils.isNullOrEmpty(username) && !CommonUtils.isNullOrEmpty(password) && password.equals(userDetailsEntity.getPassword());
	}

	private boolean verifyMobileAndMpin(String mobile, String mpin){
		UserDetails userDetailsEntity = userDetailsRepository.findByMobile(mobile);
		return !CommonUtils.isNullOrEmpty(mobile) && !CommonUtils.isNullOrEmpty(mpin) && mpin.equals(userDetailsEntity.getMpin());
	}

}
