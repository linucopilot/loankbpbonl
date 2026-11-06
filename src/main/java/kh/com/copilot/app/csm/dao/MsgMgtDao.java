package kh.com.copilot.app.csm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MsgMgtDao {
	/**
	 * 메시지 목록조회
	 * 
	 * @param request
	 * @return
	 */
	public List<Map<String, Object>> selectListMsgMgt(Map<String, Object> data);

	/**
	 * 메시지존재여부조회
	 * 
	 * @param request
	 * @return
	 */
	public Map<String, Object> selectMsgcdExstYn(Map<String, Object> data);

	/**
	 * 메시지 등록
	 * 
	 * @param request
	 * @return
	 */
	public int insertMsgMgt(Map<String, Object> data);

	/**
	 * 메시지 수정
	 * 
	 * @param request
	 * @return
	 */
	public int updateMsgMgt(Map<String, Object> data);

	/**
	 * 메시지 삭제
	 * 
	 * @param request
	 * @return
	 */
	public int deleteMsgMgt(Map<String, Object> data);
}
