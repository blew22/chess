package dataAccess;

import model.User;
import java.sql.*;

public class SQLAuthDAO implements AuthDataAccess{
    @Override
    public void clear() {

    }

    @Override
    public String getUsername(String authToken) {
        return null;
    }

    @Override
    public Object loginUser(User user) {
        return null;
    }

    @Override
    public boolean isLoggedIn(String authToken) {
        return false;
    }

    @Override
    public void logout(String authToken) {

    }
}
