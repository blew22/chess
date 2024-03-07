package service;

import dataAccess.*;
import exception.ResponseException;

public class Service {

    final static UserDataAccess userDataAccess;
    final static GameDataAccess gameDataAccess;
    final static AuthDataAccess authDataAccess;

    static {
        try {
            userDataAccess = new SQLUserDAO();
            gameDataAccess = new SQLGameDAO();
            authDataAccess = new SQLAuthDAO();

        } catch (ResponseException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }



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