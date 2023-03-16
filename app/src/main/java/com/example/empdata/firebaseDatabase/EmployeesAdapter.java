package com.example.empdata.firebaseDatabase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.empdata.R;

import java.util.List;

public class EmployeesAdapter extends RecyclerView.Adapter<EmployeesAdapter.ViewHolder>{

    private  List<Employees> employeesList;

    private OnItemClickListener listener;

    public EmployeesAdapter(List<Employees> employeesList, OnItemClickListener listener) {
        this.employeesList = employeesList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Employees employees = employeesList.get(position);
        holder.profileEmail.setText(employees.getEmail());
        holder.profileSalary.setText(String.valueOf(employees.getSalary()));
        holder.profileUserName.setText(employees.getUsername());
        holder.profileUserPosition.setText(employees.getPosition());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(employees);

            }
        });


    }

    @Override
    public int getItemCount() {
        return employeesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView profileUserName, profileEmail, profileSalary, profileUserPosition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileUserName = itemView.findViewById(R.id.profileName);
            profileEmail = itemView.findViewById(R.id.profileEmail);
            profileSalary = itemView.findViewById(R.id.profileUserSalary);
            profileUserPosition = itemView.findViewById(R.id.profilePosition);

        }
    }



}
