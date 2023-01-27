package com.example.empdata.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.empdata.R;
import com.example.empdata.model.EmployeeDatabase;
import com.example.empdata.presenter.LoginPresenter;

public class LoginFragment extends Fragment {
    public EditText mEmployeeEmail;
    public EditText mEmployeePassword;
    public Button mLoginButton;
    public TextView mSignView;
    LoginPresenter mPresenter;
    private static final String LOGIN_PREFS = "session_preferences";
    private static final String IS_LOGGED_IN = "is_logged_in";
    String userName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        EmployeeDatabase database = EmployeeDatabase.getInstance(getContext());
        mPresenter = new LoginPresenter(this, database);
        mEmployeeEmail = view.findViewById(R.id.employeeEmail);
        mEmployeePassword = view.findViewById(R.id.employeePassword);
        mLoginButton = view.findViewById(R.id.loginBtn);
        mSignView = view.findViewById(R.id.needAccount);
        mLoginButton.setOnClickListener(v ->{
            String email = mEmployeeEmail.getText().toString();
            userName = database.employeeDao().getUserName(email);
            mPresenter.onLoginButtonClicked();
        });
        mSignView.setOnClickListener(v -> mPresenter.onSignUpClicked());
        return view;
    }


    public void showHomePage(){
        SharedPreferences loginPrefs = requireActivity().getSharedPreferences(LOGIN_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginPrefs.edit();
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.apply();
        Bundle bundle = new Bundle();
        bundle.putString("key",userName);
        HomeFragment mFragment = new HomeFragment();
        mFragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, mFragment).addToBackStack("name").commit();
    }

    public void showErrorMessage(){
        Toast.makeText(getContext(), "Wrong Credentials",Toast.LENGTH_LONG).show();
    }

    public void showAddEmployee(){
        AddEmployeeFragment mFragment = new AddEmployeeFragment();
        mFragment.setArguments(new Bundle());
        getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, mFragment).addToBackStack("name").commit();
    }
}