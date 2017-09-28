package com.alex.places.server.db.converter;

import com.alex.places.server.db.entity.Place;
import com.alex.places.shared.dto.PlaceDTO;
import com.google.maps.model.PlacesSearchResult;

public class PlaceConverter {
  public static Place fromGooglePlace(PlacesSearchResult result) {
    Place place = new Place();
    place.setGooglePlaceId(result.placeId);
    place.setName(result.name);
    place.setRating(result.rating);
    return place;
  }

  public static PlaceDTO toDTO(Place place) {
    PlaceDTO placeDTO = new PlaceDTO();
    placeDTO.setName(place.getName());
    placeDTO.setRating(String.format ("%,.2f", place.getRating()));
    return placeDTO;
  }
}
