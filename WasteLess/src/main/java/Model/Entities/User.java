package Model.Entities;

import java.util.*;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String password;
    private Set groceryLists;

    private Integer calorieIntake = 0;

    public User() {}

    public User(String fname, String lname, String password) {
        this.firstName = fname;
        this.lastName = lname;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName( String first_name ) {
        this.firstName = first_name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName( String last_name ) {
        this.lastName = last_name;
    }

    public Set getGroceryLists() {
        return groceryLists;
    }

    public void setGroceryLists( Set groceryLists ) {
        this.groceryLists = groceryLists;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCalorieIntake() {
        return calorieIntake;
    }

    public void setCalorieIntake(Integer calorieIntake) {
        this.calorieIntake = calorieIntake;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}