package com.ssakssri.scp.myhrext.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssakssri.scp.myhrext.bean.Employee;
import com.ssakssri.scp.myhrext.util.DateUtils;
import com.ssakssri.scp.myhrext.ws.PersonalInfoWSClient;

/**
 * Servlet implementing
 */
public class EmployeeContactByWSServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeContactByWSServlet.class);

    @Override
    public void init() throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String pernr = request.getParameter("p_pernr");
            if (pernr == null)
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parameter is not sufficent!!!");

            PersonalInfoWSClient client = new PersonalInfoWSClient();
            client.init();

            Employee emp = client.getProfiles(pernr, DateUtils.getCurrentDate(), "3");

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
