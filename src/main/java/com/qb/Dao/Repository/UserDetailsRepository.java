package com.qb.Dao.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qb.Dao.Entities.UserDetails;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

    UserDetails findByUserNameIgnoreCaseAndIsActive(String username, boolean isActive);

    UserDetails findByMobile(String contact);

    UserDetails findByUserNameIgnoreCase(String username);

}
