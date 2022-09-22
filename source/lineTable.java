/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Lenovo
 */
public class lineTable extends AbstractTableModel {

    private ArrayList<line> lineList;

    private String[] colNames = {"ID", "Item", "Price", "Count", "Total"};

    public lineTable(ArrayList<line> lineList) {
        this.lineList = lineList;
    }

    public ArrayList<line> getLineList() {
        return lineList;
    }

    public void setLineList(ArrayList<line> lineList) {
        this.lineList = lineList;
    }

    @Override
    public int getRowCount() {
        return this.lineList.size();
    }

    @Override
    public int getColumnCount() {
        return this.colNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        line line = this.lineList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return line.getItem();
            case 2:
                return line.getPrice();
            case 3:
                return line.getCount();
            case 4:
                return line.getTotal();
        }
        return "";
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "ID";
            case 1:
                return "Item";
            case 2:
                return "Price";
            case 3:
                return "Count";
            case 4:
                return "Total";
        }
        return "";
    }
}
