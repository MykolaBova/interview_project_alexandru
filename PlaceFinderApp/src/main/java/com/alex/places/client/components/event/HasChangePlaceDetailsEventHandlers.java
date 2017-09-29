package com.alex.places.client.components.event;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface HasChangePlaceDetailsEventHandlers extends HasHandlers {
  HandlerRegistration addHasChangePlaceDetailsEventHandler(ChangePlaceDetailsEventHandler changePlaceDetailsEventHandler);
}
