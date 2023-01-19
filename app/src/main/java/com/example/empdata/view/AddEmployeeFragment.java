package com.example.empdata.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.empdata.R;
import com.example.empdata.model.EmployeeDatabase;
import com.example.empdata.presenter.AddEmployeePresenter;


public class AddEmployeeFragment extends Fragment {

    public EditText mNameEditText,mPositionEditText,mSalaryEditText,mAgeEditText,mEmailEditText,mPasswordEditText;
    AddEmployeePresenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view =  inflater.inflate(R.layout.fragment_add_employee, container, false);
        EmployeeDatabase database = EmployeeDatabase.getInstance(getContext());
        mPresenter = new AddEmployeePresenter(this, database);
        mNameEditText = view.findViewById(R.id.employeeName);
        mNameEditText.setFilters(new InputFilter[] {
                (src, start, end, dst, dStart, dEnd) -> {
                    if (src.equals("")) { // for backspace
                        return src;
                    }
                    if (src.toString().matches("[A-Z ]+")) {
                        return src;
                    }
                    return "";
                }
        });
        mAgeEditText = view.findViewById(R.id.employeeAge);
        mPositionEditText = view.findViewById(R.id.employeePosition);
        mSalaryEditText = view.findViewById(R.id.employeeSalary);
        mEmailEditText =  view.findViewById(R.id.employeeEmail);
        mPasswordEditText = view.findViewById(R.id.employeePassword);
        Button mSaveButton = view.findViewById(R.id.addEmployeeBtn);

        mSaveButton.setOnClickListener(v -> mPresenter.onSaveButtonClicked());
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });

        return view;
    }


    public boolean isInputValid() {
        boolean isValid = true;

        String name = mNameEditText.getText().toString();
        String position = mPositionEditText.getText().toString();
        String email = mEmailEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        String salary = mSalaryEditText.getText().toString();
        String age = mAgeEditText.getText().toString();

        boolean hasUpper = !password.equals(password.toLowerCase());
        boolean hasLower = !password.equals(password.toUpperCase());
        boolean hasDigit = password.matches(".*\\d+.*");
        boolean hasSpecial = !password.matches("[A-Za-z0-9 ]*");
        boolean isValidatePassword = hasUpper && hasLower && hasDigit && hasSpecial;

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        boolean isValidate = email.matches(emailPattern);

        if (TextUtils.isEmpty(name)) {
            mNameEditText.setError("Name is required");
            isValid = false;
        }

        if (TextUtils.isEmpty(position)) {
            mPositionEditText.setError("Position is required");
            isValid = false;
        }

        if (TextUtils.isEmpty(email)) {
            mEmailEditText.setError("Email is required");
            isValid = false;
        } else  if (isValidate) {
            Toast.makeText(requireActivity().getApplicationContext(), "Email is Correct", Toast.LENGTH_SHORT).show();
            isValid = true;

        } else {
            mEmailEditText.setError("Invalid email format");
            isValid = false;
        }

        if (TextUtils.isEmpty(password)) {
            mPasswordEditText.setError("Password is required");
            isValid = false;
        } else if (password.length() < 8) {
            mPasswordEditText.setError("Password must be at least 8 characters long");
            isValid = false;
        }else {
            if (isValidatePassword) {
                Toast.makeText(requireActivity().getApplicationContext(), "Password is Valid", Toast.LENGTH_SHORT).show();
                isValid= true;

            } else {
                mPasswordEditText.setError("Password must have atLeast one uppercase,lowercase, number and special characters");
                isValid =false;
            }
        }

        if (TextUtils.isEmpty(salary)) {
            mSalaryEditText.setError("Salary is required");
            isValid = false;
        }
        if (TextUtils.isEmpty(age)) {
            mAgeEditText.setError("Age is required");
            isValid = false;
        }
        return isValid;
    }

}