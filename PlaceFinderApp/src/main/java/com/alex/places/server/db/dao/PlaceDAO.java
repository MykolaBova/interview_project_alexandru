package com.alex.places.server.db.dao;

import com.alex.places.server.db.entity.Place;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class PlaceDAO extends PlaceFinderDAO<Place> {
  private final Logger log = LoggerFactory.getLogger(PlaceDAO.class);

  public PlaceDAO() {
    super(Place.class);
  }

  public Place findByGooglePlaceId(String googlePlaceId) {
    Place place = null;
    TypedQuery<Place> query = em().createQuery(
        "SELECT p FROM Place p WHERE p.googlePlaceId = :googlePlaceId", Place.class);
    try {
      place = query.setParameter("googlePlaceId", googlePlaceId).getSingleResult();
    } catch (NoResultException e) {
      log.info("The place with google id " + googlePlaceId + " does not exist.");
    }
    return place;
  }
}
