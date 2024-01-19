package com.ssakssri.scp.myhrext.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssakssri.scp.myhrext.ad.ActiveDirectoryDao;
import com.ssakssri.scp.myhrext.bean.Employee;

import DSGAuthPkg.UserEntry;

/**
 * Servlet implementing
 */
public class EmployeeSearchByADServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeSearchByADServlet.class);

    @Override
    public void init() throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String userId = request.getParameter("p_userId");
            if (userId == null)
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parameter is not sufficent!!!");

            LOGGER.info("Input ID : " + userId);

            ActiveDirectoryDao client = new ActiveDirectoryDao();

            LOGGER.info("ID Validate : " + client.hasUser(userId));
            
            UserEntry user = client.getUser(userId);
            
            Employee emp = null;
            if (user != null) {
                emp = new Employee(user.GetValue("displayName"), user.GetValue("company"), user.GetValue("department"),
                        user.GetValue("ExADObjectDetailCategory"), user.GetValue("ExADObjectClass"), user.GetValue("ExADTitle"));
            } else {
                LOGGER.error("User not found!!");
            }
            
            ObjectMapper mapper = new ObjectMapper();
            response.setContentType("application/json");
            mapper.writeValue(response.getOutputStream(), emp);

        } catch (Exception e) {
            response.getWriter().println("Persistence operation failed with reason: " + e.getMessage());
            LOGGER.error("Persistence operation failed", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            doGet(request, response);
        } catch (Exception e) {
            response.getWriter().println("Persistence operation failed with reason: " + e.getMessage());
            LOGGER.error("Persistence operation failed", e);
        }
    }

}
