package com.alex.places.shared.dto;

import java.io.Serializable;

public class PlaceDTO implements Serializable {
  private String name;
  private String rating;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRating() {
    return rating;
  }

  public void setRating(String rating) {
    this.rating = rating;
  }
}
