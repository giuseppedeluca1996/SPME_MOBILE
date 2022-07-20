package com.example.covid19.security.impl;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.covid19.model.AuthRequest;
import com.example.covid19.model.HttpClient;
import com.example.covid19.model.UnauthorizedException;
import com.example.covid19.security.AuthManager;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.Response;


public class JwtAuthManager implements AuthManager {

    private Context context;
    private HttpClient httpClient;


    private static JwtAuthManager instance;

    private static String jwtToken=null;

    private final  static Gson gsonConverter=new Gson();


    private JwtAuthManager(Context context){
        this.context=context;
        httpClient= new HttpClient(context);
    }

    public static JwtAuthManager getInstance(Context context){
        if(instance == null) {
            synchronized (JwtAuthManager.class) {
                instance = new JwtAuthManager(context);
            }
        }
        return instance;
    }


    @Override
    public boolean loginWithUsername(String username, String password)   throws UnauthorizedException {

        final String json = gsonConverter.toJson(new AuthRequest( username, null ,password));

        AsyncTask<String, Void,Boolean> asyncTask=new AsyncTask<String,Void,Boolean>(){
            @Override
            protected Boolean doInBackground(String... strings) {
                try{
                    Response response= httpClient.requestPost("signin/public/authenticate", strings[0],false, null);
                    if( response.isSuccessful()){
                        jwtToken=response.body().string();
                        return true;
                    } else {
                        return false;
                    }
                }catch (IOException ioException){
                    ioException.printStackTrace();
                    return false;
                }
            }
        };

        try {
            return asyncTask.execute(json).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean loginWithEmail(String email, String password) throws UnauthorizedException {

        final String json = gsonConverter.toJson(new AuthRequest( null, email ,password));

        AsyncTask<String, Void,Boolean> asyncTask=new AsyncTask<String,Void,Boolean>(){
            @Override
            protected Boolean doInBackground(String... strings) {
                try{
                    Response response= httpClient.requestPost("signin/public/authenticate", strings[0],false, null);
                    if( response.isSuccessful()){
                        jwtToken=response.body().string();
                        return true;
                    } else {
                        return false;
                    }
                }catch (IOException ioException){
                    ioException.printStackTrace();
                    return false;
                }
            }
        };

        try {
            return asyncTask.execute(json).get();
        } catch (ExecutionException | InterruptedException e) {e.printStackTrace();
            return false;
        }
    }

    @Override
    public  String getAuthenticationString() {
        return jwtToken;
    }

    @Override
    public void logOut() {
        jwtToken=null;
    }
}
