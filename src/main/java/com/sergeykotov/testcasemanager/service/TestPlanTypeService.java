package com.sergeykotov.testcasemanager.service;

import com.sergeykotov.testcasemanager.dao.TestPlanTypeDao;
import com.sergeykotov.testcasemanager.model.TestPlanType;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public final class TestPlanTypeService {
    private static final Logger log = Logger.getLogger(TestPlanType.class);

    public static List<TestPlanType> getAll() {
        try {
            return TestPlanTypeDao.getAll();
        } catch (SQLException exception) {
            log.error("failed to extract test plan types: ", exception);
            return null;
        }
    }

    public static TestPlanType get(long id) {
        try {
            return TestPlanTypeDao.get(id);
        } catch (SQLException exception) {
            log.error("failed to extract the test plan type with id " + id + ": ", exception);
            return null;
        }
    }

    public static String create(String name) {
        TestPlanType testPlanType = new TestPlanType(name);
        try {
            if (!TestPlanTypeDao.create(testPlanType)) {
                String message = "failed to create the test plan type " + testPlanType;
                log.error(message);
                return message;
            }
            String message = "test plan type created: " + testPlanType;
            log.info(message);
            return message;
        } catch (SQLException exception) {
            String message = "failed to create the test plan type " + testPlanType + ": " + exception;
            log.error(message);
            return message;
        }
    }

    public static String update(long id, String name) {
        TestPlanType testPlanType = new TestPlanType(id, name);
        try {
            if (!TestPlanTypeDao.update(testPlanType)) {
                String message = "failed to update the test plan type " + testPlanType;
                log.error(message);
                return message;
            }
            String message = "test plan type updated: " + testPlanType;
            log.info(message);
            return message;
        } catch (SQLException exception) {
            String message = "failed to update the test plan type " + testPlanType + ": " + exception;
            log.error(message);
            return message;
        }
    }

    public static String delete(long id, String name) {
        try {
            if (!TestPlanTypeDao.delete(id)) {
                String message = "failed to delete the test plan type " + name;
                log.error(message);
                return message;
            }
            String message = "test plan type deleted: " + name;
            log.info(message);
            return message;
        } catch (SQLException exception) {
            String message = "failed to delete the test plan type " + name + ": " + exception;
            log.error(message);
            return message;
        }
    }
}
