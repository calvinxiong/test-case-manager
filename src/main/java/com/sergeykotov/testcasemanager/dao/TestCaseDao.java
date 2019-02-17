package com.sergeykotov.testcasemanager.dao;

import com.sergeykotov.testcasemanager.model.TestCase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class TestCaseDao {
    private static final String URL = "jdbc:sqlite:src/main/resources/test-case-manager.sqlite3";
    private static final String GET_ALL_CMD = "select t.id, t.name from test_case t order by t.name;";
    private static final String GET_CMD = "select t.name from test_case t where t.id = ?;";
    private static final String CREATE_CMD = "insert into test_case (name) values (?);";
    private static final String UPDATE_CMD = "update test_case set name = ? where id = ?;";
    private static final String DELETE_CMD = "delete from test_case where id = ?;";

    public static List<TestCase> getAll() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_CMD); ResultSet resultSet = preparedStatement.executeQuery()) {
            List<TestCase> testers = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                TestCase testCase = new TestCase(id, name);
                testers.add(testCase);
            }
            return testers;
        } catch (SQLException e) {
            throw e;
        }
    }

    public static TestCase get(long id) throws SQLException {
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(GET_CMD)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            TestCase testCase = null;
            if (resultSet.next()) {
                String name = resultSet.getString(1);
                testCase = new TestCase(id, name);
            }
            return testCase;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    public static boolean create(TestCase testCase) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CMD)) {
            preparedStatement.setString(1, testCase.getName());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw e;
        }
    }

    public static boolean update(TestCase testCase) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CMD)) {
            preparedStatement.setString(1, testCase.getName());
            preparedStatement.setLong(2, testCase.getId());
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