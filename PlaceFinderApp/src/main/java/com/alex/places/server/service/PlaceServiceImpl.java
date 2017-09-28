package com.alex.places.server.service;

import com.alex.places.server.db.converter.PlaceConverter;
import com.alex.places.server.db.dao.PlaceDAO;
import com.alex.places.server.db.entity.Place;
import com.alex.places.server.thirdparty.service.GooglePlacesService;
import com.alex.places.shared.dto.PlaceDTO;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class PlaceServiceImpl implements PlaceService {
  @Inject
  private GooglePlacesService googlePlacesService;

  @Inject
  private PlaceDAO placeDAO;

  @Override
  public List<PlaceDTO> findByCity(String city) {
    List<PlaceDTO> places = new ArrayList<>();
    PlacesSearchResponse response = googlePlacesService.findPlacesByCity(city);
    if (response != null) {
      for (PlacesSearchResult result : response.results) {
        String googlePlaceId = result.placeId;
        Place place = placeDAO.findByGooglePlaceId(googlePlaceId);
        if (place == null || !place.isModifiedByUser()) {
          place = PlaceConverter.fromGooglePlace(result);
          placeDAO.save(place);
        }
        places.add(PlaceConverter.toDTO(place));
      }
    }
    return places;
  }
}
