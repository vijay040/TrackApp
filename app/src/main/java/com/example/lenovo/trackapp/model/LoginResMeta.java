package com.example.lenovo.trackapp.model;

public class LoginResMeta {
    private LoginModel response;

    public LoginModel getResponse ()
    {
        return response;
    }

    public void setResponse (LoginModel response)
    {
        this.response = response;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [response = "+response+"]";
    }
}

