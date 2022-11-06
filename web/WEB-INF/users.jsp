<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Managing User Application</title>
    </head>
    <body>
        <h1>Manage Users</h1>
        <p>        
            <c:if test="${message eq 'update'}">User information updated</c:if>
            <c:if test="${message eq 'empty'}">No users found. Please add a user</c:if>
            <c:if test="${message eq 'error'}">Sorry, something went wrong.</c:if>
            </p>

        <c:if test="${message ne 'empty'}">
            <table border="1" cellpadding="5">
                <tr>
                    <th>Email</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Role</th>
                    <th></th>
                    <th></th>
                </tr>
                <c:forEach items="${users}" var="user">
                    <tr>
                        <td><c:out value="${user.email}"  /></td>
                        <td><c:out value="${user.firstName}"  /></td>
                        <td><c:out value="${user.lastName}"  /></td>
                        <td><c:out value="${user.role.roleName}"  /></td>
                        <td><input type="hidden" name="action" value="edit">
                            <a href="<c:url value='/users?action=edit&amp;'>
                                   <c:param name='email' value='${user.email}'/>  
                               </c:url>">Edit
                            </a></td> 
                        <td><input type="hidden" name="action" value="delete">
                            <a href="<c:url value='/users?action=delete&amp;'> 
                                   <c:param name='email' value='${user.email}'/> 
                               </c:url>"> Delete</a></td>     
                    </tr>
                </c:forEach>
            </table>
        </c:if>

        <c:if test="${selectedUser eq null}">
            <form action="users" method="post">
                <h2>Add User</h2>   
                Email: <input type="text" value="${email}" name="email">  <br>
                First name: <input type="text" value="${first}" name="first"> <br>
                Last name: <input type="text" value="${last}" name="last"> <br>
                Password: <input type="password" value="${pw}" name="pw"> <br>
                Role: <select name="role">
                    <option value="1">system admin</option>
                    <option value="2">regular user</option>
                </select>
                <br>
                <input type="submit" value="Add user">
                <input type="hidden" name="action" value="add">
            </form>
        </c:if>

        <c:if test="${(message eq 'edit')}">
            <h2>Edit User</h2>
            <form action="users" method="post">
                Email: <c:out value="${email}"/> <input type="hidden" name="email" value="${email}">    <br>          
                First name: <input type="text" value="${selectedUser.firstName}" name="first"> <br>
                Last name: <input type="text" value="${selectedUser.lastName}" name="last"> <br>
                Password: <input type="password" value="" name="pw"> <br>    
                Role: <select name="role">                             
                    <c:choose>
                        <c:when test="${user_role_id == 1}">
                            <option>system admin</option>
                            <option>regular user</option>                              
                        </c:when>
                        <c:otherwise>
                            <option>regular user</option>
                            <option>system admin</option>
                        </c:otherwise>
                    </c:choose>
                </select>
                <br>
                <input type="submit" value="Update">
                <input type="hidden" name="action" value="update">
                <a href="/"><input type="button" value="Cancel">
                    <input type="hidden" name="action" value="cancel"></a> 
                </c:if>
            <p>
                ${mes}
            </p> 
    </body>
</html>
