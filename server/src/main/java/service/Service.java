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

}
