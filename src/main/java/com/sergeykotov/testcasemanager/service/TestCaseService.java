package com.sergeykotov.testcasemanager.service;

import com.sergeykotov.testcasemanager.dao.TestCaseDao;
import com.sergeykotov.testcasemanager.model.TestCase;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public final class TestCaseService {
    private static final Logger log = Logger.getLogger(TesterService.class);

    public static List<TestCase> getAll() {
        try {
            return TestCaseDao.getAll();
        } catch (SQLException exception) {
            log.error("failed to extract test cases: ", exception);
            return null;
        }
    }

    public static TestCase get(long id) {
        try {
            return TestCaseDao.get(id);
        } catch (SQLException exception) {
            log.error("failed to extract the test case with id " + id + ": ", exception);
            return null;
        }
    }

    public static String create(String name) {
        TestCase testCase = new TestCase(name);
        try {
            if (!TestCaseDao.create(testCase)) {
                String message = "failed to create the test case " + testCase;
                log.error(message);
                return message;
            }
            String message = "test case created: " + testCase;
            log.info(message);
            return message;
        } catch (SQLException exception) {
            String message = "failed to create the test case " + testCase + ": " + exception;
            log.error(message);
            return message;
        }
    }

    public static String update(long id, String name) {
        TestCase testCase = new TestCase(id, name);
        try {
            if (!TestCaseDao.update(testCase)) {
                String message = "failed to update the test case " + testCase;
                log.error(message);
                return message;
            }
            String message = "test case updated: " + testCase;
            log.info(message);
            return message;
        } catch (SQLException exception) {
            String message = "failed to update the test case " + testCase + ": " + exception;
            log.error(message);
            return message;
        }
    }

    public static String delete(long id, String name) {
        try {
            if (!TestCaseDao.delete(id)) {
                String message = "failed to delete the test case " + name;
                log.error(message);
                return message;
            }
            String message = "test case deleted: " + name;
            log.info(message);
            return message;
        } catch (SQLException exception) {
            String message = "failed to delete the test case " + name + ": " + exception;
            log.error(message);
            return message;
        }
    }
}
