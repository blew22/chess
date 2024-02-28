package dataAccess;

import exception.ResponseException;
import model.GameData;
import requests.JoinGameRequest;

public interface GameDataAccess {

    void clear();

    Object createGame(GameData game);

    Object listGames();

    Object joinGame(JoinGameRequest request) throws ResponseException;

    boolean gameExists(Integer gameID);
}
