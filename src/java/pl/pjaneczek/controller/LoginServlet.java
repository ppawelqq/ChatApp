/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pjaneczek.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
//import javax.persistence.Query;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.ehcache.hibernate.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
//import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import pl.pjaneczek.controller.LoginDao;
import pl.pjaneczek.entity.User;

public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String n = request.getParameter("username");
        String p = request.getParameter("userpass");
        String pin = request.getParameter("pin");

        HttpSession session = request.getSession(true);
        if (session != null) {
            session.setAttribute("name", n);
            session.setAttribute("id", pin);
        }

        if (LoginDao.validate(n, p)) {

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("PJChatAppPU");
            EntityManager em = emf.createEntityManager();

            User user = em.find(User.class, Integer.parseInt(pin));

            em.getTransaction().begin();

            user.setIsLogin(Boolean.TRUE);

            em.flush();
            em.getTransaction().commit();

            em.close();
            emf.close();

            RequestDispatcher rd = request.getRequestDispatcher("welcome.html");
            rd.forward(request, response);
        } else {
            out.print("<p style=\"color:red\">Niepoprawna nazwa lub haslo</p>");
            RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
            rd.include(request, response);
        }

        out.close();
    }
}
