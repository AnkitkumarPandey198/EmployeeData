package com.example.empdata.presenter;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.empdata.R;
import com.example.empdata.model.EmployeeDatabase;
import com.example.empdata.view.HomeFragment;
import com.example.empdata.view.LoginFragment;
import com.google.android.material.navigation.NavigationView;

public class HomePresenter {
     final HomeFragment mHomeFragment;
    LoginFragment loginFragment = new LoginFragment();
    EmployeeDatabase employeeDatabase = EmployeeDatabase.getInstance(loginFragment.getContext());
    LoginPresenter loginPresenter = new LoginPresenter(loginFragment,employeeDatabase);

    public HomePresenter(HomeFragment homeFragment) {
        mHomeFragment = homeFragment;
    }


    public void onAddEmployeeButtonClicked() {
        mHomeFragment.navigateToAddEmployeeFragment();

    }

    public void onShowEmployeeButtonClicked() {
        mHomeFragment.navigateToShowEmployeeFragment();
    }

    public void onLoginButtonClicked() {
        mHomeFragment.navigateToLoginEmployeeFragment();
    }






}
