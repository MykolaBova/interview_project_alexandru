package com.alex.places.client;

import com.alex.places.shared.dto.PlaceDTO;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.List;

public class PlaceFinderApp implements EntryPoint {
  private static final String SERVER_ERROR = "An error occurred while trying to get the places.";

  private final PlaceFinderAppServiceAsync placeFinderService = GWT.create(PlaceFinderAppService.class);

  public void onModuleLoad() {
    final VerticalPanel mainPanel = new VerticalPanel();
    final Button findPlacesButton = new Button("Find places");
    final ListBox citySelector = new ListBox();
    final FlexTable placesTable = new FlexTable();
    final Label errorLabel = new Label();

    citySelector.addItem("New York");
    citySelector.addItem("Paris");
    citySelector.addItem("London");
    citySelector.addItem("Sidney");

    findPlacesButton.addStyleName("findPlacesButton");

    placesTable.setText(0, 0, "#");
    placesTable.setText(0, 1, "Name");
    placesTable.setText(0, 2, "Rating");

    mainPanel.add(placesTable);

    RootPanel.get("places").add(mainPanel);
    RootPanel.get("citySelectorContainer").add(citySelector);
    RootPanel.get("findPlacesButtonContainer").add(findPlacesButton);
    RootPanel.get("errorLabelContainer").add(errorLabel);

    citySelector.setFocus(true);
    placesTable.setVisible(false);

    final DialogBox dialogBox = new DialogBox();
    dialogBox.setAnimationEnabled(true);
    final Button closeButton = new Button("Close");
    closeButton.getElement().setId("closeButton");

    final HTML serverResponseLabel = new HTML();
    VerticalPanel dialogVPanel = new VerticalPanel();
    dialogVPanel.add(serverResponseLabel);
    dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
    dialogVPanel.add(closeButton);
    dialogBox.setWidget(dialogVPanel);

    closeButton.addClickHandler(event -> {
      dialogBox.hide();
      findPlacesButton.setEnabled(true);
      findPlacesButton.setFocus(true);
    });

    class FindPlacesHandler implements ClickHandler, KeyUpHandler {
      public void onClick(ClickEvent event) {
        getPlaces();
      }

      public void onKeyUp(KeyUpEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
          getPlaces();
        }
      }

      private void getPlaces() {
        errorLabel.setText("");
        String city = citySelector.getSelectedItemText();

        findPlacesButton.setEnabled(false);
        serverResponseLabel.setText("");
        placeFinderService.getPlaces(city, new AsyncCallback<List<PlaceDTO>>() {
          public void onFailure(Throwable caught) {
            dialogBox.setText("Error");
            serverResponseLabel.addStyleName("serverResponseLabelError");
            serverResponseLabel.setHTML(SERVER_ERROR);
            dialogBox.center();
            closeButton.setFocus(true);
            findPlacesButton.setEnabled(true);
          }

          public void onSuccess(List<PlaceDTO> places) {
            placesTable.setVisible(!places.isEmpty());
            int pos = 1;
            for (PlaceDTO place : places) {
              placesTable.setText(pos, 0, Integer.toString(pos));
              placesTable.setText(pos, 1, place.getName());
              placesTable.setText(pos, 2, Float.toString(place.getRating()));
              pos++;
            }
            findPlacesButton.setEnabled(true);
          }
        });
      }
    }

    FindPlacesHandler handler = new FindPlacesHandler();
    findPlacesButton.addClickHandler(handler);
    citySelector.addKeyUpHandler(handler);
  }
}
