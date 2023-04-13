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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.empdata.R;
import com.example.empdata.SplashActivity;
import com.example.empdata.movielist.MoviesActivity;
import com.example.empdata.news.newsview.NewsFragment;
import com.example.empdata.presenter.HomePresenter;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.android.material.navigation.NavigationView;


public class HomeFragment extends Fragment {

    HomePresenter mPresenter;
    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    HomeFragment homeFragment;
    private static final String LOGIN_PREFS = "session_preferences";
    private static final String IS_LOGGED_IN = "is_logged_in";

    String userName;

    private Target[] targets;
    private int currentIndex = 0;
    private ShowcaseView showcaseView;


    @SuppressLint("NonConstantResourceId")
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
        if(bundle != null){
        userName = bundle.getString("key");}
        else{
            getParentFragmentManager().getPrimaryNavigationFragment();
        }
        userNameTextView.setText(userName);
        navigationView.setNavigationItemSelectedListener(item -> {
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

                case R.id.nav_news:
                    mPresenter.onNewsButtonClicked();
                    return true;

                case R.id.nav_movies:
                    mPresenter.OnMoviesButtonClicked();
                    return true;

                case R.id.nav_logout:
                    mPresenter.onLoginButtonClicked();
                    return true;
            }
            return true;
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

    public void navigateToNewsFragment() {
        NewsFragment mFragment = new NewsFragment();
        mFragment.setArguments(new Bundle());
        getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, mFragment).addToBackStack("name").commit();
    }

    public void navigateToMoviesFragment() {
        Intent intent = new Intent(requireActivity(), MoviesActivity.class);
        startActivity(intent);

    }

    void login_user_guide(View view) {

        // Initialize the views to showcase
        targets = new Target[]{
                new ViewTarget(view.findViewById(R.id.employeeEmail)),
                new ViewTarget(view.findViewById(R.id.employeePassword)),
                new ViewTarget(view.findViewById(R.id.loginBtn)),
                new ViewTarget(view.findViewById(R.id.needAccount)),
                new ViewTarget(view.findViewById(R.id.forgetPassword))

        };

        // Initialize the views title to showcase
        String[] loginPageTitle = {"User's Email Address",
                "User's Password",
                "Login Button",
                "SignUP Link",
                "Forget Password"
        };

        // Initialize the views description to showcase
        String[] loginPageDescription = {"Here User enter his/her Email.",
                "Here User enter his/her Password.",
                "This is Login Button.",
                "This is SignUp Page Link.",
                "This is Forget Password Link."

        };

        //Initialize  the Custom Button
        Button customSkipButton = (Button) LayoutInflater.from(requireContext()).inflate(R.layout.custom_button, null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        layoutParams.bottomMargin = 24;
        layoutParams.leftMargin = 24;
        customSkipButton.setLayoutParams(layoutParams);


        // Show the Showcase view
        showcaseView = new ShowcaseView.Builder(requireActivity())
                .setTarget(targets[currentIndex])
                .setContentTitle(loginPageTitle[currentIndex])
                .setContentText(loginPageDescription[currentIndex])
                .setStyle(R.style.ShowcaseView)
                .build();


        showcaseView.addView(customSkipButton);

        //Next Button to Skip the Tutorial
        showcaseView.setButtonText("Next");

        showcaseView.overrideButtonClick(v -> {

            currentIndex++;

            if (currentIndex < targets.length) {

                showcaseView.setTarget(targets[currentIndex]);
                showcaseView.setContentTitle(loginPageTitle[currentIndex]);
                showcaseView.setContentText(loginPageDescription[currentIndex]);

            } else {

                showcaseView.hide();
                showcaseView.hideButton();
                Toast.makeText(requireActivity(), "End of tutorial", Toast.LENGTH_SHORT).show();
            }
        });

        //Skip Button to skip the Tutorial
        customSkipButton.setOnClickListener(v -> {

            showcaseView.hide();
            showcaseView.hideButton();

        });

    }

}


