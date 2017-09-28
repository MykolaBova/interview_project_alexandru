package com.alex.places.shared.dto;

import java.io.Serializable;

public class PlaceDTO implements Serializable {
  private String name;
  private float rating;

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
}
