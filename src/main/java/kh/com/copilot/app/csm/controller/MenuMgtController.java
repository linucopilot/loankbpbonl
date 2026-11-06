package kh.com.copilot.app.csm.controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import kh.com.copilot.app.common.constants.Constants;
import kh.com.copilot.app.common.dto.ResultPayload;
import kh.com.copilot.app.csm.service.MenuMgtService;

@RestController
@RequestMapping("/csm")
public class MenuMgtController {
	
	private final MenuMgtService menuMgtService;

	MenuMgtController(MenuMgtService menuMgtService) {
		this.menuMgtService = menuMgtService;
	}

	@PostMapping(value = "/selectListMenuAll")
	public ResultPayload selectListMenuAll(HttpServletRequest request, @RequestBody HashMap<String, Object> data) {

		//log.debug("MenuMgtController selectListMenuAll start...");

		ResultPayload rPayload = new ResultPayload();
		rPayload.setResultCd(Constants.SUCCESS_CD);
		rPayload.setResultData(menuMgtService.selectListMenuAll(request, data));

		//log.debug("MenuMgtController selectListMenuAll end...");

		return rPayload;
	}

	@PostMapping(value = "/selectListMenuCombo")
	public ResultPayload selectListMenuCombo(HttpServletRequest request, @RequestBody HashMap<String, Object> data) {

		//log.debug("MenuMgtController selectListMenuCombo start...");

		ResultPayload rPayload = new ResultPayload();
		rPayload.setResultCd(Constants.SUCCESS_CD);
		rPayload.setResultData(menuMgtService.selectListMenuCombo(request, data));

		//log.debug("MenuMgtController selectListMenuCombo end...");

		return rPayload;
	}

	@PostMapping(value = "/insertMenu")
	public ResultPayload insertMenu(HttpServletRequest request, @RequestBody HashMap<String, Object> data) {

		//log.debug("MenuMgtController insertMenu start... ");

		ResultPayload rPayload = new ResultPayload();
		rPayload.setResultCd(Constants.SUCCESS_CD);
		rPayload.setResultData(menuMgtService.insertMenu(request, data));

		//log.debug("MenuMgtController selectListMenuCombo end...");

		return rPayload;
	}

	@PostMapping(value = "/updateMenu")
	public ResultPayload updateMenu(HttpServletRequest request, @RequestBody HashMap<String, Object> data) {

		//log.debug("MenuMgtController updateMenu start... ");

		ResultPayload rPayload = new ResultPayload();
		rPayload.setResultCd(Constants.SUCCESS_CD);
		rPayload.setResultData(menuMgtService.updateMenu(request, data));

		//log.debug("MenuMgtController selectListMenuCombo end...");

		return rPayload;
	}
}
