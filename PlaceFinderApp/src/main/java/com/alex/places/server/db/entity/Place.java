package com.alex.places.server.db.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Place implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String googlePlaceId;
  private String name;
  private float rating;

  @Column(columnDefinition = "boolean default false")
  private Boolean modifiedByUser = Boolean.FALSE;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getGooglePlaceId() {
    return googlePlaceId;
  }

  public void setGooglePlaceId(String googlePlaceId) {
    this.googlePlaceId = googlePlaceId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public float getRating() {
    return rating;
  }

  public void setRating(float rating) {
    this.rating = rating;
  }

  public Boolean isModifiedByUser() {
    return modifiedByUser;
  }

  public void setModifiedByUser(Boolean modifiedByUser) {
    this.modifiedByUser = modifiedByUser;
  }
}
