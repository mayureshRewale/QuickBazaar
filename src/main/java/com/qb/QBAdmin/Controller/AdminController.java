package com.qb.QBAdmin.Controller;

import com.qb.Commons.Constants.UrlConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping(UrlConstants.ADMIN_URL)
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Secured("ROLE_ADMIN")
    @GetMapping("/test")
    public String testPing() {
        log.info("Admin Test Ping");
        return "Admin Test Ping Successful";
    }

}
