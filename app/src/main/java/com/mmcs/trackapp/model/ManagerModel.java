package com.mmcs.trackapp.model;

/**
 * Created by Lenovo on 08-10-2018.
 */

public class ManagerModel {
    private String pre_request_approval;
    private String expense_request_approval;

    public String getPre_request_approval() {
        return pre_request_approval;
    }

    public void setPre_request_approval(String pre_request_approval) {
        this.pre_request_approval = pre_request_approval;
    }

    public String getExpense_request_approval() {
        return expense_request_approval;
    }

    public void setExpense_request_approval(String expense_request_approval) {
        this.expense_request_approval = expense_request_approval;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
}
