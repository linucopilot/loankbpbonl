package kh.com.copilot.app.csm.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import kh.com.copilot.app.common.constants.Constants;
import kh.com.copilot.app.common.dto.ResultPayload;
import kh.com.copilot.app.csm.service.LoginService;

@RestController
public class LoginController {
	private final LoginService loginService;

	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}
	
	@PostMapping(value = "/auth/selectListUserMgt")
	public ResultPayload selectUserList(HttpServletRequest request, @RequestBody Map<String, Object> params) {
		//log.info("selectUserList start...");
		ResultPayload rPayload = new ResultPayload();
		rPayload.setResultCd(Constants.SUCCESS_CD);
		rPayload.setResultData(loginService.selectListUserMgt(params));
		return rPayload;
	}
}
