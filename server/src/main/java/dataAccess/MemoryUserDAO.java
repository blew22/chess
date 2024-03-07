/*
package dataAccess;

import exception.ResponseException;
import model.User;

import java.util.ArrayList;

public class MemoryUserDAO implements UserDataAccess {

    final private static ArrayList<User> users = new ArrayList<>();

    final private static AuthDataAccess authDataAccess = new MemoryAuthDAO();

    public void clear() {
        users.clear();
    }

    public Object registerUser(User user) throws ResponseException {
        users.add(user);
        return loginUser(user);
    }

    public boolean userExists(User user) {
        return users.contains(user);
    }

    public Object loginUser(User user) throws ResponseException {
        return authDataAccess.loginUser(user);
    }



}
*/
