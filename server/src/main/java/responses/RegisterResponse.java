package responses;

import user.User;

public class RegisterResponse {

    private String authToken;

    private User user;

    private String username;

    public RegisterResponse(String authToken, User user){
        this.authToken = authToken;
        this.user = user;
        username = user.getUsername();
    }



}
