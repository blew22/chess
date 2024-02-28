package dataAccess;

import model.User;

public interface AuthDataAccess {

    void clear();

    String getUsername(String authToken);

    Object loginUser(User user);

    boolean isLoggedIn(String authToken);

    void logout(String authToken);

}
