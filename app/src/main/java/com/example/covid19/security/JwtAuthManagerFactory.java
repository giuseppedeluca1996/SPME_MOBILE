package com.example.covid19.security;

import com.example.covid19.security.impl.JwtAuthManager;

public class JwtAuthManagerFactory extends AuthManagerFactory{

    @Override
    public AuthManager getAuthManager() {
        return JwtAuthManager.getInstance(AuthManagerFactory.context);
    }

}
