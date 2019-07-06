package com.powerreviews.project.persistence;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "restaurant")
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String type;

    @Column
    private String latitude;

    @Column
    private String longitude;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RestaurantReviewEntity> reviews;

    public RestaurantEntity(){}

    public RestaurantEntity(String name, String type, String latitude, String longitude) {
        this.name = name;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getType() {
        return type;

    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RestaurantEntity{" +
                "id=" + id +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", lat='" + latitude + '\'' +
                ", long='" + longitude + '\'' +
                '}';
    }
}
