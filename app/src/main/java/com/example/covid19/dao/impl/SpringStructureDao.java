package com.example.covid19.dao.impl;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.covid19.dao.StructureDao;
import com.example.covid19.model.Filter;
import com.example.covid19.model.HttpClient;
import com.example.covid19.model.Order;
import com.example.covid19.model.Structure;
import com.example.covid19.model.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import okhttp3.Response;

public class SpringStructureDao extends StructureDao {

    private HttpClient httpClient;
    private static SpringStructureDao instance;
    private Gson gson;

    private SpringStructureDao(Context context){
        gson= new GsonBuilder().setDateFormat("hh:mm:ss").excludeFieldsWithoutExposeAnnotation().create();
        httpClient=new HttpClient(context);
    }

    public static StructureDao getInstance(Context context){
        if(instance == null) {
            synchronized (SpringStructureDao.class) {
                instance = new SpringStructureDao(context);
            }
        }
        return instance;
    }

    @Override
    public boolean save(Structure entity) {
        return false;
    }

    @Override
    public Structure update(Structure newEntity, Integer id) {

        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Structure getById(Integer id) {
        return null;
    }

    @Override
    public Map<String, Object> getAll(Integer page, Integer size) {

        return null;
    }

    @Override
    public Map<String, Object> getAllHotel(Integer page, Integer size) {

        return null;
    }

    @Override
    public Map<String, Object> getAllRestaurant(Integer page, Integer size) {

        return null;
    }

    @Override
    public Map<String, Object> getAllAttraction(Integer page, Integer size) {

        return null;
    }

    @Override
    public Map<String, Object> getAllHotelByText(Integer page, Integer size, String text){

        return null;
    }

    @Override
    public Map<String, Object> getAllRestaurantByText(Integer page, Integer size, String text){

        return null;
    }

    @Override
    public Map<String, Object> getAllAttractionByText(Integer page, Integer size, String text){

        return null;
    }


    @Override
    public List<Structure> getStructureAtDistance(final BigDecimal latitude, BigDecimal longitude, BigDecimal distance) {
        AsyncTask<BigDecimal, Void,List<Structure>> asyncTask=new AsyncTask<BigDecimal,Void,List<Structure>>(){
            @Override
            protected List<Structure> doInBackground(BigDecimal... bigDecimals) {
                try{
                    Response response=httpClient.requestGet("structure/public/getStructureAtDistance?latitude=" +
                            bigDecimals[0] + "&longitude=" +
                            bigDecimals[1] + "&distance=" + bigDecimals[2],false,null);
                    if( response.isSuccessful()){
                        return gson.fromJson(response.body().string(),new TypeToken<List<Structure>>() {}.getType());
                    }
                }catch (IOException ioException){
                    ioException.printStackTrace();
                }
                return null;
            }
        };

        try {
            return asyncTask.execute(latitude,longitude,distance).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public List<Structure> getStructureAroundYou(final BigDecimal latitude, final BigDecimal longitude, Filter filter, Order order) {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("latitude",latitude);
            jsonObject.put("longitude",longitude);
            jsonObject.put("priceMin",filter.getPriceMin().doubleValue());
            jsonObject.put("priceMax",filter.getPriceMax().doubleValue());
            jsonObject.put("rating",filter.getRating().doubleValue());
            JSONArray jsonArray=new JSONArray();
            for(Type t: filter.getTypes())
                jsonArray.put(t);
            jsonObject.put("types",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AsyncTask<JSONObject, Void,List<Structure>> asyncTask=new AsyncTask<JSONObject, Void, List<Structure>>(){
            @Override
            protected List<Structure> doInBackground(JSONObject... objects) {
                String s= jsonObject.toString();
                try{
                    Response response=httpClient.requestPost("structure/public/getStructureAroundYou", jsonObject.toString(), false,null);
                    if( response.isSuccessful()){
                        return gson.fromJson(response.body().string(),new TypeToken<List<Structure>>() {}.getType());
                    }
                }catch (IOException ioException){
                    Log.d("Test",ioException.getMessage());
                }
                return null;
            }


        };

        try {
            return asyncTask.execute(jsonObject).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Structure> getStructureByText(String query,  Filter filter, Order order) {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("priceMin",filter.getPriceMin().doubleValue());
            jsonObject.put("priceMax",filter.getPriceMax().doubleValue());
            jsonObject.put("rating",filter.getRating().doubleValue());
            jsonObject.put("searchValue",query);
            JSONArray jsonArray=new JSONArray();
            for(Type t: filter.getTypes())
                jsonArray.put(t);
            jsonObject.put("types",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AsyncTask<JSONObject, Void,List<Structure>> asyncTask=new AsyncTask<JSONObject, Void, List<Structure>>(){
            @Override
            protected List<Structure> doInBackground(JSONObject... objects) {
                String s= jsonObject.toString();
                try{
                    Response response=httpClient.requestPost("structure/public/getStructureByText", jsonObject.toString(), false,null);
                    if( response.isSuccessful()){
                        return gson.fromJson(response.body().string(),new TypeToken<List<Structure>>() {}.getType());
                    }
                }catch (IOException ioException){
                    ioException.printStackTrace();
                }
                return null;
            }


        };

        try {
            return asyncTask.execute(jsonObject).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<String> getTips(String query) {
        AsyncTask<String, Void,List<String>> asyncTask=new AsyncTask<String, Void, List<String>>(){
            @Override
            protected List<String> doInBackground(String... strings) {
                try{
                    Response response=httpClient.requestGet("structure/public/getTips?text=" +
                            strings[0],false,null);
                    if( response.isSuccessful()){
                        return gson.fromJson(response.body().string(),new TypeToken<Collection<String>>() {}.getType());
                    }
                }catch (IOException ioException){
                    ioException.printStackTrace();
                }
                return null;
            }
        };

        try {
            return asyncTask.execute(query).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


}
