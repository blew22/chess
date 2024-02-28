package responses;

import model.AuthData;

import java.util.Objects;

public class LoginResponse {

    String username;
    public String authToken;

    public LoginResponse(AuthData authData){
        this.username = authData.username();
        this.authToken = authData.authToken();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginResponse response = (LoginResponse) o;
        return Objects.equals(username, response.username);
    }

}
