package dataAccess;

import exception.ResponseException;
import model.GameData;
import requests.JoinGameRequest;

public interface GameDataAccess {

    void clear() throws ResponseException;

    Object createGame(GameData game) throws ResponseException;

    Object listGames();

    Object joinGame(JoinGameRequest request) throws ResponseException;

    boolean gameExists(Integer gameID);
}
