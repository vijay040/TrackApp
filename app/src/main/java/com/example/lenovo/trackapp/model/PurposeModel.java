package com.example.lenovo.trackapp.model;

public class PurposeModel {
    private String id;

    private String purpose;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getPurpose ()
    {
        return purpose;
    }

    public void setPurpose (String purpose)
    {
        this.purpose = purpose;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", purpose = "+purpose+"]";
    }
}


