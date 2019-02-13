package com.sergeykotov.testcasemanager.model;

import java.util.Objects;

public final class TestPlan {
    private Long id;
    private Story story;
    private Group group;
    private int num;
    private boolean regression;

    public TestPlan(Story story, Group group, int num, boolean regression) {
        this.story = story;
        this.group = group;
        this.num = num;
        this.regression = regression;
    }

    public TestPlan(Long id, Story story, Group group, int num, boolean regression) {
        this.id = id;
        this.story = story;
        this.group = group;
        this.num = num;
        this.regression = regression;
    }

    public Long getId() {
        return id;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isRegression() {
        return regression;
    }

    public void setRegression(boolean regression) {
        this.regression = regression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestPlan testPlan = (TestPlan) o;
        return Objects.equals(story, testPlan.story) && Objects.equals(group, testPlan.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(story, group);
    }

    @Override
    public String toString() {
        return story + " " + group;
    }
}
