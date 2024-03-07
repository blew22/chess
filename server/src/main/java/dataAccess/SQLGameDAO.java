package dataAccess;

import exception.ResponseException;
import model.GameData;
import requests.JoinGameRequest;
import java.sql.*;

public class SQLGameDAO implements GameDataAccess{
    @Override
    public void clear() {

    }

    @Override
    public Object createGame(GameData game) {
        return null;
    }

    @Override
    public Object listGames() {
        return null;
    }

    @Override
    public Object joinGame(JoinGameRequest request) throws ResponseException {
        return null;
    }

    @Override
    public boolean gameExists(Integer gameID) {
        return false;
    }

    /*private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  games (
              `gameID` int NOT NULL,
              `json` TEXT DEFAULT NULL,
              PRIMARY KEY (`gameID`)
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
    }*/

}
