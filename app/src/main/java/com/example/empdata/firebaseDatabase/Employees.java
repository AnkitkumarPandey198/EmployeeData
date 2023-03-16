package com.example.empdata.firebaseDatabase;

public class Employees {

    private String name;
    private int age;
    private String position;
    private int salary;
    private String email;
    private String password;

    private String username;

    public Employees(){

    }

    public Employees(String name, int age, String position, int salary, String email, String password, String username) {
        this.name = name;
        this.age = age;
        this.position = position;
        this.salary = salary;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
