package com.example.covid19.model;


import android.content.Context;
import android.util.Log;

import com.example.covid19.util.Util;

import java.io.IOException;
import java.util.Objects;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpClient {

    private final OkHttpClient client = new OkHttpClient();
    private  HttpUrl httpUrl;
    private Context context;

    private  String serverUrl;

    public HttpClient(Context context){
        this.context=context;
        try {
            serverUrl = Util.getProperty("server.url",context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Response requestGet(String endPoint,boolean authorization, final String jwtToken) throws IOException {
        Request request;

        httpUrl = Objects.requireNonNull(HttpUrl.parse(serverUrl+endPoint)).newBuilder()
                .build();

        if(!authorization){
            request = new Request.Builder()
                    .url(httpUrl)
                    .build();
        }else {
            request = new Request.Builder()
                    .url(httpUrl)
                    .addHeader("Authorization", "Bearer "+jwtToken)
                    .build();
        }
        return client.newCall(request).execute();

    }

    public Response requestPost(String endPoint, String body,boolean authorization, final String jwtToken) throws IOException {

        Request request;


        httpUrl = Objects.requireNonNull(HttpUrl.parse(serverUrl+endPoint)).newBuilder()
                .build();

        if(authorization){
            request = new Request.Builder()
                    .url(httpUrl)
                    .post(RequestBody.create(body,MediaType.parse("application/json; charset=utf-8")))
                    .addHeader("Authorization", "Bearer "+jwtToken)
                    .build();
        }else {
             request = new Request.Builder()
                    .url(httpUrl)
                    .post(RequestBody.create(body,MediaType.parse("application/json; charset=utf-8")))
                    .build();
        }
        return client.newCall(request).execute();

    }

}
