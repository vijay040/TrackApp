package com.example.lenovo.trackapp.db;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Fablian Android on 6/30/2017.
 */

public class HttpPostRequestService extends AsyncTask<String, Void, String> {
    private JSONObject jsonObject;
    private HttpCallBack httpCallBack;
    private String requestType ;

    public HttpPostRequestService(JSONObject jsonObject, String requestType, HttpCallBack httpCallBack) {
        this.jsonObject = jsonObject;
        this.httpCallBack = httpCallBack;
        this.requestType=requestType;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        httpCallBack.onPreExecute();
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        httpCallBack.onPostSuccess(s);
    }
    @Override
    protected String doInBackground(String... params) {
        return sendPostRequest(params[0],jsonObject);
    }

    public String sendPostRequest(String requestURL,
                                  JSONObject postDataParams) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);
           // Log.w("Leena", "url====" + requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod(requestType);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
           // Log.w("Leena", "getPostDataString(postDataParams)====" + writer);
            writer.write(getPostDataString(postDataParams));
           // Log.w("Leena", "getPostDataString(postDataParams)====" + writer);
            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                response = br.readLine();

            } else {
                response = "Error Registering";
            }
        } catch (Exception e) {
            if (e instanceof java.net.SocketTimeoutException)
                Log.w("Leena", "exception====" + e);
            e.printStackTrace();
        }
        return response;
    }

    public String getPostDataString(JSONObject params) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> itr = params.keys();
        while (itr.hasNext()) {

            String key = itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    }
}

