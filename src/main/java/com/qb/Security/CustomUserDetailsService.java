package com.qb.Security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.qb.Dao.Entities.UserDetails;
import com.qb.Dao.Repository.RolesRepository;
import com.qb.Dao.Repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.qb.Dao.Entities.Roles;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserDetailsRepository userDetailsRepository;

	@Autowired
	RolesRepository rolesRepository;
	
	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {

		UserDetails userDetailsEntity = userDetailsRepository.findByUserNameIgnoreCaseAndIsActive(username, true);
		List<String> roleNameList = Arrays.asList(userDetailsEntity.getRoles().split(","));
		List<Roles> rolesList = rolesRepository.findByNameIn(roleNameList);
		if (userDetailsEntity != null) {
//			SessionDAO sessionDAO = authenticationDAO.getSessionDAO();
//			if (sessionDAO != null) {
//				authService.checkBlockedUser(sessionDAO);
//			}
			return new User(userDetailsEntity.getUserName(), "", getGrantedAuthorities(rolesList));
		}
		throw new UsernameNotFoundException("User not found with number " + username);
	}
	
	private List<GrantedAuthority> getGrantedAuthorities(List<Roles> roles) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (Roles role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return authorities;
	}

}
