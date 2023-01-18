package com.example.empdata.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Employee.class}, version = 2,exportSchema = false)
public abstract class EmployeeDatabase extends RoomDatabase {
    public static EmployeeDatabase instance;
    public abstract EmployeeDao employeeDao();

    public static EmployeeDatabase getInstance(Context context) {
        if(instance==null){
            instance = Room.databaseBuilder(context,EmployeeDatabase.class,"employee_database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}