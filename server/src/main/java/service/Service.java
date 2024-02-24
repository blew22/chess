package service;

import dataAccess.DataAccess;
import dataAccess.MemoryDataAccess;
import user.User;
public class Service {

    private final DataAccess dataAccess = new MemoryDataAccess();

    public Service() {
    }

    public void clear(){
        dataAccess.clear();
    }

    public Object registerUser(User user){
        if(dataAccess.userExists(user)) {
            return -1;
            //error
        } else {
            //create user, return auth
            return dataAccess.registerUser(user);
        }
    };

}
