package com.example.covid19.security;


public interface AuthManager {

   boolean loginWithUsername(String username, String password);

   boolean loginWithEmail(String username, String password);

   String getAuthenticationString();

   void logOut();

}
