package com.example.empdata.presenter;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.empdata.model.EmployeeDatabase;
import com.example.empdata.view.HomeFragment;
import com.example.empdata.view.LoginFragment;
import com.example.empdata.view.ProfileFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class LoginPresenter {
    private final LoginFragment mLoginFragment;
    private final EmployeeDatabase mDatabase;

    public LoginPresenter(LoginFragment loginFragment, EmployeeDatabase database) {
        mLoginFragment = loginFragment;
        mDatabase = database;
    }


    public void onLoginButtonClicked() {
        String email = mLoginFragment.mEmployeeUserName.getText().toString();
        String password = mLoginFragment.mEmployeePassword.getText().toString();
        boolean isLoggedIn = mDatabase.employeeDao().isUserValid(email, password);
        SharedPreferences.Editor editor = mLoginFragment.requireActivity().getPreferences(Context.MODE_PRIVATE).edit();
        if (isLoggedIn) {
            editor.putBoolean("isLoggedIn", true);
            editor.apply();
            mLoginFragment.showHomePage();


        }else {
            mLoginFragment.showErrorMessage();
        }
    }

    public void onSignUpClicked(){
        mLoginFragment.showAddEmployee();
    }


        

}

