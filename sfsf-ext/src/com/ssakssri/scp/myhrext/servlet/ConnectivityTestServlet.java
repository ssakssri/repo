package com.ssakssri.scp.myhrext.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.cloud.account.TenantContext;
import com.sap.core.connectivity.api.configuration.ConnectivityConfiguration;
import com.sap.core.connectivity.api.configuration.DestinationConfiguration;

/**
 * Servlet class making http calls to specified http destinations. Destinations
 * are used in the following example connectivity scenarios:<br>
 * - Connecting to an outbound Internet resource using HTTP destinations<br>
 * - Connecting to an on-premise backend using on premise HTTP destinations,<br>
 * where the destinations have no authentication.<br>
 */
public class ConnectivityTestServlet extends HttpServlet {
    @Resource
    private TenantContext tenantContext;
    private static final long serialVersionUID = 1L;
    private static final int COPY_CONTENT_BUFFER_SIZE = 1024;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectivityTestServlet.class);
    private static final String ON_PREMISE_PROXY = "OnPremise";

    /** {@inheritDoc} */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpURLConnection urlConnection = null;
        String destinationName = request.getParameter("destname");
        // The default request to the Servlet will use outbound-internet-destination
        String reqXML = "<?xml version='1.0' encoding='utf-8'?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soapenv:Header/><soapenv:Body><ns1:MT_PersonalInfo_All_EHR xmlns:ns1=\"http://ghris.corp.doosan.com/ws\">"
                + "<PERNR>1453983</PERNR><DATE>20180223</DATE><SPRAS>3</SPRAS><DPERN>1453983</DPERN>"
                + "</ns1:MT_PersonalInfo_All_EHR></soapenv:Body></soapenv:Envelope>";

        if (destinationName == null) {
            destinationName = "OMPA_WS";
        }
        try {
            // Look up the connectivity configuration API
            Context ctx = new InitialContext();
            ConnectivityConfiguration configuration = (ConnectivityConfiguration) ctx.lookup("java:comp/env/connectivityConfiguration");
            // Get destination configuration for "destinationName"
            DestinationConfiguration destConfiguration = configuration.getConfiguration(destinationName);
            if (destConfiguration == null) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        String.format("Destination %s is not found. Hint: Make sure to have the destination configured.", destinationName));
                return;
            }
            // Get the destination URL
            String value = destConfiguration.getProperty("URL");

            // Authenticator.setDefault(new Authenticator() {
            // protected PasswordAuthentication getPasswordAuthentication() {
            // return new PasswordAuthentication("ICBC", "y1269Min.".toCharArray());
            // }
            // });

            URL url = new URL(
                    "http://dsghrpoq01.corp.doosan.com:50000/XISOAPAdapter/MessageServlet?senderParty=&senderService=EHR_QAS&receiverParty=&receiverService=&interface=PersonalInfo_All_EHR_SO&interfaceNamespace=http%3A%2F%2Fghris.corp.doosan.com%2Fws");
            String proxyType = destConfiguration.getProperty("ProxyType");

            if (ON_PREMISE_PROXY.equals(proxyType)) {
                Proxy proxy = getProxy(proxyType);
                urlConnection = (HttpURLConnection) url.openConnection(proxy);
                LOGGER.info("Using Proxy : " + proxyType);
            } else {
                urlConnection = (HttpURLConnection) url.openConnection();
                LOGGER.info("Using Non Proxy!!!");
            }
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Proxy-Authorization", "Basic SUNCQzp5MTI2OU1pbi4=");
            urlConnection.setRequestProperty("Authorization", "Basic SUNCQzp5MTI2OU1pbi4=");
            urlConnection.setRequestProperty("Content-type", "text/xml; charset=utf-8");
//            urlConnection.setRequestProperty("SOAPAction", "http://dsghrpoq01.corp.doosan.com:50000/XISOAPAdapter");
            urlConnection.setDoOutput(true);

            // OutputStream reqStream = urlConnection.getOutputStream();
            java.io.OutputStreamWriter wr = new java.io.OutputStreamWriter(urlConnection.getOutputStream());
            wr.write(reqXML);
            wr.flush();

            // Copy content from the incoming response to the outgoing response
            InputStream instream = urlConnection.getInputStream();
            OutputStream outstream = response.getOutputStream();
            response.setContentType("text/xml; charset=utf-8");
            copyStream(instream, outstream);
        } catch (Exception e) {
            // Connectivity operation failed
            String errorMessage = "Connectivity operation failed with reason: " + e.getMessage() + ". See "
                    + "logs for details. Hint: Make sure to have an HTTP proxy configured in your " + "local environment in case your environment uses "
                    + "an HTTP proxy for the outbound Internet " + "communication.";
            LOGGER.error("Connectivity operation failed", e);
            e.printStackTrace(System.err);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, errorMessage);
        }
    }

    private Proxy getProxy(String proxyType) {
        String proxyHost = null;
        int proxyPort;
        if (ON_PREMISE_PROXY.equals(proxyType)) {
            // Get proxy for on-premise destinations
            proxyHost = System.getenv("HC_OP_HTTP_PROXY_HOST");
            proxyPort = Integer.parseInt(System.getenv("HC_OP_HTTP_PROXY_PORT"));
            LOGGER.info("HC_OP_HTTP_PROXY_HOST : " + proxyHost);
            LOGGER.info("HC_OP_HTTP_PROXY_PORT : " + proxyPort);
        } else {
            // Get proxy for internet destinations
            proxyHost = System.getProperty("http.proxyHost");
            proxyPort = Integer.parseInt(System.getProperty("http.proxyPort"));
        }
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
    }

    private void injectHeader(HttpURLConnection urlConnection, String proxyType) {
        if (ON_PREMISE_PROXY.equals(proxyType)) {
            // Insert header for on-premise connectivity with the consumer subaccount name
            urlConnection.setRequestProperty("SAP-Connectivity-ConsumerAccount", tenantContext.getAccountName());
        }
    }

    private void copyStream(InputStream inStream, OutputStream outStream) throws IOException {
        byte[] buffer = new byte[COPY_CONTENT_BUFFER_SIZE];
        int len;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
    }
}
