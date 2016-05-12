/**
 * Copyright (c) 2016 Oracle and/or its affiliates
 */
package com.oracle.jdt2016.hackathon.hr.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;


/**
 * The persistent class for the REGIONS database table.
 *
 */
@Entity
@Table(name="HR.REGIONS")
@NamedQuery(name="Region.findAll", query="SELECT r FROM Region r")
public class Region implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="REGION_ID")
    private long regionId;

    @Column(name="REGION_NAME")
    private String regionName;

    //bi-directional many-to-one association to Country
    @OneToMany(mappedBy="region")
    @JsonBackReference
    private List<Country> countries;

    public Region() {
    }

    public long getRegionId() {
        return this.regionId;
    }

    public void setRegionId(long regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return this.regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public List<Country> getCountries() {
        return this.countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public Country addCountry(Country country) {
        getCountries().add(country);
        country.setRegion(this);

        return country;
    }

    public Country removeCountry(Country country) {
        getCountries().remove(country);
        country.setRegion(null);

        return country;
    }

}