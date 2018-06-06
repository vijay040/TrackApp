package com.example.lenovo.trackapp.model;

public class PreRequestModel {
    private String id;

    private String status;

    private String address;

    private String customer_name;

    private String meeting_id;

    private String date;

    private String advance;

    private String description;

    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getCustomer_name ()
    {
        return customer_name;
    }

    public void setCustomer_name (String customer_name)
    {
        this.customer_name = customer_name;
    }

    public String getMeeting_id ()
    {
        return meeting_id;
    }

    public void setMeeting_id (String meeting_id)
    {
        this.meeting_id = meeting_id;
    }

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    public String getAdvance ()
    {
        return advance;
    }

    public void setAdvance (String advance)
    {
        this.advance = advance;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", status = "+status+", address = "+address+", customer_name = "+customer_name+", meeting_id = "+meeting_id+", date = "+date+", advance = "+advance+"]";
    }
}
