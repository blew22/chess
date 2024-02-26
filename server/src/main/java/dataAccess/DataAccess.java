package dataAccess;

import exception.ResponseException;
import model.AuthData;
import model.GameData;
import model.User;

public interface DataAccess {

    public void clear();

    public Object registerUser(User user) throws ResponseException;

    public boolean userExists(User user);

    public Object loginUser(User user);

    public void logoutUser(String authToken);

    public boolean isLoggedIn(String authToken);

    public Object createGame(GameData game);

}
