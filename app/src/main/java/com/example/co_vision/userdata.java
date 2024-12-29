package com.example.co_vision;

public class userdata {

    String firstname, lastname, username, email, password;
    int age;
    //double longtitude, latitude;

    public userdata(String firstname, String lastname, String username, String email, int age, String password/*double longtitude, double latitude*/)
    {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.age = age;
        this.password = password;

    }



    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname()
    {
        return firstname;
    }

    public String getLastname()
    {
        return lastname;
    }

    public String getUsername()
    {
        return username;
    }

    public String getEmail()
    {
        return email;
    }

    public int getAge()
    {
        return age;
    }
}
