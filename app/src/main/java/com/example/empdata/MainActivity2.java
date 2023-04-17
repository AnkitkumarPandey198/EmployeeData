package com.example.empdata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.empdata.view.HomeFragment;
import com.example.empdata.view.LoginFragment;


public class MainActivity2 extends AppCompatActivity {

    private static final String LOGIN_PREFS = "session_preferences";
    private static final String IS_LOGGED_IN = "is_logged_in";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        SharedPreferences sharedPreferences = getSharedPreferences(LOGIN_PREFS, MODE_PRIVATE);

        boolean isLoggedIn = sharedPreferences.getBoolean(IS_LOGGED_IN, false);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if(!isLoggedIn) {
            fragmentTransaction.replace(R.id.fragmentContainerView, new HomeFragment());
        }else {
            getSupportFragmentManager().popBackStack();
        }
        fragmentTransaction.commit();

    }
}