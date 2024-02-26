package model;

import chess.ChessGame;
import java.util.Random;

public record GameData(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame chessGame) {

    public GameData(String gameName) {
        this(new GameHelper().createGameID(), null, null, gameName, new ChessGame());
    }
}

class GameHelper {

    public int createGameID() {
        return new Random().nextInt(9000) + 1000;
    }
}
