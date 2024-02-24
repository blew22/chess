package dataAccess;

import user.User;

public interface DataAccess {

    public void clear();

    public Object registerUser(User user);

    public boolean userExists(User user);

}
