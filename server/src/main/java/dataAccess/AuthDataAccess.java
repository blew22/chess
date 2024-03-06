package dataAccess;

import exception.ResponseException;
import model.User;

public interface AuthDataAccess {

    void clear() throws ResponseException;

    String getUsername(String authToken);

    Object loginUser(User user) throws ResponseException;

    boolean isLoggedIn(String authToken);

    void logout(String authToken);

}
