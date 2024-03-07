/*
package dataAccess;

import chess.ChessGame;
import exception.ResponseException;
import model.GameData;
import requests.JoinGameRequest;
import responses.CreateGameResponse;
import responses.JoinGameResponse;
import responses.ListGamesResponse;

import java.util.HashMap;

public class MemoryGameDAO implements GameDataAccess {

    final private static HashMap<Integer, GameData> games = new HashMap<>();

    final private static AuthDataAccess authDataAccess = new MemoryAuthDAO();

    public void clear(){
        games.clear();
    }

    public Object createGame(GameData game){
        games.put(game.gameID(), game);
        return new CreateGameResponse(game.gameID());
    }

    public Object listGames(){
        GameData[] gameList = games.values().toArray(new GameData[0]);
        return new ListGamesResponse(gameList);
    }

    public Object joinGame(JoinGameRequest request) throws ResponseException {
        GameData gameData = games.get(request.gameID());
        if(request.playerColor() == ChessGame.TeamColor.WHITE && gameData.whiteUsername() == null){
            games.replace(request.gameID(), gameData.setWhiteUsername(authDataAccess.getUsername(request.authToken())));
            return new JoinGameResponse();

        } else if(request.playerColor() == ChessGame.TeamColor.BLACK && gameData.blackUsername() == null) {
            games.replace(request.gameID(), gameData.setBlackUsername(authDataAccess.getUsername(request.authToken())));
            return new JoinGameResponse();

        } else if(request.playerColor() == null){
            return new JoinGameResponse();
        } else {
            throw new ResponseException(403, "Error: bad color");
        }
    }

    public boolean gameExists(Integer gameID){
        return games.containsKey(gameID);
    }
}
*/
