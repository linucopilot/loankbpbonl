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
	public Map<String, Object> selectListUserMgt(Map<String, Object> params) {
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> list = loginDao.selectListUserMgt(params);

		result.put("pageCnt", params.get("pageCnt"));
		result.put("pageNo", params.get("pageNo"));

		if (list == null || list.isEmpty()) {
			result.put("totalCnt", 0);
			return result;
		}

		result.put("totalCnt", list.get(0).get("totalCnt"));
		result.put("list", list);
		return result;
	}

}
