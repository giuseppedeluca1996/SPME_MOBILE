package com.example.covid19.controller;

import android.content.Context;
import android.content.Intent;

import com.example.covid19.SignUpActivity;
import com.example.covid19.dao.DaoFactory;
import com.example.covid19.dao.UserDao;
import com.example.covid19.model.User;

import java.util.Objects;

public class SignUpController {

    private static Context context;
    private static UserDao userDao;

    public static void setContext(Context context) {
        SignUpController.context = context;
        userDao=Objects.requireNonNull(DaoFactory.getDaoFactory(context)).getUserDao();
    }

    public static Boolean checkEmail(String email) {
        return  userDao.checkDisponibilityEmail(email);
    }

    public static Boolean checkUsername(String username) {
        return  userDao.checkDisponibilityUsername(username);
    }

    public static Boolean saveUser(User user){
        userDao.save(user);
        if( SignInController.signIn(user.getUsername(),user.getPassword(),false)){
            HomePageController.showHomePage(context);
            return true;

        }else {
            return false;
        }
    }

    public static void signUp(){
        SignUpActivity.showSignUpScreen(context);
    }


}
