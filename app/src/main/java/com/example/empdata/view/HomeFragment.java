package com.example.empdata.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.empdata.R;
import com.example.empdata.SplashActivity;
import com.example.empdata.presenter.HomePresenter;
import com.google.android.material.navigation.NavigationView;


public class HomeFragment extends Fragment {

    HomePresenter mPresenter;
    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    HomeFragment homeFragment;
    private static final String LOGIN_PREFS = "session_preferences";
    private static final String IS_LOGGED_IN = "is_logged_in";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        homeFragment = new HomeFragment();
        mPresenter = new HomePresenter(this);
        Button mAddEmployeeButton = view.findViewById(R.id.addEmployee);
        Button mShowEmployeeButton = view.findViewById(R.id.showEmployee);
        mAddEmployeeButton.setOnClickListener(v -> mPresenter.onAddEmployeeButtonClicked());
        mShowEmployeeButton.setOnClickListener(v -> mPresenter.onShowEmployeeButtonClicked());
        drawerLayout = view.findViewById(R.id.drawerLayout);
        navigationView = view.findViewById(R.id.navigationView);
        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.nav_name);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(requireActivity(), drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Bundle bundle = this.getArguments();
        String userName = bundle.getString("key");
        userNameTextView.setText(userName);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.nav_addEmployee:
                        mPresenter.onAddEmployeeButtonClicked();
                        return true;

                    case R.id.nav_showEmployee:
                        mPresenter.onShowEmployeeButtonClicked();
                        return true;

                    case R.id.nav_image_picker:
                        mPresenter.OnImagePickerButtonClicked();
                        return true;

                    case R.id.nav_my_devices:
                        mPresenter.OnMyDeviceButtonClicked();
                        return true;

                    case R.id.nav_logout:
                        mPresenter.onLoginButtonClicked();
                        return true;
                }
                return true;
            }
        });


        return view;
    }


    public void navigateToAddEmployeeFragment() {
        AddEmployeeFragment mFragment = new AddEmployeeFragment();
        mFragment.setArguments(new Bundle());
        getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, mFragment).addToBackStack("name").commit();
    }

    public void navigateToShowEmployeeFragment() {
        ShowEmployeeFragment mFragment = new ShowEmployeeFragment();
        mFragment.setArguments(new Bundle());
        getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, mFragment).addToBackStack("name").commit();
    }

    public void navigateImagePickerFragment() {
        ImagePickerFragment mFragment = new ImagePickerFragment();
        mFragment.setArguments(new Bundle());
        getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, mFragment).addToBackStack("name").commit();
    }


    public void navigateToBluetoothFragment() {
        BluetoothFragment mFragment = new BluetoothFragment();
        mFragment.setArguments(new Bundle());
        getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, mFragment).addToBackStack("name").commit();
    }

    public void navigateToLoginEmployeeFragment() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(LOGIN_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_LOGGED_IN, false);
        editor.apply();
        Intent intent = new Intent(requireActivity(), SplashActivity.class);
        startActivity(intent);


    }

}


