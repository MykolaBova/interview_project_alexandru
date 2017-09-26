package com.alex.places.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("find")
public interface PlaceFinderService extends RemoteService {
  String getPlaces(String city);
}
