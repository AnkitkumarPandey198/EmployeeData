package com.example.empdata;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.empdata.model.Employee;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>{
    private final List<Employee> mEmployeeList;

    public EmployeeAdapter(List<Employee> employeeList) {
        mEmployeeList = employeeList;
    }

    @NonNull
    @Override
    public EmployeeAdapter.EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_list_row, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeAdapter.EmployeeViewHolder holder, int position) {
        Employee employee = mEmployeeList.get(position);
        holder.mName.setText(employee.getName());
        holder.mAge.setText(String.valueOf(employee.getAge()));
        holder.mPosition.setText(employee.getPosition());
        holder.mSalary.setText(String.valueOf(employee.getSalary()));
        holder.mEmail.setText(employee.getEmail());
        holder.mPassword.setText(employee.getPassword());
    }

    @Override
    public int getItemCount() {
        return mEmployeeList.size();
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        public TextView mName,mPosition,mSalary,mAge,mEmail,mPassword;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            mName=itemView.findViewById(R.id.empNameValue);
            mAge=itemView.findViewById(R.id.empAgeValue);
            mPosition=itemView.findViewById(R.id.empPositionValue);
            mSalary=itemView.findViewById(R.id.empSalaryValue);
            mEmail=itemView.findViewById(R.id.empEmailValue);
            mPassword=itemView.findViewById(R.id.empPasswordValue);
        }
    }
}
