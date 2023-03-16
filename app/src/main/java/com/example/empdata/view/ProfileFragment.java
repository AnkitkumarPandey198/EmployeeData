package com.example.empdata.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.empdata.R;
import com.example.empdata.firebaseDatabase.Employees;
import com.example.empdata.firebaseDatabase.EmployeesAdapter;
import com.example.empdata.firebaseDatabase.OnItemClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {

    TextView profileUserName;
    TextView titleName,titleUserAge;

    ImageView ivProfileEdit;

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        titleName = view.findViewById(R.id.titleName);
        titleUserAge = view.findViewById(R.id.titleUserAge);
        ivProfileEdit = view.findViewById(R.id.profileEdit);

        Bundle bundle = this.getArguments();

        String nameUser = bundle.getString("name");
        int age = bundle.getInt("age");
        String userName = bundle.getString("username");
        titleName.setText(nameUser);
        titleUserAge.setText(String.valueOf(age));


        ivProfileEdit.setOnClickListener(v -> {
            passData();

        });

        // recycler View
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(layoutManager);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("employees");

        // Retrieve the user data from the Realtime Database
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Employees> employeesList = new ArrayList<>();
                for (DataSnapshot EmployeeSnapshot : dataSnapshot.getChildren()) {
                    Employees employees = EmployeeSnapshot.getValue(Employees.class);
                    employeesList.add(employees);
                }


                // Set up the RecyclerView adapter to display the employees data
                EmployeesAdapter adapter = new EmployeesAdapter(employeesList, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Employees employees) {
                        titleName.setText(employees.getName());
                        titleUserAge.setText(String.valueOf(employees.getAge()));

                    }
                });
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });

        return view;
    }


    public void passData(){
        String username = profileUserName.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("employees");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(username);
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String usernameFromDB = snapshot.child(username).child("username").getValue(String.class);
                    String passwordFromDB = snapshot.child(username).child("password").getValue(String.class);
                    String nameFromDB = snapshot.child(username).child("name").getValue(String.class);
                    String emailFromDB = snapshot.child(username).child("email").getValue(String.class);
                    Integer userSalaryFromDB = snapshot.child(username).child("salary").getValue(Integer.class);
                    Integer userAgeFromDB = snapshot.child(username).child("age").getValue(Integer.class);
                    String userPositionFromDB = snapshot.child(username).child("position").getValue(String.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name", nameFromDB);
                    bundle.putString("email",emailFromDB);
                    bundle.putString("username", usernameFromDB);
                    bundle.putInt("age", userAgeFromDB);
                    bundle.putInt("salary", userSalaryFromDB);
                    bundle.putString("position", userPositionFromDB);
                    bundle.putString("password", passwordFromDB);
                    EditProfileFragment mFragment = new EditProfileFragment();
                    mFragment.setArguments(bundle);
                    getParentFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, mFragment).addToBackStack("name").commit();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

}