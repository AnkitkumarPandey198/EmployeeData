package com.example.empdata.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.empdata.R;


public class ProfileFragment extends Fragment {

    TextView profileName, profileEmail, profileSalary, profilePassword;
    TextView titleName, titleUserPosition,titleUserAge;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profileName = view.findViewById(R.id.profileName);
        profileEmail = view.findViewById(R.id.profileEmail);
        profileSalary = view.findViewById(R.id.profileUserSalary);
        profilePassword = view.findViewById(R.id.profilePassword);
        titleName = view.findViewById(R.id.titleName);
        titleUserPosition = view.findViewById(R.id.titleUserPosition);
        titleUserAge = view.findViewById(R.id.titleUserAge);

        showUserData();

        return view;
    }

    public void showUserData(){

        Bundle bundle = this.getArguments();

        String nameUser = bundle.getString("name");
        String emailUser = bundle.getString("email");
        String userPosition = bundle.getString("position");
        String passwordUser = bundle.getString("password");
        int age = bundle.getInt("age");
        int salary = bundle.getInt("salary");

        titleName.setText(nameUser);
        titleUserPosition.setText(userPosition);
        titleUserAge.setText(String.valueOf(age));
        profileName.setText(nameUser);
        profileEmail.setText(emailUser);
        profileSalary.setText(String.valueOf(salary));
        profilePassword.setText(passwordUser);
    }


}