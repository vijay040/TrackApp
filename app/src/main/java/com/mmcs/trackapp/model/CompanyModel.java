package com.mmcs.trackapp.model;

/**
 * Created by Lenovo on 25-09-2018.
 */

public class CompanyModel {
    private String id;
    private String company_name;
    private String parent_company_id;
    private String company_code;
    private String location_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getParent_company_id() {
        return parent_company_id;
    }

    public void setParent_company_id(String parent_company_id) {
        this.parent_company_id = parent_company_id;
    }

    public String getCompany_code() {
        return company_code;
    }

    public void setCompany_code(String company_code) {
        this.company_code = company_code;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }
}
