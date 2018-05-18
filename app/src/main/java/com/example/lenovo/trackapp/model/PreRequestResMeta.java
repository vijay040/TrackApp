package com.example.lenovo.trackapp.model;

import java.util.ArrayList;

public class PreRequestResMeta {
    private ArrayList<PreRequestModel> response;

    public ArrayList<PreRequestModel> getResponse ()
    {
        return response;
    }

    public void setResponse (ArrayList<PreRequestModel> response)
    {
        this.response = response;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [response = "+response+"]";
    }
}
