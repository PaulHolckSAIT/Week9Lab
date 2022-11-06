package servlets;

import java.io.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import services.*;
import java.util.logging.*;
import models.*;

public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        //servlet-service-userdb
        UserService us = new UserService();
        RoleService rs = new RoleService();
        String action = request.getParameter("action");
        int user_role_id = 0;

        try {
            List<User> users = us.getAll();
            List<Role> roles = rs.getAll();
            request.setAttribute("users", users);
            request.setAttribute("roles", roles);
            if (users.isEmpty()) {
                request.setAttribute("message", "empty");
            }
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "error");
        }
        if (action != null) {
            if (action.equals("edit")) {
                try {
                    String email = request.getParameter("email");
                    User user = us.get(email);
                    user_role_id = user.getRole().getRoleId();
                    request.setAttribute("email", email);
                    request.setAttribute("selectedUser", user);
                    request.setAttribute("user_role_id", user_role_id);
                    request.setAttribute("message", "edit");

                } catch (Exception ex) {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (action.equals("delete")) {
                try {
                    String email = request.getParameter("email");
                    us.delete(email);
                    request.setAttribute("message", "delete");
                    response.sendRedirect("/");
                    return;
                } catch (Exception ex) {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        HttpSession session = request.getSession();
        Role role = null;
        User user = null;
        UserService us = new UserService();
        RoleService rs = new RoleService();

        String email = request.getParameter("email");
        String first = request.getParameter("first");
        String last = request.getParameter("last");
        String pw = request.getParameter("pw");
        String role_name = request.getParameter("role");
        int id = 0;

        if (role_name.equals("system admin")) {
            id = 1;
        } else {
            id = 2;
        }       
        String action = request.getParameter("action");

        try {
            List<User> users = us.getAll();
            request.setAttribute("users", users);
 us.get(email).getRole().setRoleId(id);
 us.get(email).getRole().setRoleName(role_name);
 
            if (email == null || email.equals("") || first == null || first.equals("") || last == null || last.equals("")
                    || pw == null || pw.equals("")) {
                request.setAttribute("mes", "All fields are required");
                if (users.isEmpty()) {
                    request.setAttribute("message", "empty");
                }
                getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
                return;
            }

            if (action != null) {
                switch (action) {
                    case "add":
                        //check the email                     
                        for (int i = 0; i < users.size(); i++) {
                            if (email.equals(users.get(i).getEmail())) {
                                request.setAttribute("mes", "Error. Email is already taken");
                                getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
                            }
                        }
                        us.insert(email, first, last, pw, us.get(email).getRole());
//                        rs.get(user_role_id)
                        request.setAttribute("message", "add");
                        break;
                    case "update":
                        us.update(email, first, last, pw, us.get(email).getRole());
                        request.setAttribute("message", "update");
                        break;
                }
            }
        } catch (NullPointerException ex) {
            Logger.getLogger(UserServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "error");
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "error");
        }

        try {
            List<User> users = us.getAll();
            request.setAttribute("users", users);

        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "error");
        }
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }

}
