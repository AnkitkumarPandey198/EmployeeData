package com.example.empdata.presenter;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.empdata.R;
import com.example.empdata.model.EmployeeDatabase;
import com.example.empdata.view.HomeFragment;
import com.example.empdata.view.LoginFragment;


public class LoginPresenter {
    private final LoginFragment mLoginFragment;
    private final EmployeeDatabase mDatabase;
    String userName;
    private String editTextValue;

    public LoginPresenter(LoginFragment loginFragment, EmployeeDatabase database) {
        mLoginFragment = loginFragment;
        mDatabase = database;
    }


    public void onLoginButtonClicked() {
        String email = mLoginFragment.mEmployeeEmail.getText().toString();
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

