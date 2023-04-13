package com.example.empdata.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.empdata.R;
import com.example.empdata.model.EmployeeDatabase;
import com.example.empdata.presenter.LoginPresenter;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class LoginFragment extends Fragment {
    public EditText mEmployeeUserName;
    public EditText mEmployeePassword;
    public Button mLoginButton;
    public TextView mSignView;
    LoginPresenter mPresenter;
    private static final String LOGIN_PREFS = "session_preferences";
    private static final String IS_LOGGED_IN = "is_logged_in";
    String userName;

    private Target[] targets;
    private int currentIndex = 0;
    private ShowcaseView showcaseView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        EmployeeDatabase database = EmployeeDatabase.getInstance(getContext());
        mPresenter = new LoginPresenter(this, database);
        mEmployeeUserName = view.findViewById(R.id.employeeEmail);
        mEmployeePassword = view.findViewById(R.id.employeePassword);
        mLoginButton = view.findViewById(R.id.loginBtn);
        mSignView = view.findViewById(R.id.needAccount);
        login_user_guide(view);
        mLoginButton.setOnClickListener(v ->{
//            String username = mEmployeePassword.getText().toString();
//            userName = database.employeeDao().getUserName(username);
//            mPresenter.onLoginButtonClicked();
            if (!validateUsername() | !validatePassword()) {
                Toast.makeText(requireActivity(),"Invalid Credential",Toast.LENGTH_LONG).show();

            } else {
                checkUser();
            }
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

    public Boolean validateUsername() {
        String val = mEmployeeUserName.getText().toString();
        if (val.isEmpty()) {
            mEmployeeUserName.setError("Username cannot be empty");
            return false;
        } else {
            mEmployeeUserName.setError(null);
            return true;
        }
    }
    public Boolean validatePassword(){
        String val = mEmployeePassword.getText().toString();
        if (val.isEmpty()) {
            mEmployeePassword.setError("Password cannot be empty");
            return false;
        } else {
            mEmployeePassword.setError(null);
            return true;
        }
    }
    public void checkUser(){
        String username = mEmployeeUserName.getText().toString().trim();
        String password = mEmployeePassword.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("employees");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(username);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    mEmployeeUserName.setError(null);
                    String usernameFromDB = snapshot.child(username).child("username").getValue(String.class);
                    String passwordFromDB = snapshot.child(username).child("password").getValue(String.class);
                    if (passwordFromDB.equals(password)) {
                        mEmployeeUserName.setError(null);
                        String nameFromDB = snapshot.child(username).child("name").getValue(String.class);
                        String emailFromDB = snapshot.child(username).child("email").getValue(String.class);
                        Integer userSalaryFromDB = snapshot.child(username).child("salary").getValue(Integer.class);
                        Integer userAgeFromDB = snapshot.child(username).child("age").getValue(Integer.class);
                        String userPositionFromDB = snapshot.child(username).child("position").getValue(String.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("name", nameFromDB);
                        bundle.putString("email",emailFromDB);
                        bundle.putString("username", usernameFromDB);
                        bundle.putInt("age", userAgeFromDB);
                        bundle.putInt("salary", userSalaryFromDB);
                        bundle.putString("position", userPositionFromDB);
                        bundle.putString("password", passwordFromDB);
                        EditProfileFragment mFragment2 = new EditProfileFragment();
                        ProfileFragment mFragment = new ProfileFragment();
                        mFragment.setArguments(bundle);
                        mFragment2.setArguments(bundle);
                        getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, mFragment).addToBackStack("name").commit();

                    } else {
                        mEmployeePassword.setError("Invalid Credentials");
                        mEmployeePassword.requestFocus();
                    }
                } else {
                    mEmployeeUserName.setError("User does not exist");
                    mEmployeeUserName.requestFocus();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }


    void login_user_guide(View view) {

        // Initialize the views to showcase
        targets = new Target[] {
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
                Toast.makeText(requireActivity(), "End of tutorial", Toast.LENGTH_SHORT).show();
            }
        });

        //Skip Button to skip the Tutorial
        customSkipButton.setOnClickListener(v -> showcaseView.hide());

    }
}