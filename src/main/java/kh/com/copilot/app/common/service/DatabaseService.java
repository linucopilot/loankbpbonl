package kh.com.copilot.app.common.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kh.com.copilot.app.common.dao.DatabaseDao;

@Service
public class DatabaseService {

    private final DatabaseDao databaseDao;

    public DatabaseService(DatabaseDao databaseDao) {
        this.databaseDao = databaseDao;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getDatabaseInfo() {
        return databaseDao.selectDatabaseInfo();
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getDualValue(String value) {
        return databaseDao.selectDualValue(value);
    }
}
