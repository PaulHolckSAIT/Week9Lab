package services;

import dataaccess.RoleDB;
import java.util.*;
import models.Role;

public class RoleService {

    public Role get(int id) throws Exception {
        RoleDB roleDB = new RoleDB();
        Role role = roleDB.get(id);
        return role;
    }

    public List<Role> getAll() throws Exception {
        RoleDB roleDB = new RoleDB();
        List<Role> roles = roleDB.getAll();
        return roles;
    }

}
