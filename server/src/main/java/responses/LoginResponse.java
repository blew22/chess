package responses;

import model.AuthData;

public class LoginResponse {

    String username;
    String authToken;

    public LoginResponse(AuthData authData){
        this.username = authData.username();
        this.authToken = authData.authToken();
    }

}
