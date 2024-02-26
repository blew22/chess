package model;

import java.util.UUID;

public record AuthData(String username, String authToken) {

    public AuthData(String username){
        this(username, createAuth());
    }

//    public AuthData(String username, String authToken){
//        this.username = username;
//        this.authToken = authToken;
//    }

    private static String createAuth() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
