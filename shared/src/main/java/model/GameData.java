package model;

import chess.ChessGame;
import java.util.Random;

public record GameData(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame chessGame) {

    public GameData(String gameName) {
        this(new GameIDCreator().createGameID(), null, null, gameName, new ChessGame());
    }

    public GameData setWhiteUsername(String username){
        return new GameData(this.gameID, username, this.blackUsername, this.gameName, this.chessGame);
    }

    public GameData setBlackUsername(String username){
        return new GameData(this.gameID, this.whiteUsername, username, this.gameName, this.chessGame);
    }

    @Override
    public String toString() {
        return "GameData{" +
                "whiteUsername='" + whiteUsername + '\'' +
                ", blackUsername='" + blackUsername + '\'' +
                ", gameName='" + gameName + '\'' +
                '}';
    }
}

class GameIDCreator {

    public int createGameID() {
        return new Random().nextInt(9000) + 1000;
    }

}
