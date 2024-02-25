package dataAccess;

import exception.ResponseException;
import model.User;

public interface DataAccess {

    public void clear();

    public Object registerUser(User user) throws ResponseException;

    public boolean userExists(User user);

}
