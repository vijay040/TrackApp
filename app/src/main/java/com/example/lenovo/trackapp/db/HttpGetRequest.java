package com.example.lenovo.trackapp.db;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpGetRequest extends AsyncTask<String, Void, String> {
private HttpCallBack httpCallBack;
public  HttpGetRequest(HttpCallBack httpCallBack){
this.httpCallBack=httpCallBack;
}
@Override
protected void onPreExecute() {
super.onPreExecute();
httpCallBack.onPreExecute();
 }
@Override
protected String doInBackground(String... params) {
HttpClient client = new DefaultHttpClient();
HttpGet request = new HttpGet(params[0]);
HttpResponse response;
try {
    response = client.execute(request);
    String responseStr = EntityUtils.toString(response.getEntity());
    Log.w("Leena","Http Get Response:"+ responseStr);
    return responseStr;
   } catch (ClientProtocolException e) {
    // TODO Auto-generated catch block
     e.printStackTrace();
   } catch (IOException e) {
    // TODO Auto-generated catch block
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
