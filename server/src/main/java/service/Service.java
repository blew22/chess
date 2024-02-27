package service;

import chess.ChessGame;
import dataAccess.DataAccess;
import dataAccess.MemoryDataAccess;
import exception.ResponseException;
import model.AuthData;
import model.GameData;
import model.User;
import requests.CreateGameRequest;
import requests.JoinGameRequest;

import java.lang.module.ResolutionException;

public class Service {

    private final DataAccess dataAccess = new MemoryDataAccess();

    public Service() {
    }

    public void clear(String authToken) throws ResponseException {
        dataAccess.clear();
    }

    public Object registerUser(User user) throws ResponseException {
        if (dataAccess.userExists(user)) {
            throw new ResponseException(403, "Error: already taken");
        } else {
            return dataAccess.registerUser(user);
        }
    }

    public Object loginUser(User user) throws ResponseException {
        if (dataAccess.userExists(user)) {
            return dataAccess.loginUser(user);
        } else {
            throw new ResponseException(401, "Error: unauthorized");
        }
    }

    public boolean logoutUser(String authToken) {
        if (dataAccess.isLoggedIn(authToken)) {
            dataAccess.logoutUser(authToken);
            return true;
        } else {
            return false;
        }
    }

    public Object createGame(CreateGameRequest gameRequest) throws ResponseException {
        checkAuthorization(gameRequest.authToken());
        GameData gameData = new GameData(gameRequest.gameName());
        return dataAccess.createGame(gameData);
    }

    public Object listGames(String authToken) throws ResponseException {
        checkAuthorization(authToken);
        return dataAccess.listGames();

    }

    public Object joinGame(JoinGameRequest joinRequest) throws ResponseException {
        checkAuthorization(joinRequest.authToken());
        if (!dataAccess.gameExists(joinRequest.gameID())) {
            throw new ResponseException(400, "Error: bad game");
        } else {
            return dataAccess.joinGame(joinRequest);
        }
    }

    private void checkAuthorization(String authToken) throws ResponseException {
        if (!dataAccess.isLoggedIn(authToken)) {
            throw new ResponseException(401, "Error: not authorized");
        }
    }
}