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
}
