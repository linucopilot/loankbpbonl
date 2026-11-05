package kh.com.copilot.app.common.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface DatabaseDao {

    Map<String, Object> selectDatabaseInfo();

    Map<String, Object> selectDualValue(@Param("value") String value);
}
