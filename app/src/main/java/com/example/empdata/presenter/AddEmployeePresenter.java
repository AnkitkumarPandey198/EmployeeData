package com.example.empdata.presenter;

import android.widget.Toast;

import com.example.empdata.firebaseDatabase.Employees;
import com.example.empdata.model.Employee;
import com.example.empdata.model.EmployeeDatabase;
import com.example.empdata.view.AddEmployeeFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEmployeePresenter {
    private final AddEmployeeFragment
            mAddEmployeeFragment;
    private final EmployeeDatabase mDatabase;
    int age;
    int salary;
    Employee employee;

    FirebaseDatabase database;
    DatabaseReference reference;

    public AddEmployeePresenter(AddEmployeeFragment addEmployeeFragment, EmployeeDatabase database) {
        mAddEmployeeFragment = addEmployeeFragment;
        mDatabase = database;
    }

    public void onSaveButtonClicked() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("employees");
        String name = mAddEmployeeFragment.mNameEditText.getText().toString();
        String position = mAddEmployeeFragment.mPositionEditText.getText().toString();
        String email = mAddEmployeeFragment.mEmailEditText.getText().toString();
        String password = mAddEmployeeFragment.mPasswordEditText.getText().toString();
        String username = name.toLowerCase().replace(' ', '_');
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

        if(mAddEmployeeFragment.isInputValid()) {
            Employees helperClass = new Employees(name, age, position, salary, email, password,username);
            reference.child(username).setValue(helperClass);
            mAddEmployeeFragment.navigateToLogin();
            employee = new Employee(name, age, position, salary, email, password);
            Toast.makeText(mAddEmployeeFragment.getContext(), "Employee Data Added", Toast.LENGTH_LONG).show();
            mDatabase.employeeDao().insert(employee);

        }else {
            Toast.makeText(mAddEmployeeFragment.getContext(), "Please enter the Values in Required field", Toast.LENGTH_LONG).show();

        }

    }
}
