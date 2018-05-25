package com.example.lenovo.trackapp.model;

public class MeetingModel {
    private String agenda;

    private String id;

    private String Time;

    private String Date;

    private String contact_person;

    private String discription;

    private String purpose;

    private String customer;

    public String getAgenda ()
    {
        return agenda;
    }

    public void setAgenda (String agenda)
    {
        this.agenda = agenda;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getTime ()
    {
        return Time;
    }

    public void setTime (String Time)
    {
        this.Time = Time;
    }

    public String getDate ()
    {
        return Date;
    }

    public void setDate (String Date)
    {
        this.Date = Date;
    }

    public String getContact_person ()
    {
        return contact_person;
    }

    public void setContact_person (String contact_person)
    {
        this.contact_person = contact_person;
    }

    public String getDiscription ()
    {
        return discription;
    }

    public void setDiscription (String discription)
    {
        this.discription = discription;
    }

    public String getPurpose ()
    {
        return purpose;
    }

    public void setPurpose (String purpose)
    {
        this.purpose = purpose;
    }

    public String getCustomer ()
    {
        return customer;
    }

    public void setCustomer (String customer)
    {
        this.customer = customer;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [agenda = "+agenda+", id = "+id+", Time = "+Time+", Date = "+Date+", contact_person = "+contact_person+", discription = "+discription+", purpose = "+purpose+", customer = "+customer+"]";
    }
}


