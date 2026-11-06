package kh.com.copilot.app.csm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import kh.com.copilot.app.common.exception.BusinessException;
import kh.com.copilot.app.csm.dao.MsgMgtDao;
import kh.com.copilot.app.csm.service.MsgMgtService;

@Service
public class MsgMgtServiceImpl implements MsgMgtService {
	private final MsgMgtDao msgMgtDao;

	public MsgMgtServiceImpl(MsgMgtDao msgMgtDao) {
		this.msgMgtDao = msgMgtDao;
	}

	@Override
	public Map<String, Object> selectListMsgMgt(HttpServletRequest request, Map<String, Object> data) {
//		log.debug("MsgMgtService start" + request);

		Map<String, Object> result = new HashMap<>();

		try {

//			log.debug("MsgMgtService selectListMsgMgt data >>>>>>>> {}", JsonUtils.prettify(data));

			List<Map<String, Object>> list = msgMgtDao.selectListMsgMgt(data);

			if (list.size() < 1) {
				result.put("pageNo", data.get("pageNo"));
				result.put("pageCnt", data.get("pageCnt"));
				result.put("totalCnt", 0);

				return result;
			}

			result.put("pageNo", data.get("pageNo"));
			result.put("pageCnt", data.get("pageCnt"));
			result.put("totalCnt", list.get(0).get("totalCnt"));
			result.put("list", list);

//			log.debug("MsgMgtService selectListMsgMgt result >>>>>>>> {}", JsonUtils.prettify(result));

		} catch (Exception e) {

//			log.error("MsgMgtService Exception", e);
//			log.error("MsgMgtService Exception - getMessage : " + e.getMessage());

			throw new BusinessException("CSMA00001", "CSMT00001", e);
		}

//		log.debug("MsgMgtService selectListMsgMgt end");

		return result;
	}

	@Override
	public int insertMsgMgt(HttpServletRequest request, Map<String, Object> data) {
//		log.debug("MsgMgtService start" + request);

		int result = 0;
		Map<String, Object> errMsgcdMgt = null;

		try {

//			log.debug("MsgMgtService insertMsgMgt data >>>>>>>> {}", JsonUtils.prettify(data));

			errMsgcdMgt = msgMgtDao.selectMsgcdExstYn(data);
//			log.debug("errMsgcdMgt  >>>>>>>> {}", JsonUtils.prettify(errMsgcdMgt));
			data.put("errMsgcd", errMsgcdMgt.get("errMsgcd"));

			result = msgMgtDao.insertMsgMgt(data);

//			log.debug("MsgMgtService insertMsgMgt result >>>>>>>> {}", JsonUtils.prettify(result));

		} catch (Exception e) {

//			log.error("MsgMgtService Exception", e);
//			log.error("MsgMgtService Exception - getMessage : " + e.getMessage());

			throw new BusinessException("CSMA00001", "CSMT00001", e);
		}

//		log.debug("MsgMgtService insertMsgMgt end");

		return result;
	}

	@Override
	public int updateMsgMgt(HttpServletRequest request, Map<String, Object> data) {
//		log.debug("MsgMgtService start" + request);

		int result = 0;

		try {

//			log.debug("MsgMgtService updateMsgMgt data >>>>>>>> {}", JsonUtils.prettify(data));

			result = msgMgtDao.updateMsgMgt(data);

//			log.debug("MsgMgtService updateMsgMgt result >>>>>>>> {}", JsonUtils.prettify(result));

		} catch (Exception e) {

//			log.error("MsgMgtService Exception", e);
//			log.error("MsgMgtService Exception - getMessage : " + e.getMessage());

			throw new BusinessException("CSMA00001", "CSMT00001", e);
		}

//		log.debug("MsgMgtService updateMsgMgt end");

		return result;
	}

	@Override
	public int deleteMsgMgt(HttpServletRequest request, Map<String, Object> data) {
//		log.debug("MsgMgtService start" + request);

		int result = 0;
		try {

//			log.debug("MsgMgtService deleteMsgMgt data >>>>>>>> {}", JsonUtils.prettify(data));

			result = msgMgtDao.deleteMsgMgt(data);

//			log.debug("MsgMgtService deleteMsgMgt result >>>>>>>> {}", JsonUtils.prettify(result));

		} catch (Exception e) {

//			log.error("MsgMgtService Exception", e);
//			log.error("MsgMgtService Exception - getMessage : " + e.getMessage());

			throw new BusinessException("CSMA00001", "CSMT00001", e);
		}

//		log.debug("MsgMgtService deleteMsgMgt end");

		return result;
	}

}
