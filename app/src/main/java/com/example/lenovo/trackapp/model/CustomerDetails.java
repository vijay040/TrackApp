package com.example.lenovo.trackapp.model;

public class CustomerDetails {

    private String phone;

    private String status;

    private String tax_details;

    private String country_id;

    private String customer_name;

    private String city;

    private String location_id;

    private String id;

    private String address;

    private String email;

    private String pin;

    private String company_name;

    private String place;

    public String getPhone ()
    {
        return phone;
    }

    public void setPhone (String phone)
    {
        this.phone = phone;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getTax_details ()
    {
        return tax_details;
    }

    public void setTax_details (String tax_details)
    {
        this.tax_details = tax_details;
    }

    public String getCountry_id ()
    {
        return country_id;
    }

    public void setCountry_id (String country_id)
    {
        this.country_id = country_id;
    }

    public String getCustomer_name ()
    {
        return customer_name;
    }

    public void setCustomer_name (String customer_name)
    {
        this.customer_name = customer_name;
    }

    public String getCity ()
    {
        return city;
    }

    public void setCity (String city)
    {
        this.city = city;
    }

    public String getLocation_id ()
    {
        return location_id;
    }

    public void setLocation_id (String location_id)
    {
        this.location_id = location_id;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getPin ()
    {
        return pin;
    }

    public void setPin (String pin)
    {
        this.pin = pin;
    }

    public String getCompany_name ()
    {
        return company_name;
    }

    public void setCompany_name (String company_name)
    {
        this.company_name = company_name;
    }

    public String getPlace ()
    {
        return place;
    }

    public void setPlace (String place)
    {
        this.place = place;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [phone = "+phone+", status = "+status+", tax_details = "+tax_details+", country_id = "+country_id+", customer_name = "+customer_name+", city = "+city+", location_id = "+location_id+", id = "+id+", address = "+address+", email = "+email+", pin = "+pin+", company_name = "+company_name+", place = "+place+"]";
    }
}

