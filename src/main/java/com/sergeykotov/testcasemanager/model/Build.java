package com.sergeykotov.testcasemanager.model;

import java.util.Objects;

public final class Build {
    private Long id;
    private Module module;
    private String version;

    public Build(Module module, String version) {
        this.module = module;
        this.version = version;
    }

    public Build(Long id, Module module, String version) {
        this.id = id;
        this.module = module;
        this.version = version;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Build build = (Build) o;
        return Objects.equals(module, build.module) &&
                Objects.equals(version, build.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(module, version);
    }

    @Override
    public String toString() {
        return module + " " + version;
    }
}
