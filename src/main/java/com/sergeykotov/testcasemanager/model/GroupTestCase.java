package com.sergeykotov.testcasemanager.model;

import java.util.Objects;

public final class GroupTestCase {
    private Long id;
    private Group group;
    private TestCase testCase;
    private int num;

    public GroupTestCase(Group group, TestCase testCase, int num) {
        this.group = group;
        this.testCase = testCase;
        this.num = num;
    }

    public GroupTestCase(Long id, Group group, TestCase testCase, int num) {
        this.id = id;
        this.group = group;
        this.testCase = testCase;
        this.num = num;
    }

    public Long getId() {
        return id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public TestCase getTestCase() {
        return testCase;
    }

    public void setTestCase(TestCase testCase) {
        this.testCase = testCase;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupTestCase that = (GroupTestCase) o;
        return Objects.equals(group, that.group) && Objects.equals(testCase, that.testCase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group, testCase);
    }

    @Override
    public String toString() {
        return group + " " + testCase;
    }
}
