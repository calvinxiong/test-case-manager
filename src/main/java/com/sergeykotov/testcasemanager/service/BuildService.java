package com.sergeykotov.testcasemanager.service;

import com.sergeykotov.testcasemanager.dao.BuildDao;
import com.sergeykotov.testcasemanager.dao.ModuleDao;
import com.sergeykotov.testcasemanager.model.Build;
import com.sergeykotov.testcasemanager.model.Module;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public final class BuildService {
    private static final Logger log = Logger.getLogger(GroupService.class);

    public static List<Build> getAll() {
        try {
            return BuildDao.getAll();
        } catch (SQLException exception) {
            log.error("failed to extract builds: ", exception);
            return null;
        }
    }

    public static Build get(long id) {
        try {
            return BuildDao.get(id);
        } catch (SQLException exception) {
            log.error("failed to extract the build with id " + id + ": ", exception);
            return null;
        }
    }

    public static String create(long moduleId, String version) {
        try {
            Module module = ModuleDao.get(moduleId);
            Build build = new Build(module, version);
            if (!BuildDao.create(build)) {
                String message = "failed to create the build " + version + " of module id " + moduleId;
                log.error(message);
                return message;
            }
            String message = "build created: " + version + " of module id " + moduleId;
            log.info(message);
            return message;
        } catch (SQLException exception) {
            String message = "failed to create the build " + version + " of module id " + moduleId + ": " + exception;
            log.error(message);
            return message;
        }
    }

    public static String update(long id, long moduleId, String version) {
        try {
            Module module = ModuleDao.get(moduleId);
            Build build = new Build(id, module, version);
            if (!BuildDao.create(build)) {
                String message = "failed to update the build " + version + " of module id " + moduleId;
                log.error(message);
                return message;
            }
            String message = "build updated: " + version + " of module id " + moduleId;
            log.info(message);
            return message;
        } catch (SQLException exception) {
            String message = "failed to update the build " + version + " of module id " + moduleId + ": " + exception;
            log.error(message);
            return message;
        }
    }

    public static String delete(long id, String name) {
        try {
            if (!BuildDao.delete(id)) {
                String message = "failed to delete the build " + name;
                log.error(message);
                return message;
            }
            String message = "build deleted: " + name;
            log.info(message);
            return message;
        } catch (SQLException exception) {
            String message = "failed to delete the build " + name + ": " + exception;
            log.error(message);
            return message;
        }
    }
}
