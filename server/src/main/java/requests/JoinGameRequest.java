package requests;

import chess.ChessGame;

public record JoinGameRequest(String authToken, ChessGame.TeamColor playerColor, int gameID) {


    public JoinGameRequest setAuthToken(String authToken){
        return new JoinGameRequest(authToken, this.playerColor, this.gameID);
    }
}
