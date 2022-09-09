package com.qb.Config;

import com.qb.Dao.Beans.UserDetailsBean;
import com.qb.Dao.Entities.Roles;
import com.qb.Dao.Entities.UserDetails;
import com.qb.Dao.Mappers.EntityPojoMapper;
import com.qb.Dao.Repository.RolesRepository;
import com.qb.Dao.Repository.UserDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.*;

@Component
public class ApplicationSetupConfig implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Value("${default.admin.username}")
    private String adminUsername;

    @Value("${default.admin.password}")
    private String adminPassword;

    @Value("${default.admin.email}")
    private String adminEmail;

    @Value("${default.admin.first.name}")
    private String adminFirstName;

    @Value("${default.admin.middle.name}")
    private String adminMiddleName;

    @Value("${default.admin.last.name}")
    private String adminLastName;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        addRolesIfNotExists(Arrays.asList("ROLE_ADMIN","ROLE_USER","ROLE_VENDOR","ROLE_CUSTOMER"));
        addAdminUserIfNotExists();

    }

    @Transactional
    private void addAdminUserIfNotExists() {

        try{
            UserDetails userDetails = userDetailsRepository.findByUserNameIgnoreCase(adminUsername);

            if(userDetails == null) {
                UserDetails adminUserDetails = new UserDetails();
                UserDetails daoUser = new UserDetails();

                adminUserDetails.setEmail(adminEmail);
                adminUserDetails.setUserName(adminUsername);
                adminUserDetails.setPassword(adminPassword);

                Roles userRole = rolesRepository.findByNameAndIsActive("ROLE_ADMIN", true);
                if (userRole != null) {
                    log.info("Setting Roles");
                    adminUserDetails.setRoles(userRole.getName());
                }

                daoUser.setFirstName(adminFirstName);
                daoUser.setMiddleName(adminMiddleName);
                daoUser.setLastName(adminLastName);

                UserDetails savedUser = userDetailsRepository.save(adminUserDetails);

                UserDetailsBean userDetailsDTO = EntityPojoMapper.mapUserDAO(savedUser);
                log.info("UserDetailsDTO :: {}", userDetailsDTO);
            }else {
                log.info("Admin User Exists");
//                log.info("Admin User Exists :: {} :: {}", authentication.getUserName(), adminUsername);
            }

        }catch (Exception e){
            log.info("Exception on setting up ADMIN User");
        }

    }

    @Transactional
    private void addRolesIfNotExists(List<String> rolesList) {
        List<Roles> rolesEntityList = new ArrayList<>();
        for(String role : rolesList){
            log.info("Role :: {}", role);
            if(Objects.isNull(rolesRepository.findByNameAndIsActive(role, Boolean.TRUE))){
                Roles rolesEntity = new Roles();
                rolesEntity.setName(role);
                rolesEntity.setDescription(role.replace("ROLE_", ""));
                rolesEntityList.add(rolesEntity);
            }
        }
        if(Objects.nonNull(rolesEntityList) && rolesEntityList.size() > 0){
            rolesRepository.saveAll(rolesEntityList);
        }

    }
}
