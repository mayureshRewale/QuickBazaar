package com.qb.QBTest.Controller;

import com.qb.Commons.Constants.UrlConstants;
import com.qb.Dao.Beans.RegisterUserBean;
import com.qb.Dao.Beans.ServiceResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping(UrlConstants.TEST_URL)
public class TestController {

	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	@GetMapping("/test")
	public ServiceResponseBean testPing() {
		log.info("Test Ping");
		return ServiceResponseBean.builder().status(Boolean.TRUE).message("Test Ping Successfully").build();
	}

	@GetMapping("/test-2")
	public String testPing2() {
		log.info("Test Ping");
		return "Test Ping Successful";
	}

	@PostMapping("/validation-test")
	public String testValidation(@Valid @RequestBody RegisterUserBean registerUserBean) {
		log.info("Test Ping");
		return "Test Ping Successful";
	}

}
