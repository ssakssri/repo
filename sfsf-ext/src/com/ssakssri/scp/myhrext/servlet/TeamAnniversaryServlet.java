package com.ssakssri.scp.myhrext.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ssakssri.scp.myhrext.ws.TeamAnniversaryWSClient;
import com.ssakssri.scp.myhrext.ws.TeamAnniversary_EHR_SOServiceStub.AnniversaryList_type0;
import com.ssakssri.scp.myhrext.ws.TeamAnniversary_EHR_SOServiceStub.MT_IF060_EHR_response;

/**
 * Servlet implementing
 */
public class TeamAnniversaryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamAnniversaryServlet.class);

    @Override
    public void init() throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            TeamAnniversaryWSClient client = new TeamAnniversaryWSClient();
            client.init();
            MT_IF060_EHR_response res = client.getAnniversary("1453983", "20180320", "3");

            AnniversaryList_type0[] anniversaryList = res.getMT_IF060_EHR_response().getAnniversaryList();
            System.out.println("TeamAnniversaryServlet.java ->>> anniversaryListLength : " + anniversaryList.length);
            
            response.setContentType("text/html; charset=utf-8");
            PrintWriter out = response.getWriter();

            for (AnniversaryList_type0 anniversary : anniversaryList) {
                out.println("<h1>" + anniversary.getEmpNm() + "</h1>");
                out.println("<h2>" + anniversary.getBirthType() + "/" + anniversary.getBirthYmd() + "</h2>");
            }

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
