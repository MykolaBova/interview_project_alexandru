package com.alex.places.client.components.event;

import com.alex.places.shared.dto.PlaceDTO;
import com.google.gwt.event.shared.GwtEvent;

public class ChangePlaceDetailsEvent extends GwtEvent<ChangePlaceDetailsEventHandler> {
  public static final Type<ChangePlaceDetailsEventHandler> TYPE = new Type<>();

  private PlaceDTO placeDTO;

  public ChangePlaceDetailsEvent(PlaceDTO placeDTO) {
    this.placeDTO = placeDTO;
  }

  public PlaceDTO getPlaceDTO() {
    return placeDTO;
  }

  @Override
  public Type<ChangePlaceDetailsEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(ChangePlaceDetailsEventHandler changePlaceDetailsEventHandler) {
    changePlaceDetailsEventHandler.onChangePlaceDetails(this);
  }
}
