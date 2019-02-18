package com.sergeykotov.testcasemanager.service;

import com.sergeykotov.testcasemanager.dao.TesterDao;
import com.sergeykotov.testcasemanager.model.Tester;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public final class TesterService {
    private static final Logger log = Logger.getLogger(TesterService.class);

    public static List<Tester> getAll() {
        try {
            return TesterDao.getAll();
        } catch (SQLException exception) {
            log.error("failed to extract testers: ", exception);
            return null;
        }
    }

    public static Tester get(long id) {
        try {
            return TesterDao.get(id);
        } catch (SQLException exception) {
            log.error("failed to extract the tester with id " + id + ": ", exception);
            return null;
        }
    }

    public static String create(String name) {
        Tester tester = new Tester(name);
        try {
            if (!TesterDao.create(tester)) {
                String message = "failed to create the tester " + tester;
                log.error(message);
                return message;
            }
            String message = "tester created: " + tester;
            log.info(message);
            return message;
        } catch (SQLException exception) {
            String message = "failed to create the tester " + tester + ": " + exception;
            log.error(message);
            return message;
        }
    }

    public static String update(long id, String name) {
        Tester tester = new Tester(id, name);
        try {
            if (!TesterDao.update(tester)) {
                String message = "failed to update the tester " + tester;
                log.error(message);
                return message;
            }
            String message = "tester updated: " + tester;
            log.info(message);
            return message;
        } catch (SQLException exception) {
            String message = "failed to update the tester " + tester + ": " + exception;
            log.error(message);
            return message;
        }
    }

    public static String delete(long id, String name) {
        try {
            if (!TesterDao.delete(id)) {
                String message = "failed to delete the tester " + name;
                log.error(message);
                return message;
            }
            String message = "tester deleted: " + name;
            log.info(message);
            return message;
        } catch (SQLException exception) {
            String message = "failed to delete the tester " + name + ": " + exception;
            log.error(message);
            return message;
        }
    }
}
