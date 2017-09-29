package com.alex.places.server.service;

import com.alex.places.shared.dto.PlaceDTO;

import java.util.List;

public interface PlaceService {
  List<PlaceDTO> findByCity(String city);
  void savePlace(PlaceDTO placeDTO);
}
