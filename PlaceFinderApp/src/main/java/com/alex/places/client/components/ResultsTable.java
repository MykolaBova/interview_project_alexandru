package com.alex.places.client.components;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ResultsTable extends Composite {
  private FlexTable resultsTable = new FlexTable();

  public ResultsTable() {
    VerticalPanel panel = new VerticalPanel();
    panel.add(resultsTable);
    initWidget(panel);
  }

  public void setHeader(String... columnNames) {
    resultsTable.setText(0, 0, "#");

    int pos = 1;
    for (String columnName : columnNames) {
      resultsTable.setText(0, pos++, columnName);
    }
  }

  public void addRow(String... values) {
    int row = resultsTable.getRowCount();
    resultsTable.setText(row, 0, Integer.toString(row));

    int pos = 1;
    for (String value : values) {
      resultsTable.setText(row, pos++, value);
    }
  }
}
