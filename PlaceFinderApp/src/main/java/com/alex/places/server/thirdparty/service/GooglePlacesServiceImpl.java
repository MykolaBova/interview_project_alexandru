package com.alex.places.server.thirdparty.service;

import com.alex.places.server.thirdparty.annotations.GeocodingApiContext;
import com.alex.places.server.thirdparty.annotations.PlaceApiContext;
import com.google.inject.Inject;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.PlacesApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GooglePlacesServiceImpl implements GooglePlacesService {
  private final Logger log = LoggerFactory.getLogger(GooglePlacesServiceImpl.class);

  private final GeoApiContext placesApiContext;
  private final GeoApiContext geocodingApiContext;

  @Inject
  public GooglePlacesServiceImpl(@PlaceApiContext GeoApiContext placesApiContext, @GeocodingApiContext GeoApiContext geocodingApiContext) {
    this.placesApiContext = placesApiContext;
    this.geocodingApiContext = geocodingApiContext;
  }

  @Override
  public PlacesSearchResponse findPlacesByCity(String city) {
    try {
      LatLng location = getLocationForCity(city);
      return PlacesApi.nearbySearchQuery(placesApiContext, location).radius(5000).await();
    } catch (ApiException | InterruptedException | IOException e) {
      log.error(e.getMessage(), e);
    }
    return null;
  }

  private LatLng getLocationForCity(String city) throws InterruptedException, ApiException, IOException {
    GeocodingResult[] result = GeocodingApi.geocode(geocodingApiContext, city).await();
    if (result != null && result.length > 0) {
      return result[0].geometry.location;
    }
    return null;
  }
}
