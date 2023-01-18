package com.example.empdata.presenter;


import com.example.empdata.model.EmployeeDatabase;
import com.example.empdata.view.LoginFragment;


public class LoginPresenter {
    private final LoginFragment mLoginFragment;
    private final EmployeeDatabase mDatabase;

    public LoginPresenter(LoginFragment loginFragment, EmployeeDatabase database) {
        mLoginFragment = loginFragment;
        mDatabase = database;
    }

    public void onLoginButtonClicked() {
        String email = mLoginFragment.mEmployeeEmail.getText().toString();
        String password = mLoginFragment.mEmployeePassword.getText().toString();
        boolean isLoggedIn = mDatabase.employeeDao().isUserValid(email, password);

        if (isLoggedIn) {
            mLoginFragment.showHomePage();

        }else {
            mLoginFragment.showErrorMessage();
        }
    }

    public void onSignUpClicked(){
        mLoginFragment.showAddEmployee();
    }
}

