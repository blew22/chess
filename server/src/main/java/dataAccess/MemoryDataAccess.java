package dataAccess;

import java.util.ArrayList;
import user.User;
import chess.ChessGame;

public class MemoryDataAccess implements DataAccess {

    final private ArrayList<User> users = new ArrayList<User>();

    final private ArrayList<ChessGame> games = new ArrayList<ChessGame>();

    public void clear(){
        users.clear();
        games.clear();
    }

    public int registerUser(){
        int authToken = 1234;
        return authToken;
    }

}
