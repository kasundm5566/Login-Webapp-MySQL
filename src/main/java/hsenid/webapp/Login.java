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
 *
 * @author hsenid
 */
public class Login extends HttpServlet{

    User user;
    DBCon dbc;
    String host = "jdbc:mysql://localhost:3306/";
    String database = "userdata";
    String dbuser = "root";
    String dbpass = "test123";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        user = new User(req.getParameter("uname"), req.getParameter("pass"));
        
        /*boolean status=Validate(user);
        if(status){
            resp.sendRedirect("success.jsp");
        }else{
            req.setAttribute("error_msg", "User name and password does not match!");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
            rd.forward(req, resp);
        }*/

        boolean status = ValidateByDB(user);
        if (status) {
            resp.sendRedirect("success.jsp");
        } else {
            req.setAttribute("error_msg", "User name and password does not match!");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
            rd.forward(req, resp);
        }

    }
    
    /**
     * This method will returns true if the user name is 'test' and password is '123'.
     */
    public boolean Validate(User u) {
        boolean status = false;
        status = u.getUsername().equals("test") && u.getPassword().equals("123");
        return status;
    }
    
    /**
     * This method will initialize a database connection and then validate user name
     * and password using the database.
     */
    public boolean ValidateByDB(User u) {
        dbc = new DBCon();
        boolean status = false;
        try {
            Connection connection = dbc.CreateConnection(host, database, dbuser, dbpass);
            Statement statement = connection.createStatement();
            String query = "SELECT Name FROM user_cred WHERE Name=\"" + u.getUsername() + "\" && pass=md5(\"" + u.getPassword() + "\");";
            ResultSet result = statement.executeQuery(query);
            status = result.first();
        } catch (Exception e) {
            //s=e.toString();
        }
        return status;
    }
}
