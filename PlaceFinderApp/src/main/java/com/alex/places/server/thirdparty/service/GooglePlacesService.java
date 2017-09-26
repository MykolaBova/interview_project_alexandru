package com.alex.places.server.thirdparty.service;

import com.google.maps.model.PlacesSearchResponse;

public interface GooglePlacesService {
  PlacesSearchResponse findPlacesByCity(String city);
}
