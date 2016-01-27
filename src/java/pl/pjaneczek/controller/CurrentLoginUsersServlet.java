/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pjaneczek.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import pl.pjaneczek.entity.User;

/**
 *
 * @author root
 */
@WebServlet(name = "CurrentLoginUsersServlet", urlPatterns = {"/CurrentLoginUsersServlet"})
public class CurrentLoginUsersServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Configuration configuration = new Configuration();
        SessionFactory sessionFactory = configuration.configure().buildSessionFactory();
        Session ses = sessionFactory.openSession();
        Transaction transaction = ses.beginTransaction();
        Query query = ses.createQuery("from User as user where user.isLogin = 1");
        List<User> userList = query.list();

//        JsonArray userArray = new JsonArray();
//        
// 
//        for (int i = 0; i < userList.size(); i++) {
//            JsonObject userObj = new JsonObject();
//            
//            userObj.addProperty("firstName" + i, userList.get(i).getFirstName());
//            userObj.addProperty("lastName" + i, userList.get(i).getLastName());
//            
//            userArray.add(userObj);
//
//        }
        
//        Gson gson = new GsonBuilder().setPrettyPrinting()
//                .create();
//        String json = gson.toJson(userArray);
//        response.getWriter().write(json);

        String json = new Gson().toJson(userList);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

}
