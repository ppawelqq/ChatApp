/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pjaneczek.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import pl.pjaneczek.entity.User;

/**
 *
 * @author root
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String n = request.getParameter("username");
        String p = request.getParameter("userpass");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String mail = request.getParameter("mail");

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute("name", n);
        }

        Random ran = new Random();
        int x = ran.nextInt(128) + 5;
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PJChatAppPU");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        User user = new User();
        user.setId(x);
        user.setLogin(n);
        user.setPassword(p);
        user.setFirstName(name);
        user.setLastName(surname);
        user.setMail(mail);

        em.persist(user);

        em.flush();
        em.getTransaction().commit();

        em.close();
        emf.close();

        RequestDispatcher rd = request.getRequestDispatcher("welcome.html");
        rd.forward(request, response);

        out.close();
    }
}
