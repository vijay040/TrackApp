package com.mmcs.trackapp.model;

public class CurrencyModel {
    private String id;

    private String currency_name;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getCurrency_name ()
    {
        return currency_name;
    }

    public void setCurrency_name (String currency_name)
    {
        this.currency_name = currency_name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", currency_name = "+currency_name+"]";
    }
}

