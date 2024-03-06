package service;

import dataAccess.*;
import exception.ResponseException;

public class Service {

    final static UserDataAccess userDataAccess = new MemoryUserDAO();
    final static GameDataAccess gameDataAccess = new MemoryGameDAO();

    final static AuthDataAccess authDataAccess = new MemoryAuthDAO();

    public Service() {
    }

    public void clear(String authToken) throws ResponseException{
        userDataAccess.clear();
        gameDataAccess.clear();
        authDataAccess.clear();
    }

    public void checkAuthorization(String authToken) throws ResponseException {
        if (!authDataAccess.isLoggedIn(authToken)) {
            throw new ResponseException(401, "Error: not authorized");
        }
    }
}