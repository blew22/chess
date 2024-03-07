package dataAccess;

import exception.ResponseException;
import model.AuthData;
import model.User;
import responses.LoginResponse;

import javax.xml.crypto.Data;
import java.sql.*;

public class SQLAuthDAO implements AuthDataAccess {

    public SQLAuthDAO() throws ResponseException, DataAccessException {
        try {
            configureDatabase();
        } catch (ResponseException | DataAccessException e) {
            throw new ResponseException(500, e.getMessage());
        }
    }

    @Override
    public void clear() throws ResponseException {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (var statement = conn.prepareStatement("TRUNCATE auths")) {
                statement.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            throw new ResponseException(500, e.getMessage());
        }
    }

    @Override
    public String getUsername(String authToken) throws ResponseException {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (var statement = conn.prepareStatement("SELECT username FROM users WHERE authToken = ? ")) {
                statement.setString(1, "authToken");
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("username");
                    } else {
                        throw new ResponseException(400, "user not found");
                    }
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new ResponseException(500, e.getMessage());
        }
    }

    @Override
    public Object loginUser(User user) throws ResponseException {
        AuthData authData = new AuthData(user.username());
        try (Connection conn = DatabaseManager.getConnection()) {
            try (var statement = conn.prepareStatement("INSERT INTO auths (authToken, username) VALUES(?,?)")) {
                statement.setString(1, authData.authToken());
                statement.setString(2, user.username());
                statement.executeUpdate();
                return new LoginResponse(authData);
            }
        } catch (SQLException | DataAccessException e) {
            throw new ResponseException(500, e.getMessage());
        }
    }

    @Override
    public boolean isLoggedIn(String authToken) throws ResponseException {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (var statement = conn.prepareStatement("SELECT username FROM users WHERE authToken = ? ")) {
                statement.setString(1, "authToken");
                try (ResultSet rs = statement.executeQuery()) {
                    return rs.next();
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new ResponseException(500, e.getMessage());
        }
    }

    @Override
    public void logout(String authToken) throws ResponseException {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (var statement = conn.prepareStatement("DELETE user FROM auths WHERE authToken = ?")) {
                statement.setString(1, authToken);
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  auths (
              `authToken` varchar(99) NOT NULL,
              `username` TEXT NOT NULL,
              PRIMARY KEY (`authToken`)
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
