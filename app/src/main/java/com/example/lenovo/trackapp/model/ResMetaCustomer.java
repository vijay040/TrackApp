package com.example.lenovo.trackapp.model;

import java.util.ArrayList;

public class ResMetaCustomer {

    private ArrayList<CustomerModel> response;

    public ArrayList<CustomerModel>  getResponse ()
    {
        return response;
    }

    public void setResponse (ArrayList<CustomerModel> response)
    {
        this.response = response;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [response = "+response+"]";
    }
}
