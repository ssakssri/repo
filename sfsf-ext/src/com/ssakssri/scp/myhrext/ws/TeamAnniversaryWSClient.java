package com.ssakssri.scp.myhrext.ws;

import java.rmi.RemoteException;

import javax.ws.rs.InternalServerErrorException;

import org.apache.axis2.Constants;
import org.apache.axis2.client.Options;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.HttpTransportProperties;
import org.apache.axis2.transport.http.impl.httpclient4.HttpTransportPropertiesImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.core.connectivity.api.configuration.ConnectivityConfiguration;
import com.sap.core.connectivity.api.configuration.DestinationConfiguration;
import com.ssakssri.scp.myhrext.connectivity.ConnectivityProvider;
import com.ssakssri.scp.myhrext.ws.TeamAnniversary_EHR_SOServiceStub.AnniversaryList_type0;
import com.ssakssri.scp.myhrext.ws.TeamAnniversary_EHR_SOServiceStub.DT_IF060_EHR;
import com.ssakssri.scp.myhrext.ws.TeamAnniversary_EHR_SOServiceStub.MT_IF060_EHR;
import com.ssakssri.scp.myhrext.ws.TeamAnniversary_EHR_SOServiceStub.MT_IF060_EHR_response;

public class TeamAnniversaryWSClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamAnniversaryWSClient.class);
    private static final String WS_DESTINATION_NAME = "OMPA_EAI";
    private static final String ON_PREMISE_PROXY = "OnPremise";
    private static final String URL_QUERY = "/XISOAPAdapter/MessageServlet?senderParty=&senderService=EHR_PRD&receiverParty=&receiverService=&interface=TeamAnniversary_EHR_SO&interfaceNamespace=http%3A%2F%2Fghris.corp.doosan.com%2Fws";

    // private static final String address =
    // "http://dsghrpoq01.corp.doosan.com:50000/XISOAPAdapter/MessageServlet?senderParty=&senderService=EHR_QAS&receiverParty=&receiverService=&interface=TeamAnniversary_EHR_SO&interfaceNamespace=http%3A%2F%2Fghris.corp.doosan.com%2Fws";
    private String address = "http://hr-eai.doosan.com:50000/XISOAPAdapter/MessageServlet?senderParty=&senderService=EHR_PRD&receiverParty=&receiverService=&interface=TeamAnniversary_EHR_SO&interfaceNamespace=http%3A%2F%2Fghris.corp.doosan.com%2Fws";
    private String address1 = "http://hr-eai.doosan.com:50000/XISOAPAdapter/MessageServlet?senderParty=&senderService=EHR_PRD&receiverParty=&receiverService=&interface=TeamAnniversary_EHR_SO&interfaceNamespace=http%3A%2F%2Fghris.corp.doosan.com%2Fws";

    private String wsId = "ICBC";
    private String wsPassword = "y1269Min.";
    private String proxyType = "Internet";

    private ConnectivityConfiguration connectivityConfig;

    public TeamAnniversaryWSClient() {
        this(ConnectivityProvider.getConnectivityConfiguration());
    }

    public TeamAnniversaryWSClient(ConnectivityConfiguration connectivityConfig) {
        this.connectivityConfig = connectivityConfig;
    }

    public TeamAnniversaryWSClient(String address) {
        this.address = address;
    }

    public TeamAnniversaryWSClient(String address, String wsId, String wsPassword) {
        this.address = address;
        this.wsId = wsId;
        this.wsPassword = wsPassword;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAuth(String wsId, String wsPassword) {
        this.wsId = wsId;
        this.wsPassword = wsPassword;
    }

    public void init() {
        DestinationConfiguration destinationConfig = connectivityConfig.getConfiguration(WS_DESTINATION_NAME);
        if (destinationConfig == null) {
            throw new InternalServerErrorException();
        }

        // Get the destination URL
        this.address = destinationConfig.getProperty("URL") + URL_QUERY;
        // this.wsId = destinationConfig.getProperty("User");
        // this.wsPassword = destinationConfig.getProperty("Password");

        this.wsId = "ICBC";
        this.wsPassword = "y1269Min.";

        this.proxyType = destinationConfig.getProperty("ProxyType");

        LOGGER.info("Destination URL : " + address);
        LOGGER.info("Destination User : " + wsId);
        LOGGER.info("Destination Password : " + wsPassword);
        LOGGER.info("Destination Proxy : " + proxyType);
    }

    private void setOption(Options options) {
        HttpTransportPropertiesImpl.Authenticator auth = new HttpTransportPropertiesImpl.Authenticator();
        auth.setUsername(wsId);
        auth.setPassword(wsPassword);
        // set if realm or domain is known
        options.setProperty(org.apache.axis2.transport.http.HTTPConstants.AUTHENTICATE, auth);

        if (ON_PREMISE_PROXY.equals(proxyType)) {
            HttpTransportProperties.ProxyProperties proxyProperties = new HttpTransportProperties.ProxyProperties();
            String proxyHost = System.getenv("HC_OP_HTTP_PROXY_HOST");
            int proxyPort = Integer.parseInt(System.getenv("HC_OP_HTTP_PROXY_PORT"));

            LOGGER.info("HC_OP_HTTP_PROXY_HOST : " + proxyHost);
            LOGGER.info("HC_OP_HTTP_PROXY_PORT : " + proxyPort);

            proxyProperties.setProxyName(proxyHost);
            proxyProperties.setProxyPort(proxyPort);

            options.setProperty(org.apache.axis2.transport.http.HTTPConstants.PROXY, proxyProperties);
            options.setProperty(HTTPConstants.CHUNKED, false);
            options.setProperty(Constants.Configuration.ENABLE_REST, Constants.VALUE_TRUE);
        }
    }

    public MT_IF060_EHR_response getAnniversary(String pid, String date, String lang) {
        String langSpras = "3";
        try {

            if (lang.equals("en"))
                langSpras = "E";
            else if (lang.equals("zh"))
                langSpras = "1";

            TeamAnniversary_EHR_SOServiceStub stub = new TeamAnniversary_EHR_SOServiceStub(address1);
            setOption(stub._getServiceClient().getOptions());

            DT_IF060_EHR dtParam = new DT_IF060_EHR();
            dtParam.setPersonId(pid);
            dtParam.setDate(date);
            dtParam.setLang(langSpras);
            
            System.err.println("AnniversaryWSClient.java ->>> set : " + pid + " " + date + " " + lang);

            MT_IF060_EHR mtParam = new MT_IF060_EHR();
            mtParam.setMT_IF060_EHR(dtParam);

            MT_IF060_EHR_response res = stub.teamAnniversary_EHR_SO(mtParam);

            System.err.println("AnniversaryWSClient.java ->>> get : " + res);

            return res;
        } catch (RemoteException e) {
            e.printStackTrace(System.err);
        }
        return null;
    }

    public static void main(String[] args) {
        TeamAnniversaryWSClient client = new TeamAnniversaryWSClient();
        MT_IF060_EHR_response res = client.getAnniversary("1453983", "20170320", "3");

        AnniversaryList_type0[] anniversaryList = res.getMT_IF060_EHR_response().getAnniversaryList();

        System.out.println("TeamAnniversaryBehind.java ->>> anniversaryListLength : " + anniversaryList.length);

        for (AnniversaryList_type0 anniversary : anniversaryList) {
            System.out.println(anniversary.getEmpNm());
            System.out.println(anniversary.getBirthYmd());
        }

    }
}
