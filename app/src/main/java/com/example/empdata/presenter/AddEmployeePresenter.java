package com.example.empdata.presenter;

import android.widget.Toast;
import com.example.empdata.model.Employee;
import com.example.empdata.model.EmployeeDatabase;
import com.example.empdata.view.AddEmployeeFragment;

public class AddEmployeePresenter {
    private final AddEmployeeFragment
            mAddEmployeeFragment;
    private final EmployeeDatabase mDatabase;
    int age;
    int salary;
    Employee employee;

    public AddEmployeePresenter(AddEmployeeFragment addEmployeeFragment, EmployeeDatabase database) {
        mAddEmployeeFragment = addEmployeeFragment;
        mDatabase = database;
    }

    public void onSaveButtonClicked() {
        String name = mAddEmployeeFragment.mNameEditText.getText().toString();
        String position = mAddEmployeeFragment.mPositionEditText.getText().toString();
        String email = mAddEmployeeFragment.mEmailEditText.getText().toString();
        String password = mAddEmployeeFragment.mPasswordEditText.getText().toString();
        try {
            age = Integer.parseInt(mAddEmployeeFragment.mAgeEditText.getText().toString());
        } catch (NumberFormatException e) {
            mAddEmployeeFragment.mAgeEditText.setError("Enter the Age");
        }


        try {
            salary = Integer.parseInt(mAddEmployeeFragment.mSalaryEditText.getText().toString());
        } catch (NumberFormatException e) {
            mAddEmployeeFragment.mSalaryEditText.setError("Enter the Salary");
        }

        if(!mAddEmployeeFragment.isInputValid()) {
            return;
        }else {
            employee = new Employee(name, age, position, salary, email, password);
            Toast.makeText(mAddEmployeeFragment.getContext(), "Employee Data Added", Toast.LENGTH_LONG).show();
            mDatabase.employeeDao().insert(employee);
        }

    }
}
