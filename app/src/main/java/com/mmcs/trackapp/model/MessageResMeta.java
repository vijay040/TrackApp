package com.mmcs.trackapp.model;

import java.util.ArrayList;

/**
 * Created by Lenovo on 29-06-2018.
 */

public class MessageResMeta {
  private ArrayList<MessageModel> response;

    public ArrayList<MessageModel> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<MessageModel> response) {
        this.response = response;
    }
}
