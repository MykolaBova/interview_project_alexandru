package com.alex.places.client;

import com.alex.places.shared.dto.PlaceDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

/**
 * The async counterpart of <code>PlaceFinderAppService</code>.
 */
public interface PlaceFinderAppServiceAsync {
  void getPlaces(String input, AsyncCallback<List<PlaceDTO>> callback);
}
