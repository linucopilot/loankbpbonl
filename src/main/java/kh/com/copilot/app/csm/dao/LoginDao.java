package kh.com.copilot.app.csm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginDao {

	public List<Map<String, Object>> selectListUserMgt(Map<String, Object> data);

}
