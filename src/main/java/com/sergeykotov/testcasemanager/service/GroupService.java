package com.sergeykotov.testcasemanager.service;

import com.sergeykotov.testcasemanager.dao.GroupDao;
import com.sergeykotov.testcasemanager.dao.ModuleDao;
import com.sergeykotov.testcasemanager.model.Group;
import com.sergeykotov.testcasemanager.model.Module;
import com.sergeykotov.testcasemanager.model.Story;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public final class GroupService {
    private static final Logger log = Logger.getLogger(GroupService.class);

    public static List<Group> getAll() {
        try {
            return GroupDao.getAll();
        } catch (SQLException exception) {
            log.error("failed to extract groups: ", exception);
            return null;
        }
    }

    public static Group get(long id) {
        try {
            return GroupDao.get(id);
        } catch (SQLException exception) {
            log.error("failed to extract the group with id " + id + ": ", exception);
            return null;
        }
    }

    public static String create(long moduleId, String name, int priority) {
        try {
            Module module = ModuleDao.get(moduleId);
            Group group = new Group(module, name, priority);
            if (!GroupDao.create(group)) {
                String message = "failed to create the group " + group;
                log.error(message);
                return message;
            }
            String message = "group created: " + group;
            log.info(message);
            return message;
        } catch (SQLException exception) {
            String message = "failed to create the group " + name + ": " + exception;
            log.error(message);
            return message;
        }
    }

    public static String update(long id, long moduleId, String name, int priority) {
        try {
            Module module = ModuleDao.get(moduleId);
            Group group = new Group(id, module, name, priority);
            if (!GroupDao.create(group)) {
                String message = "failed to update the group " + group;
                log.error(message);
                return message;
            }
            String message = "group updated: " + group;
            log.info(message);
            return message;
        } catch (SQLException exception) {
            String message = "failed to update the group " + name + ": " + exception;
            log.error(message);
            return message;
        }
    }

    public static String delete(long id, String name) {
        try {
            if (!GroupDao.delete(id)) {
                String message = "failed to delete the group " + name;
                log.error(message);
                return message;
            }
            String message = "group deleted: " + name;
            log.info(message);
            return message;
        } catch (SQLException exception) {
            String message = "failed to delete the group " + name + ": " + exception;
            log.error(message);
            return message;
        }
    }
}
