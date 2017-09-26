package com.alex.places.server.service;

import com.alex.places.shared.dto.PlaceDTO;

import java.util.ArrayList;
import java.util.List;

public class PlaceServiceImpl implements PlaceService {
  @Override
  public List<PlaceDTO> findByCity(String city) {
    List<PlaceDTO> places = new ArrayList<>();
    places.add(new PlaceDTO("First place " + city, "3.5"));
    places.add(new PlaceDTO("Second place " + city, "2.8"));
    return places;
  }
}
