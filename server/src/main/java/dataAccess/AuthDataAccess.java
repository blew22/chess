package dataAccess;

import exception.ResponseException;
import model.User;

public interface AuthDataAccess {

    void clear() throws ResponseException;

    String getUsername(String authToken) throws ResponseException;

    Object loginUser(User user) throws ResponseException;

    boolean isLoggedIn(String authToken) throws ResponseException;

    void logout(String authToken);

}
