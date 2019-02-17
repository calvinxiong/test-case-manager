package com.sergeykotov.testcasemanager.dao;

import com.sergeykotov.testcasemanager.model.Group;
import com.sergeykotov.testcasemanager.model.Story;
import com.sergeykotov.testcasemanager.model.TestPlan;
import com.sergeykotov.testcasemanager.model.TestPlanType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class TestPlanDao {
    private static final String URL = "jdbc:sqlite:src/main/resources/test-case-manager.sqlite3";
    private static final String GET_ALL_CMD = "select t.id, t.story_id, t.grp_id, t.num, t.type_id from test_plan t order by t.story_id, t.grp_id, t.num;";
    private static final String GET_CMD = "select t.story_id, t.grp_id, t.num, t.type_id from test_plan t where t.id = ?;";
    private static final String CREATE_CMD = "insert into test_plan (story_id, grp_id, num, type_id) values (?, ?, ?, ?);";
    private static final String UPDATE_CMD = "update test_plan set story_id = ?, grp_id = ?, num = ?, type_id = ? where id = ?;";
    private static final String DELETE_CMD = "delete from test_plan where id = ?;";

    public static List<TestPlan> getAll() throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_CMD); ResultSet resultSet = preparedStatement.executeQuery()) {
            List<TestPlan> testPlans = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                long storyId = resultSet.getLong(2);
                long groupId = resultSet.getLong(3);
                int num = resultSet.getInt(4);
                long typeId = resultSet.getLong(5);
                Story story = StoryDao.get(storyId);
                Group group = GroupDao.get(groupId);
                TestPlanType testPlanType = TestPlanTypeDao.get(typeId);
                TestPlan testPlan = new TestPlan(id, story, group, num, testPlanType);
                testPlans.add(testPlan);
            }
            return testPlans;
        } catch (SQLException e) {
            throw e;
        }
    }

    public static TestPlan get(long id) throws SQLException {
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(GET_CMD)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            TestPlan testPlan = null;
            if (resultSet.next()) {
                long storyId = resultSet.getLong(1);
                long groupId = resultSet.getLong(2);
                int num = resultSet.getInt(3);
                long typeId = resultSet.getLong(4);
                Story story = StoryDao.get(storyId);
                Group group = GroupDao.get(groupId);
                TestPlanType testPlanType = TestPlanTypeDao.get(typeId);
                testPlan = new TestPlan(id, story, group, num, testPlanType);
            }
            return testPlan;
        } catch (SQLException e) {
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    public static boolean create(TestPlan testPlan) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CMD)) {
            preparedStatement.setLong(1, testPlan.getStory().getId());
            preparedStatement.setLong(2, testPlan.getGroup().getId());
            preparedStatement.setInt(3, testPlan.getNum());
            preparedStatement.setLong(4, testPlan.getTestPlanType().getId());
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw e;
        }
    }

    public static boolean update(TestPlan testPlan) throws SQLException {
        try (Connection connection = DriverManager.getConnection(URL); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CMD)) {
            preparedStatement.setLong(1, testPlan.getStory().getId());
            preparedStatement.setLong(2, testPlan.getGroup().getId());
            preparedStatement.setInt(3, testPlan.getNum());
            preparedStatement.setLong(4, testPlan.getTestPlanType().getId());
            preparedStatement.setLong(5, testPlan.getId());
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
