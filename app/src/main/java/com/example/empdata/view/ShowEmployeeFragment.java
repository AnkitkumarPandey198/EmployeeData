package com.example.empdata.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.empdata.EmployeeAdapter;
import com.example.empdata.R;
import com.example.empdata.model.Employee;
import com.example.empdata.model.EmployeeDatabase;
import com.example.empdata.presenter.ShowEmployeePresenter;

import java.util.List;

public class ShowEmployeeFragment extends Fragment {
    ShowEmployeePresenter mPresenter;

    private RecyclerView mEmployeeRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_employee, container, false);
        EmployeeDatabase database = EmployeeDatabase.getInstance(getContext());
        mPresenter = new ShowEmployeePresenter(this,database);
        mPresenter.loadEmployeeData();
        mEmployeeRecyclerView = view.findViewById(R.id.employee_recycler_view);
        mEmployeeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    public void setEmployeeData(List<Employee> employeeList) {
        mEmployeeRecyclerView.setAdapter(new EmployeeAdapter(employeeList));
}
}