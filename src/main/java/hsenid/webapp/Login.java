/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hsenid.webapp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hsenid.
 *
 * @author hsenid
 */
public class Login extends HttpServlet {
    User user;
    DBCon dbc;
    String host = "jdbc:mysql://localhost:3306/";
    String database = "userdata";
    String dbuser = "root";
    String dbpass = "test123";
    static String error = "User name and password does not match!";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        user = new User(req.getParameter("uname"), req.getParameter("pass"));
        
        /*boolean status=Validate(user);
        if(status){
            resp.sendRedirect("success.jsp");
        }else{
            error = "User name and password does not match!";
            req.setAttribute("error_msg", "User name and password does not match!");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
            rd.forward(req, resp);
        }*/

        try {
            boolean status = ValidateByDB(user);
            if (status) {
                resp.sendRedirect("success.jsp");
            } else {
                error = "User name and password does not match!";
                req.setAttribute("error_msg", error);
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
                rd.forward(req, resp);
            }
        } catch (Exception e) {
            error = "Something bad happened. Try again later.";
        }
    }

    /**
     * @param user Passing a user to validate username and password
     * @return status
     * Returns whether user passed the validation or not
     */
    public boolean Validate(User user) {
        boolean status = false;
        status = user.getUsername().equals("test") && user.getPassword().equals("123");
        return status;
    }

    /**
     * @param user Passing a user to validate username and password
     * @return status
     * Returns whether user passed the validation or not
     */
    public boolean ValidateByDB(User user) throws Exception {
        dbc = new DBCon();
        boolean status = false;
        Statement statement = null;
        ResultSet result = null;
        try {
            Connection connection = dbc.CreateConnection(host, database, dbuser, dbpass);
            statement = connection.createStatement();
            String query = "SELECT Name FROM user_cred WHERE Name=\"" + user.getUsername() + "\" && pass=md5(\"" + user.getPassword() + "\");";
            result = statement.executeQuery(query);
            status = result.first();
        } catch (Exception e) {
            error = "Something bad happened. Try again later.";
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (result != null) {
                result.close();
            }
        }
        return status;
    }
}
