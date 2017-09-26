package com.alex.places.client;

import com.alex.places.shared.dto.PlaceDTO;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("find")
public interface PlaceFinderService extends RemoteService {
  List<PlaceDTO> getPlaces(String city);
}
