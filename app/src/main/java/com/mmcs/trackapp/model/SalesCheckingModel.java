package com.mmcs.trackapp.model;

import java.io.Serializable;

/**
 * Created by Lenovo on 16-08-2018.
 */

public class SalesCheckingModel implements Serializable {
    private String vendor_name;
    private String date;
    private String valid_till;
    private String cost;
    private String margin;

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValid_till() {
        return valid_till;
    }

    public void setValid_till(String valid_till) {
        this.valid_till = valid_till;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getMargin() {
        return margin;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }
}
