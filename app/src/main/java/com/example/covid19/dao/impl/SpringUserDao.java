package com.example.covid19.dao.impl;

import android.content.Context;
import android.os.AsyncTask;

import com.example.covid19.dao.UserDao;
import com.example.covid19.model.HttpClient;
import com.example.covid19.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

import okhttp3.Response;


public class SpringUserDao extends UserDao {

    private  HttpClient httpClient;
    private static SpringUserDao instance;
    private Gson gson;

    private SpringUserDao(Context context){
       gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
       httpClient=new HttpClient(context);
    }

    public static SpringUserDao getInstance(Context context){
        if(instance == null) {
            synchronized (SpringUserDao.class) {
                instance = new SpringUserDao(context);
            }
        }
        return instance;
    }

    @Override
    public boolean save(User entity) {

        AsyncTask<User, Void,Boolean> asyncTask=new AsyncTask<User, Void, Boolean>(){
            @Override
            protected Boolean doInBackground(User... users) {
                try{
                    String user = gson.toJson(users[0]);
                    Response response=httpClient.requestPost("user/public/insertUser",user, false,null);
                    if(response.isSuccessful()){
                        return true;
                    }
                }catch (IOException ioException){
                    ioException.printStackTrace();
                }
                return null;
            }
        };
        try {
            return  asyncTask.execute(entity).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User update(User newEntity, Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {
    }

    @Override
    public User getById(Integer id) {
        return null;
    }


    @Override
    public Map<String,Object> getAll(Integer page, Integer size) {
        return null;
    }

    @Override
    public Boolean checkDisponibilityEmail(String email){

        AsyncTask<String, Void,Boolean> asyncTask=new AsyncTask<String,Void,Boolean>(){
            @Override
            protected Boolean doInBackground(String... strings) {
                try{
                    Response response=httpClient.requestGet("user/public/checkDisponibilityEmail?email=" + strings[0],false,null);
                    if( response.isSuccessful()){
                        return Boolean.valueOf(response.body().string());
                    }
                }catch (IOException ioException){
                    ioException.printStackTrace();
                }
                return false;
            }
        };

        try {
            return asyncTask.execute(email).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean checkDisponibilityUsername(String username){
        AsyncTask<String, Void,Boolean> asyncTask=new AsyncTask<String,Void,Boolean>(){
            @Override
            protected Boolean doInBackground(String... strings) {
                try{
                    Response response=httpClient.requestGet("user/public/checkDisponibilityUsername?username=" + strings[0],false,null);
                    if( response.isSuccessful()){
                        return Boolean.valueOf(response.body().string());
                    }
                }catch (IOException ioException){
                    ioException.printStackTrace();
                }
                return false;
            }
        };

        try {
            return asyncTask.execute(username).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

}
