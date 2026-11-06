package kh.com.copilot.app.csm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuMgtDao {

	public List<Map<String, Object>> selectListMenuAll(Map<String, Object> parameter);

	public List<Map<String, Object>> selectListMenuAllWithoutPaging(Map<String, Object> parameter);

	public List<Map<String, Object>> selectListMenuCombo(Map<String, Object> parameter);

	public int insertMenu(Map<String, Object> parameter);

	public int updateMenu(Map<String, Object> parameter);
}
