package com.mmcs.trackapp.model;

public class CustomerModel {

    private String id;

    private String company_id;

    private String phone;

    private String pin;

    private String email;

    private String address;

    private String tax_details;

    private String country_id;

    private String customer_name;

    private String place;

    private String location_id;

    private String city;

    private String customer_company;

    public boolean isVisible=false;

    public String getCustomer_company() {
        return customer_company;
    }

    public void setCustomer_company(String customer_company) {
        this.customer_company = customer_company;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTax_details() {
        return tax_details;
    }

    public void setTax_details(String tax_details) {
        this.tax_details = tax_details;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
