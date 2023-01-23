package com.example.empdata.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EmployeeDao {

    @Insert
    void insert(Employee employee);

    @Update
    void update(Employee employee);

    @Delete
    void delete(Employee employee);

    @Query("Select name from employee_table where email =:email")
    String getUserName(String email);

    @Query("Select * from employee_table where email =:email and password =:password")
    boolean isUserValid(String email,String password);

    @Query("SELECT * FROM employee_table")
    LiveData<List<Employee>> getAllEmployees();
}
