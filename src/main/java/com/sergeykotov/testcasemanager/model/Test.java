package com.sergeykotov.testcasemanager.model;

import java.time.LocalDate;
import java.util.Objects;

public final class Test {
    private Long id;
    private TestPlan testPlan;
    private TestCase testCase;
    private LocalDate date;
    private Status status;
    private String note;
    private Tester tester;
    private Build build;

    public Test(TestPlan testPlan, TestCase testCase, LocalDate date, Status status, String note, Tester tester, Build build) {
        this.testPlan = testPlan;
        this.testCase = testCase;
        this.date = date;
        this.status = status;
        this.note = note;
        this.tester = tester;
        this.build = build;
    }

    public Test(Long id, TestPlan testPlan, TestCase testCase, LocalDate date, Status status, String note, Tester tester, Build build) {
        this.id = id;
        this.testPlan = testPlan;
        this.testCase = testCase;
        this.date = date;
        this.status = status;
        this.note = note;
        this.tester = tester;
        this.build = build;
    }

    public Long getId() {
        return id;
    }

    public TestPlan getTestPlan() {
        return testPlan;
    }

    public void setTestPlan(TestPlan testPlan) {
        this.testPlan = testPlan;
    }

    public TestCase getTestCase() {
        return testCase;
    }

    public void setTestCase(TestCase testCase) {
        this.testCase = testCase;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Tester getTester() {
        return tester;
    }

    public void setTester(Tester tester) {
        this.tester = tester;
    }

    public Build getBuild() {
        return build;
    }

    public void setBuild(Build build) {
        this.build = build;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Test test = (Test) o;
        return Objects.equals(testPlan, test.testPlan) &&
                Objects.equals(testCase, test.testCase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(testPlan, testCase);
    }

    @Override
    public String toString() {
        return testPlan + " " + testCase;
    }
}
