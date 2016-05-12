/**
 * Copyright (c) 2016 Oracle and/or its affiliates
 */
package com.oracle.jdt2016.hackathon.hr.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * The persistent class for the DEPARTMENTS database table.
 *
 */
@Entity
@Table(name="HR.DEPARTMENTS")
@NamedQuery(name="Department.findAll", query="SELECT d FROM Department d")
public class Department implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="DEPARTMENT_ID")
    private long departmentId;

    @Column(name="DEPARTMENT_NAME")
    private String departmentName;

    //bi-directional many-to-one association to Location
    @ManyToOne
    @JoinColumn(name="LOCATION_ID")
    @JsonManagedReference
    private Location location;

    //bi-directional many-to-one association to Employee
    @ManyToOne
    @JoinColumn(name="MANAGER_ID")
    @JsonManagedReference
    private Employee employee;

    //bi-directional many-to-one association to Employee
    @OneToMany(mappedBy="department")
    @JsonManagedReference
    private List<Employee> employees;

    //bi-directional many-to-one association to JobHistory
    @OneToMany(mappedBy="department")
    @JsonManagedReference
    private List<JobHistory> jobHistories;

    public Department() {
    }

    public long getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return this.departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Employee addEmployee(Employee employee) {
        getEmployees().add(employee);
        employee.setDepartment(this);

        return employee;
    }

    public Employee removeEmployee(Employee employee) {
        getEmployees().remove(employee);
        employee.setDepartment(null);

        return employee;
    }

    public List<JobHistory> getJobHistories() {
        return this.jobHistories;
    }

    public void setJobHistories(List<JobHistory> jobHistories) {
        this.jobHistories = jobHistories;
    }

    public JobHistory addJobHistory(JobHistory jobHistory) {
        getJobHistories().add(jobHistory);
        jobHistory.setDepartment(this);

        return jobHistory;
    }

    public JobHistory removeJobHistory(JobHistory jobHistory) {
        getJobHistories().remove(jobHistory);
        jobHistory.setDepartment(null);

        return jobHistory;
    }

}