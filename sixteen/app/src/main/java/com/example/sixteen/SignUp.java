package com.example.sixteen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    TextInputLayout regName, regUsername, regEmail, regPassword;
    Button regBtn, regToLoginBtn;

    FirebaseDatabase rootNode;
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        regName = findViewById(R.id.reg_name);
        regUsername = findViewById(R.id.reg_username);
        regEmail = findViewById(R.id.reg_email);
        regPassword = findViewById(R.id.reg_password);
        regBtn = findViewById(R.id.reg_btn);
        regToLoginBtn = findViewById(R.id.reg_login_btn);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");


                String names = regName.getEditText().getText().toString();
                String usernames = regUsername.getEditText().getText().toString();
                String emails = regEmail.getEditText().getText().toString();
                String passwords = regPassword.getEditText().getText().toString();

                UserHelperClass helperClass = new UserHelperClass(names,usernames,emails,passwords);

                reference.child(usernames).setValue(helperClass);

            }
        });

    }

//    private Boolean validateName(){
//        String val = regName.getEditText().getText().toString();
//
//        if (val.isEmpty()){
//            regName.setError("Field cannot be empty");
//            return false;
//        }
//        else {
//            regName.setError(null);
//            regName.setErrorEnabled(false);
//            return true;
//
//        }
//    }
//
//    private Boolean validateUsername(){
//        String val = regUsername.getEditText().getText().toString();
//        String noWhiteSpace = "(?=\\s+$)";
//
//        if (val.isEmpty()){
//            regName.setError("Field cannot be empty");
//            return false;
//        }else if (val.length()>=15) {
//            regName.setError("Username too long");
//            return false;
//
//        }
//        else if (!val.matches(noWhiteSpace)){
//            regName.setError("White Spaces are not allowed");
//            return false;
//        }
//        else {
//            regName.setError(null);
//            return true;
//        }
//    }
//
//
//    private Boolean validateEmail(){
//        String val = regEmail.getEditText().getText().toString();
//        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//
//        if (val.isEmpty()){
//            regName.setError("Field cannot be empty");
//            return false;
//        }else if (!val.matches(emailPattern)) {
//            regName.setError("Invalid email address");
//            return false;
//        }
//        else {
//            regName.setError(null);
//            return true;
//
//        }
//    }
//
//
//
//    private Boolean validatePassword() {
//        String val = regPassword.getEditText().getText().toString();
//        String passwordVal = "^" +
//                "(?=.*[a-zA-Z])" +
//                "(?=.*[@#$%^&+=])" +
//                "(?=\\s+$)" +
//                ".{4,}" +
//                "$";
//
//        if (val.isEmpty()) {
//            regName.setError("Field cannot be empty");
//            return false;
//        } else if (!val.matches(passwordVal)) {
//            regName.setError("Password is too weak");
//            return false;
//        } else {
//            regName.setError(null);
//            return true;
//
//        }
//    }
//
//
//
//
//
//
//
//    public void  registerUser(View view) {
//
//        if (!validateName() | !validateName() | !validatePassword() | !validateEmail() | !validateUsername()) {
//            return;
//    }
//
//        String names = regName.getEditText().toString();
//        String usernames = regUsername.getEditText().toString();
//        String emails = regEmail.getEditText().toString();
//        String passwords = regPassword.getEditText().toString();
//
//        UserHelperClass helperClass = new UserHelperClass(names,usernames,emails,passwords);
//
//        reference.child(usernames).setValue(helperClass);
//    }
}