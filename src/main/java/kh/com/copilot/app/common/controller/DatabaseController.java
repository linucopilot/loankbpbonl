package kh.com.copilot.app.common.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kh.com.copilot.app.common.service.DatabaseService;

@RestController
public class DatabaseController {

    private final DatabaseService databaseService;

    public DatabaseController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    /**
     * Diagnostic endpoint. Restrict or remove this endpoint before production.
     */
    @GetMapping("/api/database/info")
    public Map<String, Object> databaseInfo() {
        return databaseService.getDatabaseInfo();
    }

    @GetMapping("/api/database/dual")
    public Map<String, Object> dual(@RequestParam(defaultValue = "Hello Oracle") String value) {
        return databaseService.getDualValue(value);
    }
}
