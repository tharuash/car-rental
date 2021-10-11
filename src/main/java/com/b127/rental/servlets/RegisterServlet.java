package com.b127.rental.servlets;

import com.b127.rental.entity.User;
import com.b127.rental.services.AuthService;
import com.b127.rental.util.UserRoles;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private AuthService authService;

    public RegisterServlet(){
        this.authService = new AuthService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("error") != null){
            req.setAttribute("error" , "Invalid email or password");
        }
        getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User(
                0,
                req.getParameter("name"),
                req.getParameter("email"),
                req.getParameter("mobile"),
                req.getParameter("password"),
                UserRoles.USER
        );

        if(authService.register(user)) {
            resp.sendRedirect("login");
        } else {
            resp.sendRedirect("register?error=email_already_used");
        }
    }
}
