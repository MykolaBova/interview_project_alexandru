package com.alex.places.client.components;

import com.alex.places.shared.dto.PlaceDTO;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;

import java.util.List;

public class ResultsTable extends Composite {
  private static final String COLUMN_TITLE_ROW = "#";
  private static final String COLUMN_TITLE_NAME = "Name";
  private static final String COLUMN_TITLE_RATING = "Rating";

  private final CellTable<PlaceDTO> resultsTable = new CellTable<>();
  private final AsyncDataProvider<PlaceDTO> dataProvider;

  public ResultsTable() {
    dataProvider = new AsyncDataProvider<PlaceDTO>() {
      @Override
      protected void onRangeChanged(HasData<PlaceDTO> hasData) {

      }
    };

    VerticalPanel panel = new VerticalPanel();
    resultsTable.addColumn(buildIndexColumn(), buildHeader(COLUMN_TITLE_ROW));
    resultsTable.addColumn(buildColumnName(), buildHeader(COLUMN_TITLE_NAME));
    resultsTable.addColumn(buildColumnRating(), buildHeader(COLUMN_TITLE_RATING));
    dataProvider.addDataDisplay(resultsTable);
    panel.add(resultsTable);

    initWidget(panel);
  }

  public void updateTableData(List<PlaceDTO> places) {
    resultsTable.setVisibleRange(0, places.size());
    dataProvider.updateRowData(0, places);
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
    Column<PlaceDTO, String> columnName = new Column<PlaceDTO, String>(new EditTextCell()) {
      @Override
      public String getValue(PlaceDTO placeDTO) {
        return placeDTO.getName();
      }
    };
    columnName.setDataStoreName(COLUMN_TITLE_NAME);
    return columnName;
  }

  private Column<PlaceDTO, String> buildColumnRating() {
    Column<PlaceDTO, String> columnRating = new Column<PlaceDTO, String>(new EditTextCell()) {
      @Override
      public String getValue(PlaceDTO placeDTO) {
        return placeDTO.getRating();
      }
    };
    columnRating.setDataStoreName(COLUMN_TITLE_RATING);
    return columnRating;
  }
}
