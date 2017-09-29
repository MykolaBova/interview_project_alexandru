package com.alex.places.client.components;

import com.alex.places.client.PlaceFinderAppService;
import com.alex.places.client.PlaceFinderAppServiceAsync;
import com.alex.places.client.components.event.ChangePlaceDetailsEvent;
import com.alex.places.client.components.event.ChangePlaceDetailsEventHandler;
import com.alex.places.client.components.event.HasChangePlaceDetailsEventHandlers;
import com.alex.places.shared.dto.PlaceDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

public class PlaceDetailsForm extends Composite implements HasChangePlaceDetailsEventHandlers {
  private final PlaceFinderAppServiceAsync placeFinderService = GWT.create(PlaceFinderAppService.class);

  final DialogBox dialogBox = new DialogBox();

  private final TextBox nameTextBox = new TextBox();
  private final TextBox ratingTextBox = new TextBox();
  private final Button saveButton = new Button("Save");
  private final Button cancelButton = new Button("Cancel");

  private PlaceDTO placeDTO;

  public PlaceDetailsForm() {
    dialogBox.setAnimationEnabled(true);
    dialogBox.setText("Edit place");

    VerticalPanel panel = new VerticalPanel();

    HorizontalPanel namePanel = new HorizontalPanel();
    namePanel.add(new Label("Name:"));
    namePanel.add(nameTextBox);
    panel.add(namePanel);

    HorizontalPanel ratingPanel = new HorizontalPanel();
    ratingPanel.add(new Label("Rating:"));
    ratingPanel.add(ratingTextBox);
    panel.add(ratingPanel);

    HorizontalPanel buttonsPanel = new HorizontalPanel();
    buttonsPanel.add(saveButton);
    buttonsPanel.add(cancelButton);
    panel.add(buttonsPanel);

    setPlaceDTO(null);

    dialogBox.setWidget(panel);

    saveButton.addClickHandler(event -> {
      placeDTO.setName(nameTextBox.getText());
      placeDTO.setRating(ratingTextBox.getText());
      placeFinderService.savePlace(placeDTO, new AsyncCallback<PlaceDTO>() {
        @Override
        public void onFailure(Throwable throwable) {
          dialogBox.hide();
        }

        @Override
        public void onSuccess(PlaceDTO result) {
          fireEvent(new ChangePlaceDetailsEvent(placeDTO));
          dialogBox.hide();
        }
      });
    });

    cancelButton.addClickHandler(event -> dialogBox.hide());
  }

  public void setPlaceDTO(PlaceDTO placeDTO) {
    this.placeDTO = placeDTO;
    saveButton.setEnabled(placeDTO != null);
    if (placeDTO != null) {
      nameTextBox.setText(placeDTO.getName());
      ratingTextBox.setText(placeDTO.getRating());
    }
  }

  public void show() {
    dialogBox.center();
    dialogBox.show();
  }

  @Override
  public HandlerRegistration addHasChangePlaceDetailsEventHandler(ChangePlaceDetailsEventHandler changePlaceDetailsEventHandler) {
    return addHandler(changePlaceDetailsEventHandler, ChangePlaceDetailsEvent.TYPE);
  }
}
