package service;

import dataAccess.DataAccess;
import dataAccess.MemoryDataAccess;
import exception.ResponseException;
import model.User;

public class Service {

    private final DataAccess dataAccess = new MemoryDataAccess();

    public Service() {
    }

    public void clear() {
        dataAccess.clear();
    }

    public Object registerUser(User user) throws ResponseException {
        if (dataAccess.userExists(user)) {
//            ErrorResponse errorResponse = new ErrorResponse("already taken");
//            return errorResponse;
            throw new ResponseException(403, "Error: already taken");
            //error
        } else {
            //create user, return auth
            return dataAccess.registerUser(user);
        }
    }

    ;

    public Object loginUser(User user) throws ResponseException {
        if (dataAccess.userExists(user)) {
            return dataAccess.loginUser(user);
        } else {
            throw new ResponseException(401, "Error: unauthorized");

        }

    }
}
