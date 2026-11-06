package kh.com.copilot.app.csm.service;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

public interface MsgMgtService {
	
	public Map<String, Object> selectListMsgMgt(HttpServletRequest request, Map<String, Object> data);

	public int insertMsgMgt(HttpServletRequest request, Map<String, Object> data);

	public int updateMsgMgt(HttpServletRequest request, Map<String, Object> data);

	public int deleteMsgMgt(HttpServletRequest request, Map<String, Object> data);
}
