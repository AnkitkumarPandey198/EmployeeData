package com.example.empdata.view;

import android.annotation.SuppressLint;
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

import com.example.empdata.R;
import com.example.empdata.presenter.HomePresenter;
import com.google.android.material.navigation.NavigationView;


public class HomeFragment extends Fragment {

    HomePresenter mPresenter;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mPresenter = new HomePresenter(this);
        Button mAddEmployeeButton = view.findViewById(R.id.addEmployee);
        Button mShowEmployeeButton = view.findViewById(R.id.showEmployee);
        mAddEmployeeButton.setOnClickListener(v -> mPresenter.onAddEmployeeButtonClicked());
        mShowEmployeeButton.setOnClickListener(v -> mPresenter.onShowEmployeeButtonClicked());
        drawerLayout = view.findViewById(R.id.drawerLayout);
        navigationView = view.findViewById(R.id.navigationView);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle =  new ActionBarDrawerToggle(requireActivity(),drawerLayout,toolbar,R.string.nav_open,R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.nav_addEmployee:
                        mPresenter.onAddEmployeeButtonClicked();
                        return true;

                    case R.id.nav_showEmployee:
                        mPresenter.onShowEmployeeButtonClicked();
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


    public void navigateToAddEmployeeFragment(){
        AddEmployeeFragment mFragment = new AddEmployeeFragment();
        mFragment.setArguments(new Bundle());
        getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,mFragment).addToBackStack("name").commit();
    }

    public void navigateToShowEmployeeFragment(){
        ShowEmployeeFragment mFragment = new ShowEmployeeFragment();
        mFragment.setArguments(new Bundle());
        getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,mFragment).addToBackStack("name").commit();
    }

    public void navigateToLoginEmployeeFragment(){
        LoginFragment mFragment = new LoginFragment();
        mFragment.setArguments(new Bundle());
        getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,mFragment).addToBackStack("name").commit();
    }


}