package com.example.covid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.covid19.controller.HomePageController;
import com.example.covid19.controller.SearchController;
import com.example.covid19.controller.StructureViewController;
import com.example.covid19.controller.WriteReviewController;
import com.example.covid19.model.Review;
import com.example.covid19.security.AuthManagerFactory;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Objects;

public class HomePageActivity extends AppCompatActivity {


    private DrawerLayout drawer;
    private NavigationView navigationView;
    private NavController navController;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        HomePageController.setContext(this);
        SearchController.setContext(this);
        WriteReviewController.setContext(this);
        StructureViewController.setContext(this);
        context=this;

        navController= Navigation.findNavController(this,R.id.fragment);
        drawer=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.logout :{
                        Objects.requireNonNull(AuthManagerFactory.getAuthManagerFactory(context)).getAuthManager().logOut();
                        navController.navigate(R.id.homePage);
                        drawer.close();
                    }break;
                    case R.id.login :{
                        context.startActivity(new Intent(context, SignInActivity.class));
                    }break;
                    case R.id.homePage :{
                       navController.navigate(R.id.homePage);
                       drawer.close();
                    }break;
                }
                return false;
            }
        });

    }
    public static void showHomePageScreen(Context context) {
        context.startActivity(new Intent(context,HomePageActivity.class));
    }
}