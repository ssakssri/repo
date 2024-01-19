package com.ssakssri.api.connectivity;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssakssri.api.connectivity.http.InvalidResponseException;

/**
 * Servlet implementation class ODataProxyServlet
 */
// @WebServlet("/ProxyServlet")
public class ODataProxyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ODataProxyServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String uriQuery = request.getRequestURI()
					+ (request.getQueryString() == null ? "" : "?" + request.getQueryString());
			System.out.println(uriQuery);

			uriQuery = uriQuery.replaceAll("/sfsf-ext", "");

			SFODataAPIConnector sfOdataApi = SFODataAPIConnector.getInstance();
			String resJson = sfOdataApi.getJsonResponse(uriQuery);
			System.out.println(resJson);

			response.setHeader("Access-Control-Allow-Origin", "*");
			// response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT,
			// DELETE, HEAD");
			response.setHeader("Access-Control-Allow-Credentials", "true");

			response.setContentType("application/json;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(resJson);
			out.flush();
		} catch (InvalidResponseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
