package com.example.lenovo.trackapp.model;

public class LoginModel {

    private String position;

    private String status;

    private String is_active;

    private String country_id;

    private String password;

    private String department_no;

    private String location_id;

    private String id;

    private String user_name;

    private String display_name;

    private String joining_date;

    private String mobile_number;

    private String email;

    private String company_name;

    private String role;

    private String reporting_person;

    private String group;

    private String mobile;

    public String getPosition ()
    {
        return position;
    }

    public void setPosition (String position)
    {
        this.position = position;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getIs_active ()
    {
        return is_active;
    }

    public void setIs_active (String is_active)
    {
        this.is_active = is_active;
    }

    public String getCountry_id ()
    {
        return country_id;
    }

    public void setCountry_id (String country_id)
    {
        this.country_id = country_id;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    public String getDepartment_no ()
    {
        return department_no;
    }

    public void setDepartment_no (String department_no)
    {
        this.department_no = department_no;
    }

    public String getLocation_id ()
    {
        return location_id;
    }

    public void setLocation_id (String location_id)
    {
        this.location_id = location_id;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getUser_name ()
    {
        return user_name;
    }

    public void setUser_name (String user_name)
    {
        this.user_name = user_name;
    }

    public String getDisplay_name ()
    {
        return display_name;
    }

    public void setDisplay_name (String display_name)
    {
        this.display_name = display_name;
    }

    public String getJoining_date ()
    {
        return joining_date;
    }

    public void setJoining_date (String joining_date)
    {
        this.joining_date = joining_date;
    }

    public String getMobile_number ()
    {
        return mobile_number;
    }

    public void setMobile_number (String mobile_number)
    {
        this.mobile_number = mobile_number;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getCompany_name ()
    {
        return company_name;
    }

    public void setCompany_name (String company_name)
    {
        this.company_name = company_name;
    }

    public String getRole ()
    {
        return role;
    }

    public void setRole (String role)
    {
        this.role = role;
    }

    public String getReporting_person ()
    {
        return reporting_person;
    }

    public void setReporting_person (String reporting_person)
    {
        this.reporting_person = reporting_person;
    }

    public String getGroup ()
    {
        return group;
    }

    public void setGroup (String group)
    {
        this.group = group;
    }

    public String getMobile ()
    {
        return mobile;
    }

    public void setMobile (String mobile)
    {
        this.mobile = mobile;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [position = "+position+", status = "+status+", is_active = "+is_active+", country_id = "+country_id+", password = "+password+", department_no = "+department_no+", location_id = "+location_id+", id = "+id+", user_name = "+user_name+", display_name = "+display_name+", joining_date = "+joining_date+", mobile_number = "+mobile_number+", email = "+email+", company_name = "+company_name+", role = "+role+", reporting_person = "+reporting_person+", group = "+group+", mobile = "+mobile+"]";
    }
}
