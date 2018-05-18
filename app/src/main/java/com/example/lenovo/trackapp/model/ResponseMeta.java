package com.example.lenovo.trackapp.model;

import java.util.ArrayList;

public class ResponseMeta {
    private ArrayList<PurposeModel> response;

    public ArrayList<PurposeModel> getResponse ()
    {
        return response;
    }

    public void setResponse (ArrayList<PurposeModel> response)
    {
        this.response = response;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [response = "+response+"]";
    }
}


