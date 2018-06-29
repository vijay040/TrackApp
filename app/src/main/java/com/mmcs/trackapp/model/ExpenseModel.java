package com.mmcs.trackapp.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ExpenseModel implements Serializable{
    private String amount;

    private String descreption;

    private String Time;

    private String expense_type;

    private String Date;

    private String address;

    private String created_on;

    private String customer_name;

    private String image;

    public boolean isVisible=false;

    public String getExpense_type() {
        return expense_type;
    }

    public void setExpense_type(String expense_type) {
        this.expense_type = expense_type;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public String getAmount ()
    {
        return amount;
    }

    public void setAmount (String amount)
    {
        this.amount = amount;
    }

    public String getDescreption ()
    {
        return descreption;
    }

    public void setDescreption (String descreption)
    {
        this.descreption = descreption;
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

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getCreated_on ()
    {
        return created_on;
    }

    public void setCreated_on (String created_on)
    {
        this.created_on = created_on;
    }

    public String getCustomer_name ()
    {
        return customer_name;
    }

    public void setCustomer_name (String customer_name)
    {
        this.customer_name = customer_name;
    }

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [amount = "+amount+", descreption = "+descreption+", Time = "+Time+", expense_type = "+expense_type+", Date = "+Date+", address = "+address+", created_on = "+created_on+", customer_name = "+customer_name+", image = "+image+"]";
    }
}

