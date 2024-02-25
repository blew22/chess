package dataAccess;

import java.util.ArrayList;

import model.AuthData;
import responses.RegisterResponse;
import model.User;
import chess.ChessGame;

public class MemoryDataAccess implements DataAccess {

    final private ArrayList<User> users = new ArrayList<User>();

    final private ArrayList<ChessGame> games = new ArrayList<ChessGame>();

    public void clear(){
        users.clear();
        games.clear();
    }

    public Object registerUser(User user){
        User newUser = new User(user.username(), user.password(), user.email());
        users.add(user);
        String authToken = "1234";
        AuthData newAuth = new AuthData(user.username());
        RegisterResponse response = new RegisterResponse(newAuth);
        return response;
    }

    public boolean userExists(User user){
        return users.contains(user);
    }

}
