package com.vilan.pablo.technology.store.model.entities;


public class User{
    private String email;
    private String password;
    private String username;
    private String name;
    private String surname;
    private String telephone;

    public User(String username, String email, String password, String name, String surname, String telephone){
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.telephone = telephone;
    }

    public String getUsername(){return this.username;}
    public void setUsername(String username){this.username = username;}

    public String getEmail(){return this.email;}
    public void setEmail(String email){this.email = email;}

    public String getPassword(){return this.password;}
    public void setPassword(String password){this.password = password;}

    public String getName(){return this.name;}
    public void setName(String name){this.name = name;}

    public String getSurname(){return this.surname;}
    public void setSurname(String surname){this.surname = surname;}

    public String getTelephone(){return this.telephone;}
    public void setTelephone(String telephone){this.telephone = telephone;}
}