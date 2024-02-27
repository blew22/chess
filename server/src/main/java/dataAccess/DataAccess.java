package dataAccess;

import exception.ResponseException;
import model.AuthData;
import model.GameData;
import model.User;
import requests.JoinGameRequest;

public interface DataAccess {

    public void clear();

    public Object registerUser(User user) throws ResponseException;

    public boolean userExists(User user);

    public Object loginUser(User user);

    public void logoutUser(String authToken);

    public boolean isLoggedIn(String authToken);

    public Object createGame(GameData game);

    public Object listGames();

    public Object joinGame(JoinGameRequest request) throws ResponseException;

    public boolean gameExists(Integer gameID);

}
