/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Lenovo
 */
public class header {

    private int id;
    private Date date;
    private String customer;
    private double total;
    private ArrayList<line> lines;

    public header(int id, Date date, String customer) {
        this.id = id;
        this.date = date;
        this.customer = customer;
    }

    public static header getHeaderByID(ArrayList<header> list, int id) {
        for (header temp : list) {
            if (temp.getId() == id) {
                return temp;
            }
        }
        return null;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotal() {
        total = 0;
        for (line l : this.getLines()) {
            total += l.getTotal();
        }

        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public ArrayList<line> getLines() {
        if (this.lines == null) {
            lines = new ArrayList<line>();
        }
        return lines;
    }

    public void setLines(ArrayList<line> lines) {
        this.lines = lines;
    }

    @Override
    public String toString() {
        return this.getId() + " " + this.getDate() + " " + this.getCustomer();

    }

}
