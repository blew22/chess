package service;

import dataAccess.DataAccess;
import dataAccess.MemoryDataAccess;
import exception.ResponseException;
import model.AuthData;
import model.GameData;
import model.User;
import requests.CreateGameRequest;

public class Service {

    private final DataAccess dataAccess = new MemoryDataAccess();

    public Service() {
    }

    public void clear() {
        dataAccess.clear();
    }

    public Object registerUser(User user) throws ResponseException {
        if (dataAccess.userExists(user)) {
//            ErrorResponse errorResponse = new ErrorResponse("already taken");
//            return errorResponse;
            throw new ResponseException(403, "Error: already taken");
            //error
        } else {
            //create user, return auth
            return dataAccess.registerUser(user);
        }
    }

    ;

    public Object loginUser(User user) throws ResponseException {
        if (dataAccess.userExists(user)) {
            return dataAccess.loginUser(user);
        } else {
            throw new ResponseException(401, "Error: unauthorized");
        }
    }

    public boolean logoutUser(String authToken) {
        if (dataAccess.isLoggedIn(authToken)) {
            dataAccess.logoutUser(authToken);
            return true;
        } else {
            return false;
        }
    }

    public Object createGame(CreateGameRequest gameRequest) throws ResponseException{
        if(dataAccess.isLoggedIn(gameRequest.authToken())) {
            GameData gameData = new GameData(gameRequest.gameName());
            return dataAccess.createGame(gameData);
        } else {
            throw new ResponseException(401, "Error: unauthorized");
        }
    }

}
