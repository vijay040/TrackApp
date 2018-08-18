package com.mmcs.trackapp.model;

import java.io.Serializable;

public class VQuotationModel implements Serializable {
    private String ex_rate;

    private String v_quot_mstr_id;

    private String margin_curr;

    private String audit_mas_id;

    private String commodity;

    private String s_no;

    private String charge_code;

    private String v_quot_no;

    private String margin;

    private String crg_amt;

    private String unit_base;

    private String container_size;

    private String currency;

    public String getEx_rate() {
        return ex_rate;
    }

    public void setEx_rate(String ex_rate) {
        this.ex_rate = ex_rate;
    }

    public String getV_quot_mstr_id() {
        return v_quot_mstr_id;
    }

    public void setV_quot_mstr_id(String v_quot_mstr_id) {
        this.v_quot_mstr_id = v_quot_mstr_id;
    }

    public String getMargin_curr() {
        return margin_curr;
    }

    public void setMargin_curr(String margin_curr) {
        this.margin_curr = margin_curr;
    }

    public String getAudit_mas_id() {
        return audit_mas_id;
    }

    public void setAudit_mas_id(String audit_mas_id) {
        this.audit_mas_id = audit_mas_id;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getS_no() {
        return s_no;
    }

    public void setS_no(String s_no) {
        this.s_no = s_no;
    }

    public String getCharge_code() {
        return charge_code;
    }

    public void setCharge_code(String charge_code) {
        this.charge_code = charge_code;
    }

    public String getV_quot_no() {
        return v_quot_no;
    }

    public void setV_quot_no(String v_quot_no) {
        this.v_quot_no = v_quot_no;
    }

    public String getMargin() {
        return margin;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }

    public String getCrg_amt() {
        return crg_amt;
    }

    public void setCrg_amt(String crg_amt) {
        this.crg_amt = crg_amt;
    }

    public String getUnit_base() {
        return unit_base;
    }

    public void setUnit_base(String unit_base) {
        this.unit_base = unit_base;
    }

    public String getContainer_size() {
        return container_size;
    }

    public void setContainer_size(String container_size) {
        this.container_size = container_size;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
