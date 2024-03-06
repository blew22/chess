package dataAccess;

import exception.ResponseException;
import model.User;
import java.sql.*;

public class SQLUserDAO implements UserDataAccess {
    @Override
    public void clear() {

    }

    @Override
    public Object registerUser(User user) throws ResponseException {
        return null;
    }

    @Override
    public boolean userExists(User user) {
        return false;
    }
}
