package com.sergeykotov.testcasemanager.dao;

import com.sergeykotov.testcasemanager.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class TestDao {
    private static final String URL = "jdbc:sqlite:src/main/resources/test-case-manager.sqlite3";
    private static final String GET_ALL_CMD = "select t.id, t.test_plan_id, t.test_case_id, t.date, t.status, t.note, t.tester_id, t.build_id from test t order by t.test_plan_id, t.date;";
    private static final String GET_CMD = "select t.test_plan_id, t.test_case_id, t.date, t.status, t.note, t.tester_id, t.build_id from test t where t.id = ?;";
    private static final String CREATE_CMD = "insert into test (test_plan_id, test_case_id, date, status, note, tester_id, build_id) values (?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_CMD = "update test set test_plan_id = ?, test_case_id = ?, date = ?, status = ?, note = ?, tester_id = ?, build_id = ? where id = ?;";
    private static final String DELETE_CMD = "delete from test where id = ?;";

    public static List<Test> getAll() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_CMD); ResultSet resultSet = preparedStatement.executeQuery()) {
            List<Test> tests = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                long testPlanId = resultSet.getLong(2);
                long testCaseId = resultSet.getLong(3);
                LocalDate date = resultSet.getDate(4).toLocalDate();
                Status status = Status.valueOf(resultSet.getString(5));
                String note = resultSet.getString(6);
                long testerId = resultSet.getLong(7);
                long buildId = resultSet.getLong(8);
                TestPlan testPlan = TestPlanDao.get(testPlanId);
                TestCase testCase = TestCaseDao.get(testCaseId);
                Tester tester = TesterDao.get(testerId);
                Build build = BuildDao.get(buildId);
                Test test = new Test(id, testPlan, testCase, date, status, note, tester, build);
                tests.add(test);
            }
            return tests;
        } catch (SQLException e) {
            throw e;
        }
    }

    public static Test get(long id) throws SQLException {
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(GET_CMD)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            Test test = null;
            if (resultSet.next()) {
                long testPlanId = resultSet.getLong(1);
                long testCaseId = resultSet.getLong(2);
                LocalDate date = resultSet.getDate(3).toLocalDate();
                Status status = Status.valueOf(resultSet.getString(4));
                String note = resultSet.getString(5);
                long testerId = resultSet.getLong(6);
                long buildId = resultSet.getLong(7);
                TestPlan testPlan = TestPlanDao.get(testPlanId);
                TestCase testCase = TestCaseDao.get(testCaseId);
                Tester tester = TesterDao.get(testerId);
                Build build = BuildDao.get(buildId);
                test = new Test(id, testPlan, testCase, date, status, note, tester, build);
            }
            return test;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    public static boolean create(Test test) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CMD)) {
            preparedStatement.setLong(1, test.getTestPlan().getId());
            preparedStatement.setLong(2, test.getTestCase().getId());
            preparedStatement.setDate(3, Date.valueOf(test.getDate()));
            preparedStatement.setString(4, test.getStatus().toString());
            preparedStatement.setString(5, test.getNote());
            preparedStatement.setLong(6, test.getTester().getId());
            preparedStatement.setLong(7, test.getBuild().getId());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw e;
        }
    }

    public static boolean update(Test test) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CMD)) {
            preparedStatement.setLong(1, test.getTestPlan().getId());
            preparedStatement.setLong(2, test.getTestCase().getId());
            preparedStatement.setDate(3, Date.valueOf(test.getDate()));
            preparedStatement.setString(4, test.getStatus().toString());
            preparedStatement.setString(5, test.getNote());
            preparedStatement.setLong(6, test.getTester().getId());
            preparedStatement.setLong(7, test.getBuild().getId());
            preparedStatement.setLong(8, test.getId());
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
