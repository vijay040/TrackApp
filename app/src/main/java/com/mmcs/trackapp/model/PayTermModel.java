package com.mmcs.trackapp.model;

/**
 * Created by Lenovo on 25-09-2018.
 */

public class PayTermModel {
    private String id;
    private String pay_term_descrp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPay_term_descrp() {
        return pay_term_descrp;
    }

    public void setPay_term_descrp(String pay_term_descrp) {
        this.pay_term_descrp = pay_term_descrp;
    }

    public String getPay_days() {
        return pay_days;
    }

    public void setPay_days(String pay_days) {
        this.pay_days = pay_days;
    }

    private String pay_days;
}

