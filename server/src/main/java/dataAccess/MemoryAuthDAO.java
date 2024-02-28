package dataAccess;

import model.AuthData;
import model.User;
import responses.LoginResponse;

import java.util.HashMap;

public class MemoryAuthDAO implements AuthDataAccess{

    final public static HashMap<String, String> authorizations = new HashMap<>();

    public void clear() {
        authorizations.clear();
    }

    public String getUsername(String authToken){
        return authorizations.get(authToken);
    }

    public Object loginUser(User user) {
        AuthData authData = new AuthData(user.username());
        authorizations.put(authData.authToken(), authData.username());
        return new LoginResponse(authData);
    }

    public void logout(String authToken) {
        authorizations.remove(authToken);
    }

    public boolean isLoggedIn(String authToken) {
        return authorizations.containsKey(authToken);
    }
}
