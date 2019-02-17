package com.sergeykotov.testcasemanager.dao;

import com.sergeykotov.testcasemanager.model.Group;
import com.sergeykotov.testcasemanager.model.Module;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class GroupDao {
    private static final String URL = "jdbc:sqlite:src/main/resources/test-case-manager.sqlite3";
    private static final String GET_ALL_CMD = "select g.id, g.module_id, g.name, g.priority from grp g order by g.module_id, g.name;";
    private static final String GET_CMD = "select g.module_id, g.name, g.priority from grp g where g.id = ?;";
    private static final String CREATE_CMD = "insert into grp (module_id, name, priority) values (?, ?, ?);";
    private static final String UPDATE_CMD = "update grp set module_id = ?, name = ?, priority = ? where id = ?;";
    private static final String DELETE_CMD = "delete from grp where id = ?;";

    public static List<Group> getAll() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_CMD); ResultSet resultSet = preparedStatement.executeQuery()) {
            List<Group> groups = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                long moduleId = resultSet.getLong(2);
                String name = resultSet.getString(3);
                int priority = resultSet.getInt(4);
                Module module = ModuleDao.get(moduleId);
                Group group = new Group(id, module, name, priority);
                groups.add(group);
            }
            return groups;
        } catch (SQLException e) {
            throw e;
        }
    }

    public static Group get(long id) throws SQLException {
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(GET_CMD)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            Group group = null;
            if (resultSet.next()) {
                long moduleId = resultSet.getLong(1);
                String name = resultSet.getString(2);
                int priority = resultSet.getInt(3);
                Module module = ModuleDao.get(moduleId);
                group = new Group(id, module, name, priority);
            }
            return group;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    public static boolean create(Group group) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CMD)) {
            preparedStatement.setLong(1, group.getModule().getId());
            preparedStatement.setString(2, group.getName());
            preparedStatement.setInt(3, group.getPriority());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw e;
        }
    }

    public static boolean update(Group group) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CMD)) {
            preparedStatement.setLong(1, group.getModule().getId());
            preparedStatement.setString(2, group.getName());
            preparedStatement.setInt(3, group.getPriority());
            preparedStatement.setLong(4, group.getId());
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