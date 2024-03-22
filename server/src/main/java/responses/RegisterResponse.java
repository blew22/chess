package responses;

import model.AuthData;

public class RegisterResponse {

    String username;
    String authToken;

    public RegisterResponse(AuthData authData){
        username = authData.username();
        authToken = authData.authToken();
    }

    public String getAuthToken() {
        return authToken;
    }
}