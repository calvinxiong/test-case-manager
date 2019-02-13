package com.sergeykotov.testcasemanager.model;

import java.time.LocalDate;
import java.util.Objects;

public final class Story {
    private Long id;
    private int num;
    private LocalDate date;

    public Story(int num, LocalDate date) {
        this.num = num;
        this.date = date;
    }

    public Story(Long id, int num, LocalDate date) {
        this.id = id;
        this.num = num;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Story story = (Story) o;
        return num == story.num;
    }

    @Override
    public int hashCode() {
        return Objects.hash(num);
    }

    @Override
    public String toString() {
        return String.valueOf(num);
    }
}
