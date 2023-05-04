package com.example.dat257_project_team_1;


import static com.example.dat257_project_team_1.Encryption.*;

public class User {
    private final String id;
    private String encryptedPassword;
    private String email;
    public User(String id, String encyptedPassward, String email) {
        this.id = id;
        this.encryptedPassword = encrypt(encyptedPassward);
        this.email = email;
    }
    public User(String id, String encyptedPassward) {
        this(id, encyptedPassward, null);
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public boolean changePassward(String oldPass, String newPass){
        if(decrypt(this.encryptedPassword) == oldPass){
            this.encryptedPassword = encrypt(newPass);
            return true;
        }else{
            System.out.println("false password");
            return false;
        }
    }
}
