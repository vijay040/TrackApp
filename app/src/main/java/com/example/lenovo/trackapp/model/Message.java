package com.example.lenovo.trackapp.model;

/**
 * Created by Lenovo on 25-04-2018.
 */

public class Message {
    private String msg;
    private  String name;
  private   int image;
    public Message(String name,String msg,int image){
        this.name=name;
        this.msg=msg;
        this.image=image;
    }
    public String getMsg(){
        return msg;
    }
    public String getName(){
        return name;
    }
    public int getImage() {
        return image;
    }
}
