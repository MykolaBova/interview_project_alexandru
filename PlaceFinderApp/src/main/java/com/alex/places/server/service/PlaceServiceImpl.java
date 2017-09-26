package com.alex.places.server.service;

import com.alex.places.server.thirdparty.service.GooglePlacesService;
import com.alex.places.shared.dto.PlaceDTO;
import com.google.inject.Inject;
import com.google.maps.model.PlacesSearchResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PlaceServiceImpl implements PlaceService {
  @Inject
  private GooglePlacesService googlePlacesService;

  @Override
  public List<PlaceDTO> findByCity(String city) {
    List<PlaceDTO> places = new ArrayList<>();
    PlacesSearchResponse response = googlePlacesService.findPlacesByCity(city);
    if (response != null) {
      return Arrays.stream(response.results)
          .map(result -> new PlaceDTO(result.name, String.valueOf(result.rating)))
          .collect(Collectors.toList());
    }
    return places;
  }
}
