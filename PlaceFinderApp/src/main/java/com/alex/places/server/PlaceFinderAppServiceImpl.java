package com.alex.places.server;

import com.alex.places.client.PlaceFinderAppService;
import com.alex.places.server.service.PlaceService;
import com.alex.places.shared.dto.PlaceDTO;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
@Singleton
public class PlaceFinderAppServiceImpl extends RemoteServiceServlet implements
    PlaceFinderAppService {

  @Inject
  private PlaceService placeService;

  public List<PlaceDTO> getPlaces(String city) {
    // Escape data from the client to avoid cross-site script vulnerabilities.
    city = escapeHtml(city);
    return placeService.findByCity(city);
  }

  @Override
  public void savePlace(PlaceDTO placeDTO) {
    placeService.savePlace(placeDTO);
  }

  /**
   * Escape an html string. Escaping data received from the client helps to
   * prevent cross-site script vulnerabilities.
   * 
   * @param html the html string to escape
   * @return the escaped string
   */
  private String escapeHtml(String html) {
    if (html == null) {
      return null;
    }
    return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(
        ">", "&gt;");
  }
}
