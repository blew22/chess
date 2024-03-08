package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import exception.ResponseException;
import model.GameData;
import requests.JoinGameRequest;
import responses.CreateGameResponse;
import responses.JoinGameResponse;
import responses.ListGamesResponse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLGameDAO implements GameDataAccess{

    static AuthDataAccess authDataAccess = null;

    public SQLGameDAO() throws ResponseException {
        try{
            configureDatabase();
            authDataAccess = new SQLAuthDAO();
        } catch (ResponseException | DataAccessException e) {
            throw new ResponseException(500, e.getMessage());
        }
    }


    @Override
    public void clear() throws ResponseException {
        try(Connection conn = DatabaseManager.getConnection()) {
            try (var statement = conn.prepareStatement("TRUNCATE games")) {
                statement.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            throw new ResponseException(500, e.getMessage());
        }
    }

    @Override
    public Object createGame(GameData game) throws ResponseException {
        try(Connection conn = DatabaseManager.getConnection()){
            try(var statement = conn.prepareStatement("INSERT INTO games (gameID, whiteUsername, blackUsername, gameName, chessJson, dataJson) VALUES(?,?,?,?,?,?)")){
                statement.setInt(1, game.gameID());
                statement.setString(2, game.whiteUsername());
                statement.setString(3, game.blackUsername());
                statement.setString(4, game.gameName());
                statement.setString(5, new Gson().toJson(game.chessGame()));
                statement.setString(6, new Gson().toJson(game));
                statement.executeUpdate();
                return new CreateGameResponse(game.gameID());
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object listGames() {
        try(Connection conn = DatabaseManager.getConnection()){
            try(var statement = conn.prepareStatement("SELECT dataJson FROM games")){
                try(var rs = statement.executeQuery()){
                    List<GameData> games = new ArrayList<>();
                    while (rs.next()) {
                        String dataJson = rs.getString("dataJson");
                        GameData gameData = new Gson().fromJson(dataJson, GameData.class);
                        games.add(gameData);
                    }
                    return new ListGamesResponse(games.toArray(new GameData[0]));
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object joinGame(JoinGameRequest request) throws ResponseException {
        try(Connection conn = DatabaseManager.getConnection()) {
            String whiteUsername;
            String blackUsername;
            GameData gameData;
//update data json when joining
            try (var statement = conn.prepareStatement("SELECT whiteUsername, blackUsername, dataJson FROM games WHERE gameID =?")) {
                statement.setInt(1, request.gameID());
                try (var rs = statement.executeQuery()) {
                    if (rs.next()) {
                        whiteUsername = rs.getString("whiteUsername");
                        blackUsername = rs.getString("blackUsername");
                        gameData = new Gson().fromJson(rs.getString("dataJson"), GameData.class);
                    } else {
                        throw new DataAccessException("game not found");
                    }
                }
            }
            String username = authDataAccess.getUsername(request.authToken());

            if (request.playerColor() == ChessGame.TeamColor.WHITE && whiteUsername == null) {
                gameData = gameData.setWhiteUsername(username);
                try(var statement = conn.prepareStatement("UPDATE games SET whiteUsername = ?, dataJson = ? WHERE gameID=?")){
                    statement.setString(1, username);
                    statement.setString(2, new Gson().toJson(gameData));
                    statement.setInt(3, request.gameID());
                    statement.executeUpdate();
                }

                return new JoinGameResponse();

            } else if (request.playerColor() == ChessGame.TeamColor.BLACK && blackUsername == null) {
                gameData = gameData.setBlackUsername(username);
                try(var statement = conn.prepareStatement("UPDATE games SET blackUsername = ?, dataJson = ? WHERE gameID=?")){
                    statement.setString(1, username);
                    statement.setString(2, new Gson().toJson(gameData));
                    statement.setInt(3, request.gameID());
                    statement.executeUpdate();
                }
                return new JoinGameResponse();

            } else if (request.playerColor() == null) {
                return new JoinGameResponse();
            } else {
                throw new ResponseException(403, "Error: bad color");
            }
        } catch (SQLException | DataAccessException e) {
            throw new ResponseException(500, e.getMessage());
        }
    }

    @Override
    public boolean gameExists(Integer gameID) {
        try(Connection conn = DatabaseManager.getConnection()){
            try(var statement = conn.prepareStatement("SELECT gameID FROM games WHERE gameID=?")){
                statement.setInt(1, gameID);
                try(var rs = statement.executeQuery()){
                    return rs.next();
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  games (
              `gameID` int NOT NULL,
              `whiteUsername` varChar(64),
              `blackUsername` varChar(64),
              `gameName` varChar(64),
              `chessJson` TEXT DEFAULT NULL,
              `dataJson` TEXT DEFAULT NULL,
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
    }

}
