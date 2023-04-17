package com.example.empdata.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ankitkrpandey.userguideview.UserGuideView;
import com.ankitkrpandey.userguideview.config.Gravity;
import com.ankitkrpandey.userguideview.config.PointerType;
import com.example.empdata.R;
import com.example.empdata.model.EmployeeDatabase;
import com.example.empdata.presenter.LoginPresenter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class LoginFragment extends Fragment {
    public EditText mEmployeeUserName;
    public EditText mEmployeePassword;
    public Button mLoginButton;
    public TextView mSignView, mLoginTitle, mForgetPassword;
    LoginPresenter mPresenter;
    private static final String LOGIN_PREFS = "session_preferences";
    private static final String IS_LOGGED_IN = "is_logged_in";
    String userName;

    private UserGuideView mGuideView;
    private UserGuideView.Builder builder;

    View customView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        EmployeeDatabase database = EmployeeDatabase.getInstance(getContext());
        mPresenter = new LoginPresenter(this, database);
        mLoginTitle = view.findViewById(R.id.login_Title);
        mForgetPassword = view.findViewById(R.id.forgetPassword);
        mEmployeeUserName = view.findViewById(R.id.employeeEmail);
        mEmployeePassword = view.findViewById(R.id.employeePassword);
        mLoginButton = view.findViewById(R.id.loginBtn);
        mSignView = view.findViewById(R.id.needAccount);
        guideViewLoginUser();


        mLoginButton.setOnClickListener(v -> {
//            String username = mEmployeePassword.getText().toString();
//            userName = database.employeeDao().getUserName(username);
//            mPresenter.onLoginButtonClicked();
            if (!validateUsername() | !validatePassword()) {
                Toast.makeText(requireActivity(), "Invalid Credential", Toast.LENGTH_LONG).show();

            } else {
                checkUser();
            }
        });
        mSignView.setOnClickListener(v -> mPresenter.onSignUpClicked());
        return view;
    }


    public void showHomePage() {
        SharedPreferences loginPrefs = requireActivity().getSharedPreferences(LOGIN_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginPrefs.edit();
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.apply();
        Bundle bundle = new Bundle();
        bundle.putString("key", userName);
        HomeFragment mFragment = new HomeFragment();
        mFragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, mFragment).addToBackStack("name").commit();
    }

    public void showErrorMessage() {
        Toast.makeText(getContext(), "Wrong Credentials", Toast.LENGTH_LONG).show();
    }

    public void showAddEmployee() {
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

    public Boolean validatePassword() {
        String val = mEmployeePassword.getText().toString();
        if (val.isEmpty()) {
            mEmployeePassword.setError("Password cannot be empty");
            return false;
        } else {
            mEmployeePassword.setError(null);
            return true;
        }
    }

    public void checkUser() {
        String username = mEmployeeUserName.getText().toString().trim();
        String password = mEmployeePassword.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("employees");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(username);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    mEmployeeUserName.setError(null);
                    String usernameFromDB = snapshot.child(username).child("username").getValue(String.class);
                    String passwordFromDB = snapshot.child(username).child("password").getValue(String.class);
                    if (Objects.equals(passwordFromDB, password)) {
                        mEmployeeUserName.setError(null);
                        String nameFromDB = snapshot.child(username).child("name").getValue(String.class);
                        String emailFromDB = snapshot.child(username).child("email").getValue(String.class);
                        Integer userSalaryFromDB = snapshot.child(username).child("salary").getValue(Integer.class);
                        Integer userAgeFromDB = snapshot.child(username).child("age").getValue(Integer.class);
                        String userPositionFromDB = snapshot.child(username).child("position").getValue(String.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("name", nameFromDB);
                        bundle.putString("email", emailFromDB);
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


    @SuppressLint("NonConstantResourceId")
    void guideViewLoginUser() {

        //Custom View for the Buttons
        customView = LayoutInflater.from(requireContext()).inflate(R.layout.showcase_layout_button, null);

        //Guide view Implementer
        builder = new UserGuideView.Builder(requireContext())
                .setTitle("Login Title")
                .setContentText("This is the Login Title.")
                .setGravity(Gravity.center)
                .setPointerType(PointerType.circle)
                .setTargetView(mLoginTitle)
                .setUserGuideListener(v -> {
                    switch (v.getId()) {
                        case R.id.login_Title:
                            builder.setTargetView(mEmployeeUserName).setTitle("Email Field").setContentText("This is the Email Field.").build();
                            mGuideView.removeView(customView);
                            break;
                        case R.id.employeeEmail:
                            builder.setTargetView(mEmployeePassword).setTitle("Password Field").setContentText("This is the Password Field.").build();
                            mGuideView.removeView(customView);
                            break;
                        case R.id.employeePassword:
                            builder.setTargetView(mLoginButton).setTitle("Login Button").setContentText("This is the Login Button.").build();
                            mGuideView.removeView(customView);
                            break;
                        case R.id.loginBtn:
                            builder.setTargetView(mSignView).setTitle("Sign UP Link").setContentText("This is the Sign UP Link.").build();
                            mGuideView.removeView(customView);
                            break;
                        case R.id.needAccount:
                            builder.setTargetView(mForgetPassword).setTitle("Forget Password Link").setContentText("This is the Forget Password Link.").build();
                            mGuideView.removeView(customView);
                            break;
                        case R.id.forgetPassword:
                            return;
                    }
                    mGuideView = builder.build();
                    mGuideView.addView(customView);
                    mGuideView.show();
                });


        mGuideView = builder.build();
        mGuideView.addView(customView);
        mGuideView.show();

        //next Button working
        Button button1 = customView.findViewById(R.id.next_button);
        button1.setOnClickListener(v -> mGuideView.dismiss());

        Button button2 = customView.findViewById(R.id.skip_button);
        button2.setOnClickListener(v -> mGuideView.dismissAll());


    }


}