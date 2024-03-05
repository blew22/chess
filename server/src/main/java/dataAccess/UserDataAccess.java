package dataAccess;

import exception.ResponseException;
import model.User;

public interface UserDataAccess {

    void clear();

    Object registerUser(User user) throws ResponseException;

    boolean userExists(User user);
}
