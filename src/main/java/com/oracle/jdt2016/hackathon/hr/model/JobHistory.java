/**
 * Copyright (c) 2016 Oracle and/or its affiliates
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oracle.jdt2016.hackathon.hr.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * The persistent class for the JOB_HISTORY database table.
 *
 */
@Entity
@Table(name="HR.JOB_HISTORY")
@NamedQuery(name="JobHistory.findAll", query="SELECT j FROM JobHistory j")
public class JobHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private JobHistoryPK id;

    @Temporal(TemporalType.DATE)
    @Column(name="END_DATE")
    private Date endDate;

    //bi-directional many-to-one association to Department
    @ManyToOne
    @JoinColumn(name="DEPARTMENT_ID")
    @JsonBackReference
    private Department department;

    //bi-directional many-to-one association to Employee
    @ManyToOne
    @JoinColumn(name="EMPLOYEE_ID")
    @JsonBackReference
    private Employee employee;

    //bi-directional many-to-one association to Job
    @ManyToOne
    @JoinColumn(name="JOB_ID")
    @JsonManagedReference
    private Job job;

    public JobHistory() {
    }

    public JobHistoryPK getId() {
        return this.id;
    }

    public void setId(JobHistoryPK id) {
        this.id = id;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Job getJob() {
        return this.job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

}