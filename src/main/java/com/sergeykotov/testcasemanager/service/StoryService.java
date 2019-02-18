package com.sergeykotov.testcasemanager.service;

import com.sergeykotov.testcasemanager.dao.StoryDao;
import com.sergeykotov.testcasemanager.model.Story;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public final class StoryService {
    private static final Logger log = Logger.getLogger(StoryService.class);

    public static List<Story> getAll() {
        try {
            return StoryDao.getAll();
        } catch (SQLException exception) {
            log.error("failed to extract stories: ", exception);
            return null;
        }
    }

    public static Story get(long id) {
        try {
            return StoryDao.get(id);
        } catch (SQLException exception) {
            log.error("failed to extract the story with id " + id + ": ", exception);
            return null;
        }
    }

    public static String create(int num, LocalDate date) {
        Story story = new Story(num, date);
        try {
            if (!StoryDao.create(story)) {
                String message = "failed to create the story " + story;
                log.error(message);
                return message;
            }
            String message = "story created: " + story;
            log.info(message);
            return message;
        } catch (SQLException exception) {
            String message = "failed to create the story " + story + ": " + exception;
            log.error(message);
            return message;
        }
    }

    public static String update(long id, int num, LocalDate date) {
        Story story = new Story(id, num, date);
        try {
            if (!StoryDao.update(story)) {
                String message = "failed to update the story " + story;
                log.error(message);
                return message;
            }
            String message = "story updated: " + story;
            log.info(message);
            return message;
        } catch (SQLException exception) {
            String message = "failed to update the story " + story + ": " + exception;
            log.error(message);
            return message;
        }
    }

    public static String delete(long id, String name) {
        try {
            if (!StoryDao.delete(id)) {
                String message = "failed to delete the story " + name;
                log.error(message);
                return message;
            }
            String message = "story deleted: " + name;
            log.info(message);
            return message;
        } catch (SQLException exception) {
            String message = "failed to delete the story " + name + ": " + exception;
            log.error(message);
            return message;
        }
    }
}
