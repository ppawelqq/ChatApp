/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pjaneczek.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
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
import javax.servlet.http.HttpSession;
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
@WebServlet(name = "SendMessageServlet", urlPatterns = {"/SendMessageServlet"})
public class SendMessageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String contentMessage = request.getParameter("message");

        HttpSession session = request.getSession(false);

        String currentUserId = (String) session.getAttribute("id");
        String currentUserName = (String) session.getAttribute("name");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PJChatAppPU");
        EntityManager em = emf.createEntityManager();

        Random ran = new Random();
        int x = ran.nextInt(12) + 5;
        em.getTransaction().begin();

        Message message = new Message();
       
        message.setContent(contentMessage);
        message.setUserId(currentUserId);
        message.setUserName(currentUserName);

        em.persist(message);
        em.getTransaction().commit();

        em.close();
        emf.close();

        RequestDispatcher rd = request.getRequestDispatcher("welcome.html");
        rd.forward(request, response);

        out.close();
    }

}
