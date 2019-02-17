package com.sergeykotov.testcasemanager.dao;

import com.sergeykotov.testcasemanager.model.TestPlanType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class TestPlanTypeDao {
    private static final String URL = "jdbc:sqlite:src/main/resources/test-case-manager.sqlite3";
    private static final String GET_ALL_CMD = "select t.id, t.name from test_plan_type t order by t.name;";
    private static final String GET_CMD = "select t.name from tester t where t.id = ?;";
    private static final String CREATE_CMD = "insert into tester (name) values (?);";
    private static final String UPDATE_CMD = "update tester set name = ? where id = ?;";
    private static final String DELETE_CMD = "delete from tester where id = ?;";

    public static List<TestPlanType> getAll() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_CMD); ResultSet resultSet = preparedStatement.executeQuery()) {
            List<TestPlanType> testPlanTypes = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                TestPlanType testPlanType = new TestPlanType(id, name);
                testPlanTypes.add(testPlanType);
            }
            return testPlanTypes;
        } catch (SQLException e) {
            throw e;
        }
    }

    public static TestPlanType get(long id) throws SQLException {
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(GET_CMD)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            TestPlanType testPlanType = null;
            if (resultSet.next()) {
                String name = resultSet.getString(1);
                testPlanType = new TestPlanType(id, name);
            }
            return testPlanType;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    public static boolean create(TestPlanType testPlanType) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CMD)) {
            preparedStatement.setString(1, testPlanType.getName());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw e;
        }
    }

    public static boolean update(TestPlanType testPlanType) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CMD)) {
            preparedStatement.setString(1, testPlanType.getName());
            preparedStatement.setLong(2, testPlanType.getId());
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
