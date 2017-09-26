package com.alex.places.server;

import com.alex.places.client.PlaceFinderService;
import com.alex.places.shared.dto.PlaceDTO;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.util.ArrayList;
import java.util.List;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class PlaceFinderServiceImpl extends RemoteServiceServlet implements
    PlaceFinderService {

  public List<PlaceDTO> getPlaces(String city) {
    // Escape data from the client to avoid cross-site script vulnerabilities.
    city = escapeHtml(city);
    List<PlaceDTO> places = new ArrayList<>();
    places.add(new PlaceDTO("First place " + city, "3.5"));
    places.add(new PlaceDTO("Second place " + city, "2.8"));
    return places;
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
