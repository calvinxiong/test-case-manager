CREATE TABLE build (
    id        INTEGER PRIMARY KEY,
    module_id INTEGER REFERENCES module (id) ON DELETE CASCADE NOT NULL,
    version   TEXT    NOT NULL,
    UNIQUE (module_id, version)
);

CREATE TABLE grp (
    id        INTEGER PRIMARY KEY,
    name      TEXT    NOT NULL UNIQUE,
    module_id INTEGER REFERENCES module (id) ON DELETE CASCADE NOT NULL
);

CREATE TABLE grp_test_case (
    id           INTEGER PRIMARY KEY,
    grp_id       INTEGER REFERENCES grp (id) ON DELETE CASCADE NOT NULL,
    test_case_id INTEGER NOT NULL REFERENCES test_case (id) ON DELETE CASCADE,
    num          INTEGER NOT NULL,
    UNIQUE (grp_id, test_case_id)
);

CREATE TABLE module (
    id   INTEGER PRIMARY KEY,
    name TEXT    NOT NULL UNIQUE
);

CREATE TABLE story (
    id   INTEGER PRIMARY KEY,
    date DATE    NOT NULL UNIQUE,
    num  INTEGER NOT NULL UNIQUE
);

CREATE TABLE test (
    id           INTEGER PRIMARY KEY,
    test_plan_id INTEGER REFERENCES test_plan (id) ON DELETE CASCADE NOT NULL,
    test_case_id INTEGER NOT NULL REFERENCES test_case (id) ON DELETE CASCADE,
    date         DATE,
    status       TEXT    NOT NULL,
    note         TEXT,
    tester_id    INTEGER NOT NULL REFERENCES tester (id) ON DELETE CASCADE,
    build_id     INTEGER NOT NULL REFERENCES build (id) ON DELETE CASCADE,
    UNIQUE (test_plan_id, test_case_id)
);

CREATE TABLE test_case (
    id   INTEGER PRIMARY KEY,
    name TEXT    NOT NULL UNIQUE
);

CREATE TABLE test_plan (
    id         INTEGER PRIMARY KEY,
    story_id   INTEGER NOT NULL REFERENCES story (id) ON DELETE CASCADE,
    grp_id     INTEGER NOT NULL REFERENCES grp (id) ON DELETE CASCADE,
    num        INTEGER NOT NULL,
    regression BOOLEAN NOT NULL,
    UNIQUE (story_id, grp_id)
);

CREATE TABLE tester (
    id   INTEGER PRIMARY KEY,
    name TEXT    NOT NULL UNIQUE
);