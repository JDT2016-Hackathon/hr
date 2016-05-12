--------------------------------------------------------------------------------
--  Copyright (c) 2016 Oracle and/or its affiliates
--
--  Licensed under the Apache License, Version 2.0 (the "License");
--  you may not use this file except in compliance with the License.
--  You may obtain a copy of the License at
--
--      http://www.apache.org/licenses/LICENSE-2.0
--
--  Unless required by applicable law or agreed to in writing, software
--  distributed under the License is distributed on an "AS IS" BASIS,
--  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
--  See the License for the specific language governing permissions and
--  limitations under the License.
--------------------------------------------------------------------------------

CREATE SCHEMA HR AUTHORIZATION hr;

SET CURRENT SCHEMA HR;

-- REGIONS

CREATE TABLE regions (
    region_id       BIGINT PRIMARY KEY,
    region_name     VARCHAR(25)
);

-- COUNTRIES

CREATE TABLE countries (
    country_id      CHAR(2) PRIMARY KEY,
    country_name    VARCHAR(40),
    region_id       BIGINT,
    CONSTRAINT countr_reg_fk FOREIGN KEY (region_id) REFERENCES regions (region_id)
);

-- LOCATIONS

CREATE TABLE locations (
    location_id     DECIMAL(4) PRIMARY KEY,
    street_address  VARCHAR(40),
    postal_code     VARCHAR(12),
    city            VARCHAR(30) NOT NULL,
    state_province  VARCHAR(25),
    country_id      CHAR(2),
    CONSTRAINT loc_c_id_fk FOREIGN KEY (country_id) REFERENCES countries (country_id)
);

-- Useful for any subsequent addition of rows to locations table
-- Starts with 3300

CREATE SEQUENCE locations_seq
    AS INTEGER
    START WITH      3300
    INCREMENT BY    100
    MAXVALUE        9900
    NO CYCLE;

-- DEPARTMEMTS

CREATE TABLE departments (
    department_id       DECIMAL(4) PRIMARY KEY,
    department_name     VARCHAR(30) NOT NULL,
    manager_id          DECIMAL(6),
    location_id         DECIMAL(4),
    CONSTRAINT dept_loc_fk FOREIGN KEY (location_id) REFERENCES locations (location_id)
);

-- Useful for any subsequent addition of rows to departments table
-- Starts with 280 

CREATE SEQUENCE departments_seq
    AS INTEGER
    START WITH      280
    INCREMENT BY    10
    MAXVALUE        9990
    NO CYCLE;

-- JOBS

CREATE TABLE jobs (
    job_id          VARCHAR(10) PRIMARY KEY,
    job_title       VARCHAR(35) NOT NULL,
    min_salary      DECIMAL(6),
    max_salary      DECIMAL(6)
);

-- EMPLOYEES

CREATE TABLE employees (
    employee_id         DECIMAL(6) PRIMARY KEY,
    first_name          VARCHAR(20),
    last_name           VARCHAR(25) NOT NULL,
    email               VARCHAR(25) NOT NULL,
    phone_number        VARCHAR(20),
    hire_date           DATE NOT NULL,
    job_id              VARCHAR(10) NOT NULL,
    salary              DECIMAL(8,2),
    commission_pct      DECIMAL(2,2),
    manager_id          DECIMAL(6),
    department_id       DECIMAL(4),
    CONSTRAINT emp_email_uk UNIQUE (email),
    CONSTRAINT emp_salary_min CHECK (salary > 0),
    CONSTRAINT emp_dept_fk FOREIGN KEY (department_id) REFERENCES departments,
    CONSTRAINT emp_job_fk FOREIGN KEY (job_id) REFERENCES jobs (job_id),
    CONSTRAINT emp_manager_fk FOREIGN KEY (manager_id) REFERENCES employees
);

ALTER TABLE departments ADD
    CONSTRAINT dept_mgr_fk FOREIGN KEY (manager_id) REFERENCES employees (employee_id);

-- Useful for any subsequent addition of rows to employees table
-- Starts with 207 

CREATE SEQUENCE employees_seq
    START WITH     207
    INCREMENT BY   1
    NO CYCLE;

-- JOB HISTORY

CREATE TABLE job_history (
    employee_id     DECIMAL(6) NOT NULL,
    start_date      DATE NOT NULL,
    end_date        DATE NOT NULL,
    job_id          VARCHAR(10) NOT NULL,
    department_id   DECIMAL(4),
    CONSTRAINT jhist_emp_id_st_date_pk PRIMARY KEY (employee_id, start_date),
    CONSTRAINT jhist_date_interval CHECK (end_date > start_date),
    CONSTRAINT jhist_emp_fk FOREIGN KEY (employee_id) REFERENCES employees,
    CONSTRAINT jhist_dept_fk FOREIGN KEY (department_id) REFERENCES departments
);
