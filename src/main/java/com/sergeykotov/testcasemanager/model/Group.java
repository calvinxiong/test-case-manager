package com.sergeykotov.testcasemanager.model;

import java.util.Objects;

public final class Group {
    private Long id;
    private Module module;
    private String name;

    public Group(Module module, String name) {
        this.module = module;
        this.name = name;
    }

    public Group(Long id, Module module, String name) {
        this.id = id;
        this.module = module;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(group.name, name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
