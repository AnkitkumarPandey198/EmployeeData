package com.example.empdata.view;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.InputFilter;
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

        return view;
    }


    public void navigateToHomeFragment(){
        LoginFragment mFragment = new LoginFragment();
        mFragment.setArguments(new Bundle());
        getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,mFragment).addToBackStack("name").commit();
    }

    public boolean validateEmail(){
        String input = mEmailEditText.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        boolean isValidate = input.matches(emailPattern);
        if(input.isEmpty()){
            mEmailEditText.setError("Enter the Email Address");
        }
        else  if (isValidate) {
            Toast.makeText(requireActivity().getApplicationContext(), "Email is Correct", Toast.LENGTH_SHORT).show();
            return true;

        } else {
            mEmailEditText.setError("Invalid email format");
        }
        return isValidate;
    }

    public boolean validatePassword(){
        String password = mPasswordEditText.getText().toString();
        boolean hasUpper = !password.equals(password.toLowerCase());
        boolean hasLower = !password.equals(password.toUpperCase());
        boolean hasDigit = password.matches(".*\\d+.*");
        boolean hasSpecial = !password.matches("[A-Za-z0-9 ]*");
        boolean isValidate = hasUpper && hasLower && hasDigit && hasSpecial;
        if (password.isEmpty()) {
            mPasswordEditText.setError("Please enter a password");
        } else if (password.length() < 8) {
            mPasswordEditText.setError("Password must be at least 8 characters long");
        } else {

            if (isValidate) {
                Toast.makeText(requireActivity().getApplicationContext(), "Password is Valid", Toast.LENGTH_SHORT).show();
                return true;

            } else {
                mPasswordEditText.setError("Password must have atLeast one uppercase,lowercase, number and special characters");
            }
        }
        return isValidate;
    }
}