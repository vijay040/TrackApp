package com.example.lenovo.trackapp.model;

public class DepartmentModel {

    private String id;

    private String status;

    private String department_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", status = " + status + ", department_name = " + department_name + "]";
    }

}
