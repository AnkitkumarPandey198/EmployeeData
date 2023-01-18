package com.example.empdata.presenter;

import androidx.lifecycle.LiveData;

import com.example.empdata.model.Employee;
import com.example.empdata.model.EmployeeDatabase;
import com.example.empdata.view.ShowEmployeeFragment;

import java.util.List;

public class ShowEmployeePresenter {

    private final ShowEmployeeFragment mShowEmployeeFragment;
    private final EmployeeDatabase mDatabase;

    public ShowEmployeePresenter(ShowEmployeeFragment showEmployeeFragment, EmployeeDatabase database) {
        mShowEmployeeFragment = showEmployeeFragment;
        mDatabase = database;
    }

    public void loadEmployeeData() {
        LiveData<List<Employee>> employeeList = mDatabase.employeeDao().getAllEmployees();
        employeeList.observe(mShowEmployeeFragment, mShowEmployeeFragment::setEmployeeData);
    }
}
