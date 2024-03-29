package service;

import dataAccess.*;
import exception.ResponseException;
import model.User;


public class UserService extends Service{

    final static UserDataAccess userDataAccess;

    static {
        try {
            userDataAccess = new SQLUserDAO();
        } catch (ResponseException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    final static AuthDataAccess authDataAccess;

    static {
        try {
            authDataAccess = new SQLAuthDAO();
        } catch (ResponseException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }


    public Object registerUser(User user) throws ResponseException{
        if (userDataAccess.userExists(user)) { //allows duplicate usernames if password is different
            throw new ResponseException(403, "Error: already taken");
        } else {
            return userDataAccess.registerUser(user);
        }
    }

    public Object loginUser(User user) throws ResponseException{
        if (userDataAccess.userExists(user)) {
            return authDataAccess.loginUser(user);
        } else {
            throw new ResponseException(401, "Error: unauthorized");
        }
    }

    public boolean logoutUser(String authToken) throws ResponseException {
        if (authDataAccess.isLoggedIn(authToken)) {
            authDataAccess.logout(authToken);
            return true;
        } else {
            return false;
        }
    }
}
