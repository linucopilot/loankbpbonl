package kh.com.copilot.app.csm.service.impl;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import kh.com.copilot.app.csm.dao.LoginDao;
import kh.com.copilot.app.csm.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {
	private final LoginDao loginDao;

	public LoginServiceImpl(LoginDao loginDao) {
		this.loginDao = loginDao;
	}

	@Override
	public Map<String, Object> selectListUser(Map<String, Object> params) {
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> list = loginDao.selectListUser(params);

		result.put("dmndCnt", params.get("dmndCnt"));
		result.put("displayCnt", params.get("displayCnt"));

		if (list == null || list.isEmpty()) {
			result.put("totalCnt", 0);
			return result;
		}

		result.put("totalCnt", list.get(0).get("totalCnt"));
		result.put("list", list);
		return result;
	}

}
