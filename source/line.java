/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import javax.swing.JOptionPane;

/**
 *
 * @author Lenovo
 */
public class line {

    private header header;
    private int id;
    private String item;
    private double price;
    private int count;

    public line(header header, String item, double price, int count) {
        this.header = header;
        this.item = item;
        this.price = price;
        this.count = count;
    }

    public header getHeader() {
        return header;
    }

    public void setHeader(header header) {
        this.header = header;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {

        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getTotal() {
        return this.price * this.count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.getHeader().getId() + " " + this.getItem() + " " + this.getPrice() + " " + this.getCount();

    }

}
