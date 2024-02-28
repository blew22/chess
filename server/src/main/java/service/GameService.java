package service;

import exception.ResponseException;
import model.GameData;
import requests.CreateGameRequest;
import requests.JoinGameRequest;

public class GameService extends Service {

    public Object createGame(CreateGameRequest gameRequest) throws ResponseException {
        checkAuthorization(gameRequest.authToken());
        GameData gameData = new GameData(gameRequest.gameName());
        return Service.gameDataAccess.createGame(gameData);
    }

    public Object listGames(String authToken) throws ResponseException {
        checkAuthorization(authToken);
        return Service.gameDataAccess.listGames();
    }

    public Object joinGame(JoinGameRequest joinRequest) throws ResponseException {
        checkAuthorization(joinRequest.authToken());
        if (!Service.gameDataAccess.gameExists(joinRequest.gameID())) {
            throw new ResponseException(400, "Error: bad game");
        } else {
            return Service.gameDataAccess.joinGame(joinRequest);
        }
    }

}
