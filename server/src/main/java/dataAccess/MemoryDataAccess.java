package dataAccess;

import java.util.ArrayList;
import java.util.HashMap;

import exception.ResponseException;
import model.AuthData;
import responses.RegisterResponse;
import model.User;
import chess.ChessGame;

public class MemoryDataAccess implements DataAccess {

    final private ArrayList<User> users = new ArrayList<User>();

    final private ArrayList<ChessGame> games = new ArrayList<ChessGame>();

    final private HashMap<String, String> authorizations = new HashMap<>();

    public void clear() {
        users.clear();
        games.clear();
    }

    public Object registerUser(User user) {
//        User newUser = new User(user.username(), user.password(), user.email());
        users.add(user);
//        AuthData newAuth = new AuthData(user.username());
        return loginUser(user);
    }

    public boolean userExists(User user) {
        return users.contains(user);
    }

    @Override
    public Object loginUser(User user) {
        AuthData newAuth = new AuthData(user.username());
        authorizations.put(newAuth.authToken(), user.username());
        return new RegisterResponse(newAuth);
    }

    public void logoutUser(String authToken){
        authorizations.remove(authToken);
    }

    public boolean isLoggedIn(String authToken){
        return authorizations.containsKey(authToken);
    }
}
