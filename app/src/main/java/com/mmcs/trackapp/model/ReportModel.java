package com.mmcs.trackapp.model;

import java.util.ArrayList;

public class ReportModel {

    private String total_amount;

    private String id;

    private String balance_rct;

    private String created_on;

    private String status;

    private ArrayList<Expense> expense;

    private String meeting_id;

    private String advance;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBalance_rct() {
        return balance_rct;
    }

    public void setBalance_rct(String balance_rct) {
        this.balance_rct = balance_rct;
    }

    public ArrayList<Expense> getExpense() {
        return expense;
    }

    public void setExpense(ArrayList<Expense> expense) {
        this.expense = expense;
    }

    public String getMeeting_id() {
        return meeting_id;
    }

    public void setMeeting_id(String meeting_id) {
        this.meeting_id = meeting_id;
    }

    public String getAdvance() {
        return advance;
    }

    public void setAdvance(String advance) {
        this.advance = advance;
    }


    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
