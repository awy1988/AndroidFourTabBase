package com.demo.model.item;

import java.util.Date;
import java.util.List;

/**
 * Created by anweiyang on 17/12/28.
 */

public class Item {
    /**
     * tenantName : 中央音乐学院
     * name : 终极发声练习
     * timeFrom : 2018-01-01T09:00:00.000Z
     * timeUntil : 2019-01-01T00:00:00.000Z
     * units : 10
     * price : 10
     * status : normal
     * ageGrades : []
     * categories : ["music"]
     * id : Od3Q4PJ65inU
     */

    private String id;
    private String tenantName;
    private String name;
    private String description;
    private String comment;
    private String cover;
    private String coverLarge;
    private List<String> categories;
    private List<String> ageGrades;
    private Date timeFrom;
    private Date timeUntil;
    private String address;
    private List<Integer> location;
    private String distance;
    private float price;
    private int units;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCoverLarge() {
        return coverLarge;
    }

    public void setCoverLarge(String coverLarge) {
        this.coverLarge = coverLarge;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getAgeGrades() {
        return ageGrades;
    }

    public void setAgeGrades(List<String> ageGrades) {
        this.ageGrades = ageGrades;
    }

    public Date getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(Date timeFrom) {
        this.timeFrom = timeFrom;
    }

    public Date getTimeUntil() {
        return timeUntil;
    }

    public void setTimeUntil(Date timeUntil) {
        this.timeUntil = timeUntil;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Integer> getLocation() {
        return location;
    }

    public void setLocation(List<Integer> location) {
        this.location = location;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
