package com.mmcs.trackapp.model;

public class FeedbackModel {
    private String feedback;

    private String id;

    private String created_on;

    private String image;

    private String user_id;

    private String customer_id;

    public String getFeedback ()
    {
        return feedback;
    }

    public void setFeedback (String feedback)
    {
        this.feedback = feedback;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getCreated_on ()
    {
        return created_on;
    }

    public void setCreated_on (String created_on)
    {
        this.created_on = created_on;
    }

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getCustomer_id ()
    {
        return customer_id;
    }

    public void setCustomer_id (String customer_id)
    {
        this.customer_id = customer_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [feedback = "+feedback+", id = "+id+", created_on = "+created_on+", image = "+image+", user_id = "+user_id+", customer_id = "+customer_id+"]";
    }

}
