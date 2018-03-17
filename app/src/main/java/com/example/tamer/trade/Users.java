package com.example.tamer.trade;

/**
 * Created by tamer on 3/3/2018.
 */

public class Users {
    public String Id;
    public String FirstName;
    public String LastName;
    public String Gender;
    public String Email;
    public String PhoneNumber;
    public String City;

    public Users(String id, String firstName, String lastName, String gender, String email, String phoneNumber, String city) {
        Id = id;
        FirstName = firstName;
        LastName = lastName;
        Gender = gender;
        Email = email;
        PhoneNumber = phoneNumber;
        City = city;
    }

    public Users() {
    }
}
