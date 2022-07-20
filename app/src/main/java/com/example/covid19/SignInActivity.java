package com.example.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.covid19.controller.SignInController;
import com.example.covid19.controller.SignUpController;



public class SignInActivity extends AppCompatActivity {

    EditText emailTextField;
    EditText passwordTextField;
    Switch singInAsGuestSwitch;
    Button signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_in);
        emailTextField=findViewById(R.id.emailTextField);
        passwordTextField=findViewById(R.id.passwordTextField);
        singInAsGuestSwitch=findViewById(R.id.singInAsGuestSwitch);
        signInButton=findViewById(R.id.signInButton);

        singInAsGuestSwitch.setChecked(false);
        signInButton.setClickable(false);
        signInButton.setEnabled(false);
        signInButton.setAlpha(0.3F);

        singInAsGuestSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    emailTextField.setEnabled(false);
                    emailTextField.setCursorVisible(false);
                    emailTextField.setTextColor(Color.GRAY);
                    passwordTextField.setEnabled(false);
                    passwordTextField.setCursorVisible(false);
                    passwordTextField.setTextColor(Color.GRAY);
                    signInButton.setClickable(true);
                    signInButton.setEnabled(true);
                    signInButton.setAlpha(1F);
                } else {
                    emailTextField.setEnabled(true);
                    emailTextField.setCursorVisible(true);
                    emailTextField.setClickable(true);
                    emailTextField.setTextColor(Color.BLACK);
                    passwordTextField.setEnabled(true);
                    passwordTextField.setCursorVisible(true);
                    passwordTextField.setClickable(true);
                    passwordTextField.setTextColor(Color.BLACK);
                    if(emailTextField.getText().toString().isEmpty()){
                        signInButton.setClickable(false);
                        signInButton.setEnabled(false);
                        signInButton.setAlpha(0.3F);

                    }
                }
            }
        });

        emailTextField.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().trim().length()==0){
                    signInButton.setEnabled(false);
                    signInButton.setClickable(false);
                    signInButton.setAlpha(0.3F);
                } else {
                    signInButton.setEnabled(true);
                    signInButton.setClickable(true);
                    signInButton.setAlpha(1F);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
        });

        passwordTextField.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(emailTextField.getText().toString().isEmpty()){
                    emailTextField.setError("Email  or username not is empty!");
                }else {
                    emailTextField.setError(null);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
        });


        findViewById(R.id.layoutParentSignInActivity).setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if(getCurrentFocus()!= null && getCurrentFocus().getWindowToken() != null){
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                return true;
            }
        });

        SignInController.setContext(this);
        SignUpController.setContext(this);
    }

    public void signUp(View view) {
        SignUpController.signUp();
    }

    public void signIn(View view) {
        if(signInButton.isEnabled()){
            if(singInAsGuestSwitch.isChecked()){
                SignInController.signIn(null,null,true);
            }else {
                if(!SignInController.signIn(emailTextField.getText().toString(),passwordTextField.getText().toString(),false)){
                    passwordTextField.getText().clear();
                    emailTextField.getText().clear();
                    Toast toast = Toast.makeText(this, "Credential are not valid", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
    }





}