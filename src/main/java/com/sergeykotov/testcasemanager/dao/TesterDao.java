package com.sergeykotov.testcasemanager.dao;

import com.sergeykotov.testcasemanager.model.Tester;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class TesterDao {
    private static final String URL = "jdbc:sqlite:src/main/resources/test-case-manager.sqlite3";
    private static final String GET_ALL_CMD = "select t.id, t.name from tester t order by t.name;";
    private static final String GET_CMD = "select t.name from tester t where t.id = ?;";
    private static final String CREATE_CMD = "insert into tester (name) values (?);";
    private static final String UPDATE_CMD = "update tester set name = ? where id = ?;";
    private static final String DELETE_CMD = "delete from tester where id = ?;";

    public static List<Tester> getAll() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_CMD); ResultSet resultSet = preparedStatement.executeQuery()) {
            List<Tester> testers = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                Tester tester = new Tester(id, name);
                testers.add(tester);
            }
            return testers;
        } catch (SQLException e) {
            throw e;
        }
    }

    public static Tester get(long id) throws SQLException {
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(GET_CMD)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            Tester tester = null;
            if (resultSet.next()) {
                String name = resultSet.getString(1);
                tester = new Tester(id, name);
            }
            return tester;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    public static boolean create(Tester tester) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CMD)) {
            preparedStatement.setString(1, tester.getName());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw e;
        }
    }

    public static boolean update(Tester tester) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CMD)) {
            preparedStatement.setString(1, tester.getName());
            preparedStatement.setLong(2, tester.getId());
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
