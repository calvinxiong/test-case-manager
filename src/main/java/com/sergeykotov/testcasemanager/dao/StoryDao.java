package com.sergeykotov.testcasemanager.dao;

import com.sergeykotov.testcasemanager.model.Story;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class StoryDao {
    private static final String URL = "jdbc:sqlite:src/main/resources/test-case-manager.sqlite3";
    private static final String GET_ALL_CMD = "select s.id, s.num, s.date from story s order by s.date;";
    private static final String GET_CMD = "select s.num, s.date from story s where s.id = ?;";
    private static final String CREATE_CMD = "insert into story (num, date) values (?, ?);";
    private static final String UPDATE_CMD = "update story set num = ?, date = ? where id = ?;";
    private static final String DELETE_CMD = "delete from story where id = ?;";

    public static List<Story> getAll() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_CMD); ResultSet resultSet = preparedStatement.executeQuery()) {
            List<Story> stories = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                int num = resultSet.getInt(2);
                LocalDate date = resultSet.getDate(3).toLocalDate();
                Story story = new Story(id, num, date);
                stories.add(story);
            }
            return stories;
        } catch (SQLException e) {
            throw e;
        }
    }

    public static Story get(long id) throws SQLException {
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(GET_CMD)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            Story story = null;
            if (resultSet.next()) {
                int num = resultSet.getInt(1);
                LocalDate date = resultSet.getDate(2).toLocalDate();
                story = new Story(num, date);
            }
            return story;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    public static boolean create(Story story) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CMD)) {
            preparedStatement.setInt(1, story.getNum());
            preparedStatement.setDate(2, Date.valueOf(story.getDate()));
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw e;
        }
    }

    public static boolean update(Story story) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CMD)) {
            preparedStatement.setInt(1, story.getNum());
            preparedStatement.setDate(2, Date.valueOf(story.getDate()));
            preparedStatement.setLong(3, story.getId());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw e;
        }
    }

    public static boolean delete(long id) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CMD)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw e;
        }
    }
}
