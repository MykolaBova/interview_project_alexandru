package com.alex.places.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

public class PlaceFinder implements EntryPoint {
  private static final String SERVER_ERROR = "An error occurred while trying to get the places.";

  private final PlaceFinderServiceAsync placeFinderService = GWT.create(PlaceFinderService.class);

  public void onModuleLoad() {
    final VerticalPanel mainPanel = new VerticalPanel();
    final Button findPlacesButton = new Button("Find places");
    final ListBox citySelector = new ListBox();
    citySelector.addItem("New York");
    citySelector.addItem("Paris");
    citySelector.addItem("London");
    citySelector.addItem("Sidney");
    final Label errorLabel = new Label();

    findPlacesButton.addStyleName("findPlacesButton");

    RootPanel.get("places").add(mainPanel);
    RootPanel.get("citySelectorContainer").add(citySelector);
    RootPanel.get("findPlacesButtonContainer").add(findPlacesButton);
    RootPanel.get("errorLabelContainer").add(errorLabel);

    citySelector.setFocus(true);

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
        placeFinderService.getPlaces(city, new AsyncCallback<String>() {
          public void onFailure(Throwable caught) {
            dialogBox.setText("Error");
            serverResponseLabel.addStyleName("serverResponseLabelError");
            serverResponseLabel.setHTML(SERVER_ERROR);
            dialogBox.center();
            closeButton.setFocus(true);
          }

          public void onSuccess(String response) {
            dialogBox.setText("Success");
            serverResponseLabel.removeStyleName("serverResponseLabelError");
            serverResponseLabel.setHTML(response);
            dialogBox.center();
            closeButton.setFocus(true);
          }
        });
      }
    }

    FindPlacesHandler handler = new FindPlacesHandler();
    findPlacesButton.addClickHandler(handler);
    citySelector.addKeyUpHandler(handler);
  }
}
