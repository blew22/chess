package dataAccess;

import exception.ResponseException;
import model.AuthData;
import model.User;
import responses.LoginResponse;

import javax.xml.crypto.Data;
import java.sql.*;

public class SQLAuthDAO implements AuthDataAccess{

    public SQLAuthDAO() throws ResponseException, DataAccessException {
        try {
            configureDatabase();
        } catch (ResponseException | DataAccessException e) {
            throw new ResponseException(500, e.getMessage());
        }
    }

    @Override
    public void clear() throws ResponseException {
        try(Connection conn = DatabaseManager.getConnection()){
            try(var statement = conn.prepareStatement("TRUNCATE auths")){
                statement.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            throw new ResponseException(500, e.getMessage());
        }
    }

    @Override
    public String getUsername(String authToken) {
        return null;
    }

    @Override
    public Object loginUser(User user) throws ResponseException {
        AuthData authData = new AuthData(user.username());
        try(Connection conn = DatabaseManager.getConnection()){
            try(var statement = conn.prepareStatement("INSERT INTO auths (authToken, username) VALUES(?,?)")){
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
    public boolean isLoggedIn(String authToken) {
        return false;
    }

    @Override
    public void logout(String authToken) {

    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  auths (
              `authToken` varchar(99) NOT NULL,
              `username` TEXT DEFAULT NOT NULL,
              PRIMARY KEY (`authToken`),
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
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
