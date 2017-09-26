package com.alex.places.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>PlaceFinderService</code>.
 */
public interface PlaceFinderServiceAsync {
  void getPlaces(String input, AsyncCallback<String> callback);
}
