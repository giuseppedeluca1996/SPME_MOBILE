package com.example.covid19.security;

import android.content.Context;

import com.example.covid19.util.Util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

 public  abstract class AuthManagerFactory {

     private static String authManagerType;
     protected static Context context;
     public abstract AuthManager  getAuthManager();

     public static  AuthManagerFactory getAuthManagerFactory(Context context){
         AuthManagerFactory.context=context;
         try {
            authManagerType=Util.getProperty("authManager.type", context);
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (authManagerType) {
            case "jwt" : {
                return new JwtAuthManagerFactory();
            }
            default : {
                return null;
            }
        }

     }


     public static Context getContext() {
         return AuthManagerFactory.context;
     }

     public static  void setContext(Context context) {
         AuthManagerFactory.context = context;
     }
 }
