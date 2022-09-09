package com.qb.QBUserDetails.Controller;

import com.qb.Commons.Constants.UrlConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping(UrlConstants.USER_URL)
public class UserDetailsController {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @GetMapping("/test")
    public String testPing() {
        log.info("User Test Ping");
        return "User Test Ping Successful";
    }

}
