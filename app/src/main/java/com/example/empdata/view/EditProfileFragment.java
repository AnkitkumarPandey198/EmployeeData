package com.example.empdata.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.empdata.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class EditProfileFragment extends Fragment {

    public EditText mNameEdit,mPositionEdit,mSalaryEdit,mAgeEdit,mEmailEdit,mPasswordEdit;
    DatabaseReference reference;
    public String mName,mPosition,mEmail,mPassword,mUsername;
    public int mAge,mSalary;
    Button saveButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        reference = FirebaseDatabase.getInstance().getReference("employees");
        mNameEdit = view.findViewById(R.id.employeeName);
        mAgeEdit = view.findViewById(R.id.employeeAge);
        mPositionEdit = view.findViewById(R.id.employeePosition);
        mSalaryEdit = view.findViewById(R.id.employeeSalary);
        mEmailEdit =  view.findViewById(R.id.employeeEmail);
        mPasswordEdit = view.findViewById(R.id.employeePassword);
        saveButton = view.findViewById(R.id.saveBtn);

        showUserData();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNameChanged() || isEmailChanged() || isPasswordChanged() || isAgeChanged() || isPositionChanged() || isSalaryChanged()) {
                    Toast.makeText(requireActivity(), "Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireActivity(), "No Changes Found", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }

    public boolean isNameChanged(){
        if (!mName.equals(mNameEdit.getText().toString())){
            reference.child(mUsername).child("name").setValue(mNameEdit.getText().toString());
            mName = mNameEdit.getText().toString();
            return true;
        } else{
            return false;
        }
    }

    public boolean isEmailChanged(){
        if (!mEmail.equals(mEmailEdit.getText().toString())){
            reference.child(mUsername).child("email").setValue(mEmailEdit.getText().toString());
            mEmail = mEmailEdit.getText().toString();
            return true;
        } else{
            return false;
        }
    }

    public boolean isPasswordChanged(){
        if (!mPassword.equals(mPasswordEdit.getText().toString())){
            reference.child(mUsername).child("password").setValue(mPasswordEdit.getText().toString());
            mPassword = mPasswordEdit.getText().toString();
            return true;
        } else{
            return false;
        }
    }

    public boolean isPositionChanged(){
        if (!mPosition.equals(mPositionEdit.getText().toString())){
            reference.child(mUsername).child("password").setValue(mPositionEdit.getText().toString());
            mPassword = mPositionEdit.getText().toString();
            return true;
        } else{
            return false;
        }
    }

    public boolean isAgeChanged(){
        if (mAge!= Integer.parseInt(mAgeEdit.getText().toString())){
            reference.child(mUsername).child("age").setValue(Integer.parseInt(mAgeEdit.getText().toString()));
            mAge = Integer.parseInt(mAgeEdit.getText().toString());
            return true;
        } else{
            return false;
        }
    }

    public boolean isSalaryChanged(){
        if (mSalary!= Integer.parseInt(mSalaryEdit.getText().toString())){
            reference.child(mUsername).child("salary").setValue(Integer.parseInt(mSalaryEdit.getText().toString()));
            mSalary = Integer.parseInt(mSalaryEdit.getText().toString());
            return true;
        } else{
            return false;
        }
    }

    public void showUserData(){

        Bundle bundle = this.getArguments();

        mName = bundle.getString("name");
        mEmail = bundle.getString("email");
        mPosition = bundle.getString("position");
        mAge = bundle.getInt("age");
        mSalary = bundle.getInt("salary");
        mPassword = bundle.getString("password");
        mUsername = bundle.getString("username");

        mNameEdit.setText(mName);
        mPositionEdit.setText(mPosition);
        mAgeEdit.setText(String.valueOf(mAge));
        mEmailEdit.setText(mEmail);
        mSalaryEdit.setText(String.valueOf(mSalary));
        mPasswordEdit.setText(mPassword);
    }



}