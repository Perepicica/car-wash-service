CREATE SCHEMA IF NOT EXISTS service_schema;

CREATE TABLE db_user
(
    id        VARCHAR,
    email VARCHAR,
    password VARCHAR,
    name       VARCHAR,
    role VARCHAR,
    is_active BOOLEAN,

    PRIMARY KEY (id)
);

CREATE TABLE box
(
    id        VARCHAR,
    name       VARCHAR,
    opens_at TIME,
    closes_at TIME,
    work_coefficient REAL,
    operator_id VARCHAR,

    PRIMARY KEY (id),
    FOREIGN KEY (operator_id) REFERENCES db_user (id)
);

CREATE TABLE service
(
    id        VARCHAR,
    name       VARCHAR,
    duration    INTEGER,
    cost    INTEGER,
    discount INTEGER,

    PRIMARY KEY (id)
);

CREATE TABLE appointment(
    id VARCHAR,
    date DATE,
    time TIME,
    status VARCHAR,
    cost NUMERIC,
    service_id VARCHAR,
    box_id VARCHAR,
    customer_id VARCHAR,

    PRIMARY KEY (id),
    FOREIGN KEY (service_id) REFERENCES service (id),
    FOREIGN KEY (box_id) REFERENCES box (id),
    FOREIGN KEY (customer_id) REFERENCES db_user (id)
);