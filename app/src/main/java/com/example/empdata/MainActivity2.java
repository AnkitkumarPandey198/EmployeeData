package com.example.empdata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.empdata.view.HomeFragment;


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
            getSupportFragmentManager().popBackStack();
        }else {
            fragmentTransaction.replace(R.id.fragmentContainerView, new HomeFragment());

        }
        fragmentTransaction.commit();

    }
}