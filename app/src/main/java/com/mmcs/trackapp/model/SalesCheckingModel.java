package com.mmcs.trackapp.model;

import java.io.Serializable;

/**
 * Created by Lenovo on 16-08-2018.
 */

public class SalesCheckingModel implements Serializable {
    private String port;

    private String id;

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
