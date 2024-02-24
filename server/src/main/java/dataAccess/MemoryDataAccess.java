package dataAccess;

import java.util.ArrayList;
import java.util.Objects;

import responses.RegisterResponse;
import user.User;
import chess.ChessGame;

public class MemoryDataAccess implements DataAccess {

    final private ArrayList<User> users = new ArrayList<User>();

    final private ArrayList<ChessGame> games = new ArrayList<ChessGame>();

    public void clear(){
        users.clear();
        games.clear();
    }

    public Object registerUser(User user){
        User newUser = new User(user.getUsername(), user.getPassword(), user.getEmail());
        users.add(user);
        String authToken = "1234";
        RegisterResponse response = new RegisterResponse(authToken, newUser);
        return response;
    }

    public boolean userExists(User user){
        return users.contains(user);
    }

}
