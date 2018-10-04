package com.mmcs.trackapp.model;

import java.io.Serializable;

/**
 * Created by Lenovo on 18-09-2018.
 */

public class ExpenseApprovalListModel implements Serializable {
    private String currency_id;

    private String status;

    private String created_on;

    private String update_comment;

    private String image;

    private String final_status;

    private String id;

    private String amount;

    public String getExchange_rate() {
        return exchange_rate;
    }

    public void setExchange_rate(String exchange_rate) {
        this.exchange_rate = exchange_rate;
    }

    private String exchange_rate;

    private String expense_type_id;

    private String user_id;

    private String meeting_id;

    private String comment;
    private String customer_name;
    private String description;
    private String date;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time;

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String address;

    private String update_image;

    public String getCurrency_id ()
    {
        return currency_id;
    }

    public void setCurrency_id (String currency_id)
    {
        this.currency_id = currency_id;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getCreated_on ()
    {
        return created_on;
    }

    public void setCreated_on (String created_on)
    {
        this.created_on = created_on;
    }

    public String getUpdate_comment ()
    {
        return update_comment;
    }

    public void setUpdate_comment (String update_comment)
    {
        this.update_comment = update_comment;
    }

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    public String getFinal_status ()
    {
        return final_status;
    }

    public void setFinal_status (String final_status)
    {
        this.final_status = final_status;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getAmount ()
    {
        return amount;
    }

    public void setAmount (String amount)
    {
        this.amount = amount;
    }

    public String getExpense_type_id ()
    {
        return expense_type_id;
    }

    public void setExpense_type_id (String expense_type_id)
    {
        this.expense_type_id = expense_type_id;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getMeeting_id ()
    {
        return meeting_id;
    }

    public void setMeeting_id (String meeting_id)
    {
        this.meeting_id = meeting_id;
    }

    public String getComment ()
    {
        return comment;
    }

    public void setComment (String comment)
    {
        this.comment = comment;
    }

    public String getUpdate_image ()
    {
        return update_image;
    }

    public void setUpdate_image (String update_image)
    {
        this.update_image = update_image;
    }


}


