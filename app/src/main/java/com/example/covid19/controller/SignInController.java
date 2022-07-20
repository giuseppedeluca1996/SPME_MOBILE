package com.example.covid19.controller;

import android.content.Context;
import android.widget.Toast;

import com.example.covid19.HomePageActivity;
import com.example.covid19.SignUpActivity;
import com.example.covid19.model.UnauthorizedException;
import com.example.covid19.security.AuthManager;
import com.example.covid19.security.AuthManagerFactory;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInController {

    private static  Context context;
    private static AuthManager authManager;

    public static Boolean signIn(String usernameOrEmail, String password, boolean asAGuest){
        if(asAGuest){
            HomePageActivity.showHomePageScreen(context);
            authManager.logOut();
            return true;
        }else {

            String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(usernameOrEmail);
            if(matcher.matches()){
                try{
                    if(authManager.loginWithEmail(usernameOrEmail,password)){
                        HomePageActivity.showHomePageScreen(context);
                        return  true;
                    }
                }catch (UnauthorizedException ue){
                    ue.printStackTrace();
                }
            }else {
                try{
                    if(authManager.loginWithUsername(usernameOrEmail,password)){
                        HomePageActivity.showHomePageScreen(context);
                        return true;
                    }
                }catch (UnauthorizedException ue){
                    ue.printStackTrace();
                }
            }
        }
        return false;
    }


    public static void setContext(Context context) {
        SignInController.context = context;
        authManager = Objects.requireNonNull( AuthManagerFactory.getAuthManagerFactory(context)).getAuthManager();
    }
}
