package com.b127.rental.servlets;

import com.b127.rental.entity.User;
import com.b127.rental.services.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private LoginService loginService;

    public LoginServlet(){
        loginService = new LoginService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("error") != null){
            req.setAttribute("error" , "Invalid email or password");
        }
        getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<User> incomingUser = loginService.loginUser(req.getParameter("email"), req.getParameter("password"));

        if(incomingUser.isEmpty()) {
            resp.sendRedirect("login?error=true");
        } else {
            HttpSession session = req.getSession();
            session.setAttribute("id" , incomingUser.get().getId());
            session.setAttribute("email" , incomingUser.get().getEmail());
            session.setAttribute("role", incomingUser.get().getRole());

            resp.sendRedirect(setRedirectUriAccordingToTheRole(incomingUser.get().getRole()));
        }
    }

    private String setRedirectUriAccordingToTheRole(String role) {
        String redirectUri = "";

        switch (role) {
            case "USER" :
                redirectUri = "user";
                break;

            case "ADMIN" :
                redirectUri = "admin";
                break;

            case "TO" :
                redirectUri = "to";
                break;

            default:
                redirectUri = "error";
        }

        return redirectUri;
    }
}
