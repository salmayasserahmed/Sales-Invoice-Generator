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
public class headerTable extends AbstractTableModel {

    private ArrayList<header> headerList;

    private String[] colNames = {"ID", "Date", "Customer", "Total"};

    public headerTable(ArrayList<header> headerList) {
        this.headerList = headerList;
    }

    public ArrayList<header> getHeaderList() {
        return headerList;
    }

    public void setHeaderList(ArrayList<header> headerList) {
        this.headerList = headerList;
    }

    @Override
    public int getRowCount() {
        return this.headerList.size();
    }

    @Override
    public int getColumnCount() {
        return this.colNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        header header = this.headerList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return header.getId();
            case 1:
                return header.getDate();
            case 2:
                return header.getCustomer();
            case 3:
                return header.getTotal();
        }
        return "";
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "ID";
            case 1:
                return "Date";
            case 2:
                return "Customer";
            case 3:
                return "Total";
        }
        return "";
    }
}
