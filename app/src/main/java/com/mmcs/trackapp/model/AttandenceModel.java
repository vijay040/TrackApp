package com.mmcs.trackapp.model;

public class AttandenceModel {

    private String status;

    private String location;

    private String date_time;

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getLocation ()
    {
        return location;
    }

    public void setLocation (String location)
    {
        this.location = location;
    }

    public String getDate_time ()
    {
        return date_time;
    }

    public void setDate_time (String date_time)
    {
        this.date_time = date_time;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [status = "+status+", location = "+location+", date_time = "+date_time+"]";
    }
}

