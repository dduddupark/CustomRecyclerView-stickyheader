package com.example.yap.customrecycler;

import java.util.ArrayList;

public class People {

    private String firstName;
    private ArrayList<LastNames> lastNames;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public ArrayList<LastNames> getLastNames() {
        return lastNames;
    }

    public void setLastNames(ArrayList<LastNames> lastNames) {
        this.lastNames = lastNames;
    }

    public static class LastNames
    {
        String lastName;
        String age;

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }
}
