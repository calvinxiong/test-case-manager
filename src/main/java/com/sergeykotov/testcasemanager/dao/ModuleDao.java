package com.sergeykotov.testcasemanager.dao;

import com.sergeykotov.testcasemanager.model.Module;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class ModuleDao {
    private static final String URL = "jdbc:sqlite:src/main/resources/test-case-manager.sqlite3";
    private static final String GET_ALL_CMD = "select m.id, m.name from module m order by m.name;";
    private static final String GET_CMD = "select m.name from module m where m.id = ?;";
    private static final String CREATE_CMD = "insert into module (name) values (?);";
    private static final String UPDATE_CMD = "update module set name = ? where id = ?;";
    private static final String DELETE_CMD = "delete from module where id = ?;";

    public static List<Module> getAll() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_CMD); ResultSet resultSet = preparedStatement.executeQuery()) {
            List<Module> modules = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                Module module = new Module(id, name);
                modules.add(module);
            }
            return modules;
        } catch (SQLException e) {
            throw e;
        }
    }

    public static Module get(long id) throws SQLException {
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(GET_CMD)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            Module module = null;
            if (resultSet.next()) {
                String name = resultSet.getString(1);
                module = new Module(id, name);
            }
            return module;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    public static boolean create(Module module) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CMD)) {
            preparedStatement.setString(1, module.getName());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw e;
        }
    }

    public static boolean update(Module module) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CMD)) {
            preparedStatement.setString(1, module.getName());
            preparedStatement.setLong(2, module.getId());
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
