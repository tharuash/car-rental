package com.b127.rental.servlets;

import com.b127.rental.entity.Vehicle;
import com.b127.rental.services.UserDashboardService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/user")
public class UserDashboardServlet extends HttpServlet {

    private UserDashboardService userDashboardService;

    public UserDashboardServlet(){
        userDashboardService = new UserDashboardService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("vehicles", userDashboardService.getAllVehicles());
        getServletContext().getRequestDispatcher("/user-dashboard.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Vehicle> filteredVehicles = userDashboardService.searchVehicles(LocalDate.parse(req.getParameter("from")),
                LocalDate.parse(req.getParameter("to")),
                Integer.parseInt(req.getParameter("capacity")));

        req.setAttribute("vehicles", filteredVehicles);
        getServletContext().getRequestDispatcher("/user-dashboard.jsp").forward(req, resp);
    }
}
