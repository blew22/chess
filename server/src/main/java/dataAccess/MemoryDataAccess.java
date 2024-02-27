package dataAccess;

import java.util.ArrayList;
import java.util.HashMap;

import exception.ResponseException;
import model.AuthData;
import model.GameData;
import requests.JoinGameRequest;
import responses.CreateGameResponse;
import responses.JoinGameResponse;
import responses.ListGamesResponse;
import responses.RegisterResponse;
import model.User;
import chess.ChessGame;

public class MemoryDataAccess implements DataAccess {

    final private ArrayList<User> users = new ArrayList<>();

    final private HashMap<String, String> authorizations = new HashMap<>();

    final private HashMap<Integer, GameData> games = new HashMap<>();

    public void clear() {
        users.clear();
        games.clear();
        authorizations.clear();
    }

    public Object registerUser(User user) {
        users.add(user);
        return loginUser(user);
    }

    public boolean userExists(User user) {
        return users.contains(user);
    }

    @Override
    public Object loginUser(User user) {
        AuthData newAuth = new AuthData(user.username());
        authorizations.put(newAuth.authToken(), user.username());
        return new RegisterResponse(newAuth);
    }

    public void logoutUser(String authToken){
        authorizations.remove(authToken);
    }

    public boolean isLoggedIn(String authToken){
        return authorizations.containsKey(authToken);
    }

    public Object createGame(GameData game){
        games.put(game.gameID(), game);
        return new CreateGameResponse(game.gameID());
    }

    public Object listGames(){
        GameData[] gameList = games.values().toArray(new GameData[0]);
        return new ListGamesResponse(gameList);
    }

    public Object joinGame(JoinGameRequest request) throws ResponseException{
        GameData gameData = games.get(request.gameID());
        if(request.playerColor() == ChessGame.TeamColor.WHITE && gameData.whiteUsername() == null){
            games.replace(request.gameID(), gameData.setWhiteUsername(authorizations.get(request.authToken())));
            return new JoinGameResponse();
        } else if(request.playerColor() == ChessGame.TeamColor.BLACK && gameData.blackUsername() == null) {
            games.replace(request.gameID(), gameData.setBlackUsername(authorizations.get(request.authToken())));
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