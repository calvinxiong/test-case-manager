package com.sergeykotov.testcasemanager.service;

import com.sergeykotov.testcasemanager.dao.ModuleDao;
import com.sergeykotov.testcasemanager.model.Module;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public final class ModuleService {
    private static final Logger log = Logger.getLogger(ModuleService.class);

    public static List<Module> getAll() {
        try {
            return ModuleDao.getAll();
        } catch (SQLException exception) {
            log.error("failed to extract modules: ", exception);
            return null;
        }
    }

    public static Module get(long id) {
        try {
            return ModuleDao.get(id);
        } catch (SQLException exception) {
            log.error("failed to extract the module with id " + id + ": ", exception);
            return null;
        }
    }

    public static String create(String name) {
        Module module = new Module(name);
        try {
            if (!ModuleDao.create(module)) {
                String message = "failed to create the module " + module;
                log.error(message);
                return message;
            }
            String message = "module created: " + module;
            log.info(message);
            return message;
        } catch (SQLException exception) {
            String message = "failed to create the module " + module + ": " + exception;
            log.error(message);
            return message;
        }
    }

    public static String update(long id, String name) {
        Module module = new Module(id, name);
        try {
            if (!ModuleDao.update(module)) {
                String message = "failed to update the module " + module;
                log.error(message);
                return message;
            }
            String message = "module updated: " + module;
            log.info(message);
            return message;
        } catch (SQLException exception) {
            String message = "failed to update the module " + module + ": " + exception;
            log.error(message);
            return message;
        }
    }

    public static String delete(long id, String name) {
        try {
            if (!ModuleDao.delete(id)) {
                String message = "failed to delete the module " + name;
                log.error(message);
                return message;
            }
            String message = "module deleted: " + name;
            log.info(message);
            return message;
        } catch (SQLException exception) {
            String message = "failed to delete the module " + name + ": " + exception;
            log.error(message);
            return message;
        }
    }
}
