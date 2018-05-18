package com.example.lenovo.trackapp.db;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;


public class HttpPostRequest extends AsyncTask<String, Void, String> {
private List<NameValuePair> nameValuePair;
private HttpCallBack httpCallBack;
public  HttpPostRequest(List<NameValuePair> nameValuePair, HttpCallBack httpCallBack){
    this.nameValuePair=nameValuePair;
    this.httpCallBack=httpCallBack;
}
@Override
protected void onPreExecute() {
    super.onPreExecute();
    httpCallBack.onPreExecute();
}
@Override
protected String doInBackground(String... params) {
    HttpClient httpClient = new DefaultHttpClient();
    Log.w("Leena","Url post==="+params[0]);
        HttpPost httpPost = new HttpPost(params[0]);
    try {
           httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
          } catch (UnsupportedEncodingException e) {
           e.printStackTrace();
            }
       try {
           HttpResponse response = httpClient.execute(httpPost);
           String responseString = new BasicResponseHandler().handleResponse(response);
            Log.w("Leena","Http Post Response:"+ responseString);
             return responseString;
       } catch (ClientProtocolException e) {
           e.printStackTrace();
           } catch (IOException e) {
           e.printStackTrace();
       }
    return null;
}
@Override
protected void onPostExecute(String s) {
    super.onPostExecute(s);
    httpCallBack.onPostSuccess(s);
}
}
