package com.example.covid19;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;

import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.covid19.controller.SignInController;
import com.example.covid19.controller.SignUpController;
import com.example.covid19.model.Gender;
import com.example.covid19.model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity  {



    private EditText password;
    private EditText confirmPassword;
    private EditText surname;
    private EditText name;
    private EditText email;
    private EditText username;
    private EditText dateOfBirth;
    private Spinner gender;
    private ImageButton calendar;
    private Button signUpButton;
    private Switch preferencesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name=findViewById(R.id.nameTextField);
        surname=findViewById(R.id.surnameTextField);
        email=findViewById(R.id.emailTextField2);
        username=findViewById(R.id.usernameTextField);
        password=findViewById(R.id.passwordTextField2);
        confirmPassword=findViewById(R.id.confirmPasswordTextField);
        dateOfBirth=findViewById(R.id.dateOfBirthTextField);
        gender=findViewById(R.id.genderComboBox);
        calendar=findViewById(R.id.calendarButton);
        signUpButton=findViewById(R.id.signUpButton);
        preferencesView=findViewById(R.id.preferencesViewSwitch);

        dateOfBirth.setClickable(false);
        dateOfBirth.setFocusable(false);


        List<Gender> genderList=new ArrayList<>();
        genderList.add(Gender.FEMALE);
        genderList.add(Gender.MALE);
        ArrayAdapter<Gender> datAdapter=new ArrayAdapter<Gender>(this,android.R.layout.simple_spinner_item, genderList);
        datAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(datAdapter);


        signUpButton.setClickable(false);
        signUpButton.setEnabled(false);
        signUpButton.setAlpha(0.3F);

        email.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(s);
                if(!matcher.matches()){
                    email.setError("Email non valid format!");
                }
                signUpButton.setClickable(true);
                signUpButton.setAlpha(1F);
                signUpButton.setEnabled(true);

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length() < 8){
                    password.setError("Password  not valid!");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
        });
        confirmPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              if(!s.toString().equals(password.getText().toString())){
                 confirmPassword.setError("Password don't match!");
              }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
        });
        username.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty()){
                    username.setError("username is empty!");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
        });
        name.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty()){
                    name.setError("name is empty!");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
        });
        surname.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty()){
                    surname.setError("surname is empty!");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
        });


        findViewById(R.id.layoutParentSignUpActivity).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if(getCurrentFocus()!= null && getCurrentFocus().getWindowToken() != null){
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                return true;
            }
        });



        SignUpController.setContext(this);
    }


    public void showCalendar(View view){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONDAY);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dataPickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                updateDateTextField(year,month,dayOfMonth);
            }
        }, year, month, day);
        dataPickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        dataPickerDialog.show();

    }

    void updateDateTextField( int year, int month, int dayOfMont){
        String date = dayOfMont+"/"+month+"/"+year;
        dateOfBirth.setText(date);
        dateOfBirth.setError(null);
    }

    public void signUp(View view) {

        if(name.getText().toString().isEmpty() ){
            name.setError("Name is empty!");
        }
        if(surname.getText().toString().isEmpty() ){
            surname.setError("Surname is empty!");

        }
        if(password.getText().toString().isEmpty() ){
            password.setError("Password is empty!");
        }
        if(confirmPassword.getText().toString().isEmpty() ){
            confirmPassword.setError("Confirm password is empty!");

        }
        if(email.getText().toString().isEmpty() ){
            email.setError("Email is empty!");
        }
        if(username.getText().toString().isEmpty() ){
            username.setError("Username is empty!");

        }
        if(dateOfBirth.getText().toString().isEmpty() ){
            dateOfBirth.setError("Date is empty!");

        }
        if(!password.getText().toString().equals(confirmPassword.getText().toString())){
            confirmPassword.setError("Password don't match!");
        }
        if (email.getError()==null && username.getError()==null && name.getError()==null && surname.getError()==null && password.getError()==null && confirmPassword.getError()==null && dateOfBirth.getError()==null){
           Boolean emailIsValid=SignUpController.checkEmail(email.getText().toString()), usernameIsValid=SignUpController.checkUsername(username.getText().toString());
           if( emailIsValid && usernameIsValid ){
               User user = new  User();
               user.setEmail(email.getText().toString());
               user.setUsername(username.getText().toString());
               user.setName(name.getText().toString());
               user.setSurname(surname.getText().toString());
               user.setDateOfBirth( new Date(dateOfBirth.getText().toString()));
               user.setGender((Gender) gender.getSelectedItem());
               user.setPreferencesView(preferencesView.isChecked());
               user.setPassword(password.getText().toString());
               user.setEnabled(true);
               if(!SignUpController.saveUser(user)){
                   Toast toast = Toast.makeText(this, "Registration failed, please try again!", Toast.LENGTH_LONG);
                   toast.show();
               }
           }else {
                if(!emailIsValid){
                    email.setError("Email already used!");
                }
               if(!usernameIsValid){
                   username.setError("Username already used!");
               }
           }
        }
    }


    public static void showSignUpScreen(Context context) {
        context.startActivity(new Intent(context, SignUpActivity.class));
    }

}