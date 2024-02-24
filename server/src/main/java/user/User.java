package user;

import java.lang.String;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getUsername(), user.getUsername()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getEmail(), user.getEmail());
    }


    public void setAuthToken(int authToken) {
        this.authToken = authToken;
    }
}
