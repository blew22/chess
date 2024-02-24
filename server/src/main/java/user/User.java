package user;

import java.lang.String;

public class User {

    private String username;
    private String password;
    private String email;

    private int authToken;

    public User(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername(){
        return username;
    }

    public int getAuthToken() {
        return authToken;
    }

    public void setAuthToken(int authToken) {
        this.authToken = authToken;
    }
}
