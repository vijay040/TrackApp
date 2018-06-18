package com.example.lenovo.trackapp.model;

public class RequestTypeModel {
    private String id;

    private String request_type;

    private boolean isSelected=false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequest_type() {
        return request_type;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setRequest_type(String request_type) {
        this.request_type = request_type;
    }

    @Override
    public String toString() {
        return id;
    }
}
