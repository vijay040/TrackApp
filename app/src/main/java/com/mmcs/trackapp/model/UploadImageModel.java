package com.mmcs.trackapp.model;

public class UploadImageModel {
    private String image;

    private String msg;

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    public String getMsg ()
    {
        return msg;
    }

    public void setMsg (String msg)
    {
        this.msg = msg;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [image = "+image+", msg = "+msg+"]";
    }
}
