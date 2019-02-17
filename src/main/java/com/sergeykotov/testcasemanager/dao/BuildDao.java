package com.sergeykotov.testcasemanager.dao;

import com.sergeykotov.testcasemanager.model.Build;
import com.sergeykotov.testcasemanager.model.Module;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class BuildDao {
    private static final String URL = "jdbc:sqlite:src/main/resources/test-case-manager.sqlite3";
    private static final String GET_ALL_CMD = "select b.id, b.module_id, b.version from build b order by b.module_id, b.version;";
    private static final String GET_CMD = "select b.module_id, b.version from build b where b.id = ?;";
    private static final String CREATE_CMD = "insert into build (module_id, version) values (?, ?);";
    private static final String UPDATE_CMD = "update build set module_id = ?, version = ? where id = ?;";
    private static final String DELETE_CMD = "delete from build where id = ?;";

    public static List<Build> getAll() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_CMD); ResultSet resultSet = preparedStatement.executeQuery()) {
            List<Build> builds = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                long moduleId = resultSet.getLong(2);
                String version = resultSet.getString(3);
                Module module = ModuleDao.get(moduleId);
                Build build = new Build(id, module, version);
                builds.add(build);
            }
            return builds;
        } catch (SQLException e) {
            throw e;
        }
    }

    public static Build get(long id) throws SQLException {
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(GET_CMD)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            Build build = null;
            if (resultSet.next()) {
                long moduleId = resultSet.getLong(1);
                String version = resultSet.getString(2);
                Module module = ModuleDao.get(moduleId);
                build = new Build(id, module, version);
            }
            return build;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    public static boolean create(Build build) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CMD)) {
            preparedStatement.setLong(1, build.getModule().getId());
            preparedStatement.setString(2, build.getVersion());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw e;
        }
    }

    public static boolean update(Build build) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CMD)) {
            preparedStatement.setLong(1, build.getModule().getId());
            preparedStatement.setString(2, build.getVersion());
            preparedStatement.setLong(3, build.getId());
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
