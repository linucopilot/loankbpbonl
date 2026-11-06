package kh.com.copilot.app.csm.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import kh.com.copilot.app.common.constants.Constants;
import kh.com.copilot.app.common.dto.ResultPayload;
import kh.com.copilot.app.csm.service.MsgMgtService;

@RestController
@RequestMapping("/csm")
public class MsgMgtController {
	private final MsgMgtService msgMgtService;

	MsgMgtController(MsgMgtService mgtService) {
		this.msgMgtService = mgtService;
	}

	@PostMapping(value = "/selectListMsgMgt")
	public ResultPayload selectListMsgMgt(HttpServletRequest request, @RequestBody Map<String, Object> data) {

		//log.debug("MsgMgtController selectListMsgMgt start...");

		ResultPayload rPayload = new ResultPayload();

		rPayload.setResultCd(Constants.SUCCESS_CD);

		rPayload.setResultData(msgMgtService.selectListMsgMgt(request, data));

//		log.debug("MsgMgtController selectListMsgMgt end...");

		return rPayload;

	}

	@PostMapping(value = "/insertMsgMgt")
	public ResultPayload insertMsgMgt(HttpServletRequest request, @RequestBody Map<String, Object> data) {

//		log.debug("MsgMgtController insertMsgMgt start...");

		ResultPayload rPayload = new ResultPayload();

		rPayload.setResultCd(Constants.SUCCESS_CD);

		rPayload.setResultData(msgMgtService.insertMsgMgt(request, data));

//		log.debug("MsgMgtController insertMsgMgt end...");

		return rPayload;

	}

	@PostMapping(value = "/updateMsgMgt")
	public ResultPayload updateMsgMgt(HttpServletRequest request, @RequestBody Map<String, Object> data) {

//		log.debug("MsgMgtController updateMsgMgt start...");

		ResultPayload rPayload = new ResultPayload();

		rPayload.setResultCd(Constants.SUCCESS_CD);

		rPayload.setResultData(msgMgtService.updateMsgMgt(request, data));

//		log.debug("MsgMgtController updateMsgMgt end...");

		return rPayload;

	}

	@PostMapping(value = "/deleteMsgMgt")
	public ResultPayload deleteMsgMgt(HttpServletRequest request, @RequestBody Map<String, Object> data) {

//		log.debug("MsgMgtController deleteMsgMgt start...");

		ResultPayload rPayload = new ResultPayload();

		rPayload.setResultCd(Constants.SUCCESS_CD);

		rPayload.setResultData(msgMgtService.deleteMsgMgt(request, data));

//		log.debug("MsgMgtController deleteMsgMgt end...");

		return rPayload;

	}
}
