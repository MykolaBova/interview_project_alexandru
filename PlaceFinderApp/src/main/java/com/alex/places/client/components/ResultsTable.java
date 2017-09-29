package com.alex.places.client.components;

import com.alex.places.client.components.event.ChangePlaceDetailsEvent;
import com.alex.places.client.components.event.ChangePlaceDetailsEventHandler;
import com.alex.places.shared.dto.PlaceDTO;
import com.google.gwt.cell.client.*;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResultsTable extends Composite {
  Logger logger = Logger.getLogger(ResultsTable.class.getName());
  private final ProvidesKey<PlaceDTO> KEY_PROVIDER = (item) -> item == null ? null : item.getGooglePlaceId();

  private static final String COLUMN_TITLE_ROW = "#";
  private static final String COLUMN_TITLE_NAME = "Name";
  private static final String COLUMN_TITLE_RATING = "Rating";
  private static final String COLUMN_TITLE_EDIT = "Edit";
  private static final String COLUMN_TITLE_DELETE = "Delete";

  private final CellTable<PlaceDTO> resultsTable = new CellTable<>();
  private final SingleSelectionModel<PlaceDTO> selectionModel = new SingleSelectionModel<>(KEY_PROVIDER);

  private final PlaceDetailsForm placeDetailsForm = new PlaceDetailsForm();

  public ResultsTable() {
    VerticalPanel panel = new VerticalPanel();
    resultsTable.addColumn(buildIndexColumn(), buildHeader(COLUMN_TITLE_ROW));
    resultsTable.addColumn(buildColumnName(), buildHeader(COLUMN_TITLE_NAME));
    resultsTable.addColumn(buildColumnRating(), buildHeader(COLUMN_TITLE_RATING));
    resultsTable.addColumn(buildColumnUpdate());
    resultsTable.addColumn(buildColumnDelete());
    resultsTable.setSelectionModel(selectionModel);
    panel.add(resultsTable);

    initWidget(panel);
  }

  public void updateTableData(List<PlaceDTO> places) {
    resultsTable.setVisibleRange(0, places.size());
    resultsTable.setRowData(0, places);
  }

  private Header<String> buildHeader(final String text) {
    return new Header<String>(new TextCell()) {
      @Override
      public String getValue() {
        return text;
      }
    };
  }

  private Column<PlaceDTO, String> buildIndexColumn() {
    return new Column<PlaceDTO, String>(new AbstractCell<String>() {
      @Override
      public void render(Context context, String text, SafeHtmlBuilder safeHtmlBuilder) {
        safeHtmlBuilder.append(context.getIndex() + 1);
      }
    }) {
      @Override
      public String getValue(PlaceDTO placeDTO) {
        return null;
      }
    };
  }

  private Column<PlaceDTO, String> buildColumnName() {
    Column<PlaceDTO, String> columnName = new Column<PlaceDTO, String>(new TextCell()) {
      @Override
      public String getValue(PlaceDTO placeDTO) {
        return placeDTO.getName();
      }
    };
    columnName.setDataStoreName(COLUMN_TITLE_NAME);
    return columnName;
  }

  private Column<PlaceDTO, String> buildColumnRating() {
    Column<PlaceDTO, String> columnRating = new Column<PlaceDTO, String>(new TextCell()) {
      @Override
      public String getValue(PlaceDTO placeDTO) {
        return placeDTO.getRating();
      }
    };
    columnRating.setDataStoreName(COLUMN_TITLE_RATING);
    return columnRating;
  }

  private Column<PlaceDTO, String> buildColumnUpdate() {
    Column<PlaceDTO, String> columnUpdate = new Column<PlaceDTO, String>(new TextButtonCell()) {
      @Override
      public String getValue(PlaceDTO placeDTO) {
        return COLUMN_TITLE_EDIT;
      }
    };
    columnUpdate.setDataStoreName(COLUMN_TITLE_EDIT);
    columnUpdate.setFieldUpdater((int i, PlaceDTO placeDTO, String s) -> {
        placeDetailsForm.setPlaceDTO(selectionModel.getSelectedObject());
        placeDetailsForm.addHasChangePlaceDetailsEventHandler(new ChangePlaceDetailsEventHandler() {
          @Override
          public void onChangePlaceDetails(ChangePlaceDetailsEvent event) {
            PlaceDTO placeDTO = event.getPlaceDTO();
            logger.log(Level.SEVERE, placeDTO.getRating());
            selectionModel.setSelected(placeDTO, false);
          }
        });
        placeDetailsForm.show();
    });
    return columnUpdate;
  }

  private Column<PlaceDTO, String> buildColumnDelete() {
    Column<PlaceDTO, String> columnDelete = new Column<PlaceDTO, String>(new TextButtonCell()) {
      @Override
      public String getValue(PlaceDTO placeDTO) {
        return COLUMN_TITLE_DELETE;
      }
    };
    columnDelete.setDataStoreName(COLUMN_TITLE_DELETE);
    return columnDelete;
  }
}
