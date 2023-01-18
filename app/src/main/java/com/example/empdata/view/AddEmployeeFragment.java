package com.example.empdata.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.empdata.R;
import com.example.empdata.model.EmployeeDatabase;
import com.example.empdata.presenter.AddEmployeePresenter;


public class AddEmployeeFragment extends Fragment {

    public EditText mNameEditText,mPositionEditText,mSalaryEditText,mAgeEditText,mEmailEditText,mPasswordEditText;
    private Button mSaveButton;
    AddEmployeePresenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view =  inflater.inflate(R.layout.fragment_add_employee, container, false);
        EmployeeDatabase database = EmployeeDatabase.getInstance(getContext());
        mPresenter = new AddEmployeePresenter(this, database);
        mNameEditText = view.findViewById(R.id.employeeName);
        mAgeEditText = view.findViewById(R.id.employeeAge);
        mPositionEditText = view.findViewById(R.id.employeePosition);
        mSalaryEditText = view.findViewById(R.id.employeeSalary);
        mEmailEditText =  view.findViewById(R.id.employeeEmail);
        mPasswordEditText = view.findViewById(R.id.employeePassword);
        mSaveButton = view.findViewById(R.id.addEmployeeBtn);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onSaveButtonClicked();
            }
        });

        return view;
    }

    public void navigateToHomeFragment(){
        HomeFragment mFragment = new HomeFragment();
        mFragment.setArguments(new Bundle());
        getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,mFragment).addToBackStack("name").commit();
    }
}