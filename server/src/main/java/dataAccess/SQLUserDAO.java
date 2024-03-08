package dataAccess;

import com.google.gson.Gson;
import exception.ResponseException;
import model.User;
import java.sql.*;

public class SQLUserDAO implements UserDataAccess {

    private static final AuthDataAccess authDataAccess;

    static {
        try {
            authDataAccess = new SQLAuthDAO();
        } catch (ResponseException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public SQLUserDAO() throws ResponseException, DataAccessException {
        configureDatabase();
    }

    @Override
    public void clear() throws ResponseException {
        try(Connection conn = DatabaseManager.getConnection()) {
            try (var statement = conn.prepareStatement("TRUNCATE users")) {
                statement.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            throw new ResponseException(500, e.getMessage());
        }
    }

    @Override
    public Object registerUser(User user) throws ResponseException{
        String newUser = new Gson().toJson(user);
        try (Connection conn = DatabaseManager.getConnection()){
            try (var statement = conn.prepareStatement("INSERT INTO users (username, password, json) VALUES (?,?,?)")){
                statement.setString(1, user.username());
                statement.setString(2, user.password());
                statement.setString(3, newUser);
                statement.executeUpdate();
                return loginUser(user);
            }
        } catch (SQLException | DataAccessException e) {
            throw new ResponseException(500, e.getMessage());
        }
    }

    @Override
    public boolean userExists(User user) throws ResponseException {
        try(Connection conn = DatabaseManager.getConnection()) {
            try (var statement = conn.prepareStatement("SELECT username FROM users WHERE username =? AND password=?")) {
                statement.setString(1, user.username());
                statement.setString(2, user.password());
                try (var rs = statement.executeQuery()){
                    return rs.next();
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new ResponseException(500, e.getMessage());
        }
    }

    private Object loginUser(User user) throws ResponseException {
        return authDataAccess.loginUser(user);
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  users (
              `username` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`username`)
            );
            """
    };


    private void configureDatabase() throws ResponseException, DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new ResponseException(500, String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }
}
