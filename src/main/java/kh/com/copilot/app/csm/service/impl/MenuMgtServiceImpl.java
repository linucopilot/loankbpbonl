package kh.com.copilot.app.csm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import kh.com.copilot.app.common.exception.BusinessException;
import kh.com.copilot.app.csm.dao.MenuMgtDao;
import kh.com.copilot.app.csm.service.MenuMgtService;

@Service
public class MenuMgtServiceImpl implements MenuMgtService {
	private final MenuMgtDao menuMgtDao;

	public MenuMgtServiceImpl(MenuMgtDao menuMgtDao) {
		this.menuMgtDao = menuMgtDao;
	}

	@Override
	public Map<String, Object> selectListMenuAll(HttpServletRequest request, Map<String, Object> params) {
		Map<String, Object> resMap = new HashMap<>();
		try {
			// log.debug(">>>>>>>>" + params.toString());
			List<Map<String, Object>> menuList = menuMgtDao.selectListMenuAll(params);
			if (menuList.size() <= 0) {
				resMap.put("pageNo", params.get("pageNo"));
				resMap.put("pageCnt", params.get("pageCnt"));
				resMap.put("totalCnt", 0);

				return resMap;
			}

			// log.debug("menuList : " + menuList.toString());

			resMap.put("totalCnt", menuList.get(0).get("totalCnt"));
			resMap.put("menuList", menuList);
		} catch (Exception e) {
			// log.error("error", e);
			throw new BusinessException("CSMA00001", "CSMT00001", e);
		}
		return resMap;
	}

	@Override
	public Map<String, Object> selectListMenuCombo(HttpServletRequest request, Map<String, Object> params) {
		// log.debug("[selectListMenuCombo] Start");
		Map<String, Object> resMap = new HashMap<>();
		try {
			// log.debug(">>>>>>>>" + params.toString());
			List<Map<String, Object>> menuComboList = menuMgtDao.selectListMenuCombo(params);
			resMap.put("menuComboList", menuComboList);
		} catch (Exception e) {
			// log.error("error", e);
			throw new BusinessException("CSMA00001", "CSMT00001", e);
		}
		return resMap;
	}

	@Override
	public int insertMenu(HttpServletRequest request, Map<String, Object> params) throws BusinessException {
		// log.debug("[insertMenu] Start");
		int result = 0;
		try {
			result = menuMgtDao.insertMenu(params);

		} catch (Exception e) {
			// log.error("error", e);
			throw new BusinessException("CSMA00001", "CSMT00001", e);
		}
		return result;
	}

	@Override
	public int updateMenu(HttpServletRequest request, Map<String, Object> params) throws BusinessException {
		// log.debug("[updateMenu] Start");
		int result = 0;
		try {
			result = menuMgtDao.updateMenu(params);

		} catch (Exception e) {
			// log.error("error", e);
			throw new BusinessException("CSMA00001", "CSMT00001", e);
		}
		return result;
	}

}
