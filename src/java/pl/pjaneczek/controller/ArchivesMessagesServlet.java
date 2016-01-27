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
import java.util.List;
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
import pl.pjaneczek.entity.Message;
import pl.pjaneczek.entity.User;

/**
 *
 * @author root
 */
@WebServlet(name = "ArchivesMessagesServlet", urlPatterns = {"/ArchivesMessagesServlet"})
public class ArchivesMessagesServlet extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");

        Configuration configuration = new Configuration();
        SessionFactory sessionFactory = configuration.configure().buildSessionFactory();
        Session ses = sessionFactory.openSession();
        Transaction transaction = ses.beginTransaction();
        Query query = ses.createQuery("from Message");
        List<Message> messagesList = query.list();

//        JsonArray messagesArray = new JsonArray();
//
//        for (int i = 0; i < messagesList.size(); i++) {
//            JsonObject messageObj = new JsonObject();
//
//            messageObj.addProperty("userId" + i, messagesList.get(i).getUserId());
//            messageObj.addProperty("message" + i, messagesList.get(i).getContent());
//
//            messagesArray.add(messageObj);
//
//        }

//        Gson gson = new GsonBuilder().setPrettyPrinting()
//                .create();
//        String json = gson.toJson(messagesArray);
//        response.getWriter().write(json);
        String json = new Gson().toJson(messagesList);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }

}
