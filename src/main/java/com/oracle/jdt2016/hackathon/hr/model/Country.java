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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * The persistent class for the COUNTRIES database table.
 *
 */
@Entity
@Table(name="HR.COUNTRIES")
@NamedQuery(name="Country.findAll", query="SELECT c FROM Country c")
public class Country implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="COUNTRY_ID")
    private String countryId;

    @Column(name="COUNTRY_NAME")
    private String countryName;

    //bi-directional many-to-one association to Region
    @ManyToOne
    @JoinColumn(name="REGION_ID")
    @JsonManagedReference
    private Region region;

    //bi-directional many-to-one association to Location
    @OneToMany(mappedBy="country")
    @JsonBackReference
    private List<Location> locations;

    public Country() {
    }

    public String getCountryId() {
        return this.countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Region getRegion() {
        return this.region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public List<Location> getLocations() {
        return this.locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public Location addLocation(Location location) {
        getLocations().add(location);
        location.setCountry(this);

        return location;
    }

    public Location removeLocation(Location location) {
        getLocations().remove(location);
        location.setCountry(null);

        return location;
    }

}