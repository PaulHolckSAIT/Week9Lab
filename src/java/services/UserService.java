package services;

import dataaccess.RoleDB;
import dataaccess.UserDB;
import java.util.List;
import models.Role;
import models.User;

public class UserService {

    public User get(String email) throws Exception {
        //constructs userDB and get me that user based on email
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        return user;
    }

    public List<User> getAll() throws Exception {
        //get all users
        UserDB userDB = new UserDB();
        List<User> users = userDB.getAll();
        return users;
    }

    public void insert(String email, String firstName, String lastName, String password, Role role) throws Exception {
        User user = new User(email, firstName, lastName, password);
        RoleDB roleDB = new RoleDB();
        Role r = roleDB.get(role.getRoleId());
        user.setRole(r);
        UserDB userDB = new UserDB();
        userDB.insert(user);
    }

    //param from the servlet
    public void update(String email, String firstName, String lastName, String password, Role role) throws Exception {
       UserDB userDB = new UserDB();
       User user = userDB.get(email);
       user.setFirstName(firstName);
       user.setLastName(lastName);
       user.setPassword(password);
       user.setRole(role);
        userDB.update(user);
    }

    public void delete(String email) throws Exception {    
        UserDB userDB = new UserDB();
        User user = userDB.get(email);    
        userDB.delete(user);
    }
}
