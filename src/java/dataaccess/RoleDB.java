package dataaccess;

import java.sql.*;
import java.util.*;
import javax.persistence.EntityManager;
import models.Role;

public class RoleDB {
//returns list

    public List<Role> getAll() throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        try {
            List<Role> roles = em.createNamedQuery("Role.findAll", Role.class).getResultList();
            return roles;
        } finally {
            em.close();
        }

    }

    public Role get(int id) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try {
            Role role = em.find(Role.class, id);
            return role;
        } finally {
            em.close();
        }

    }

}
