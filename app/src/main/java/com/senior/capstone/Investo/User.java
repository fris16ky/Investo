package com.senior.capstone.Investo;

public class User {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String username;
    private String password;
    private String profilePic;

    //initial user creation constructor
    public User(String first_name, String last_name, String email, String username, String password, String profilePic) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.profilePic = profilePic;
    }

    public User(int id, String first_name, String last_name, String email, String username, String password) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User(int id, String first_name, String last_name, String email) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
    }

    public User(String first_name, String last_name, String email) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
    }

    public User(String first_name, String last_name) {
        this.first_name = first_name;
        this.last_name = last_name;
    }

    //TESTING or profile picture
    public User(int id, String profilePic) {
        this.id = id;
        this.profilePic = profilePic;
    }

    public User(String first_name) {
        this.first_name = first_name;
    }

    public User(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", profilePic='" + profilePic + '\'' +
                '}';
    }
}
