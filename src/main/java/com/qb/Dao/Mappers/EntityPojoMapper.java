package com.qb.Dao.Mappers;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qb.Dao.Entities.UserDetails;
import com.qb.Dao.Beans.UserDetailsBean;

public class EntityPojoMapper {
	
	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	public static UserDetailsBean mapUserDAO(UserDetails daoUser) {
		UserDetailsBean userDetailsDTO = new UserDetailsBean();
		
		try {
			
			userDetailsDTO.setId(daoUser.getId());
			userDetailsDTO.setFirstName(daoUser.getFirstName());
			userDetailsDTO.setMiddleName(daoUser.getMiddleName());
			userDetailsDTO.setLastName(daoUser.getLastName());
			userDetailsDTO.setEmail(daoUser.getEmail());
			userDetailsDTO.setMobile(daoUser.getMobile());
			userDetailsDTO.setRoles(Arrays.asList(daoUser.getRoles().split(",")));
			
		}catch (Exception e) {
			log.info("Exception while mapping : {}", e.toString());
		}
		
		return userDetailsDTO;
	}

}
