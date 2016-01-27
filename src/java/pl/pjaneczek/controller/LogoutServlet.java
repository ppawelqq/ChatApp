/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pjaneczek.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import pl.pjaneczek.entity.User;

/**
 *
 * @author root
 */
@WebServlet(name = "LogoutServlet", urlPatterns = {"/LogoutServlet"})
public class LogoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PJChatAppPU");
        EntityManager em = emf.createEntityManager();

        // TODO znalezc zalogowanego i go wylogowac
        User user = em.find(User.class, 1);

        em.getTransaction().begin();

        user.setIsLogin(Boolean.FALSE);

        em.flush();
        em.getTransaction().commit();

        em.close();
        emf.close();

        RequestDispatcher rd = request.getRequestDispatcher("index.xhtml");
        rd.forward(request, response);

        out.close();
    }

}
