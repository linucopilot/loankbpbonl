package kh.com.copilot.app.csm.service;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import kh.com.copilot.app.common.exception.BusinessException;

public interface MenuMgtService {

	public Map<String, Object> selectListMenuAll(HttpServletRequest request, Map<String, Object> params)
			throws BusinessException;

	public Map<String, Object> selectListMenuCombo(HttpServletRequest request, Map<String, Object> params)
			throws BusinessException;

	public int insertMenu(HttpServletRequest request, Map<String, Object> params) throws BusinessException;

	public int updateMenu(HttpServletRequest request, Map<String, Object> params) throws BusinessException;
}
