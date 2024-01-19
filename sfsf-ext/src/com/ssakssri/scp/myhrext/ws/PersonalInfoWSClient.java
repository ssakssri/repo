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
import com.ssakssri.scp.myhrext.bean.Employee;
import com.ssakssri.scp.myhrext.connectivity.ConnectivityProvider;
import com.ssakssri.scp.myhrext.ws.PersonalInfo_All_EHR_SOServiceStub.BasicInfo_type0;
import com.ssakssri.scp.myhrext.ws.PersonalInfo_All_EHR_SOServiceStub.DT_PersonalInfo_All_EHR;
import com.ssakssri.scp.myhrext.ws.PersonalInfo_All_EHR_SOServiceStub.MT_PersonalInfo_All_EHR;
import com.ssakssri.scp.myhrext.ws.PersonalInfo_All_EHR_SOServiceStub.MT_PersonalInfo_All_EHR_response;

public class PersonalInfoWSClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonalInfoWSClient.class);
    private static final String WS_DESTINATION_NAME = "OMPA_WS";
    private static final String ON_PREMISE_PROXY = "OnPremise";

    private String address = "http://dsghrpoq01.corp.doosan.com:50000";
    private String urlQuery = "/XISOAPAdapter/MessageServlet?senderParty=&senderService=EHR_QAS&receiverParty=&receiverService=&interface=PersonalInfo_All_EHR_SO&interfaceNamespace=http%3A%2F%2Fghris.corp.doosan.com%2Fws";

    private String wsId = "ICBC";
    private String wsPassword = "y1269Min.";
    private String proxyType = "Internet";

    private ConnectivityConfiguration connectivityConfig;

    public PersonalInfoWSClient() {
        this(ConnectivityProvider.getConnectivityConfiguration());
    }

    public PersonalInfoWSClient(ConnectivityConfiguration connectivityConfig) {
        this.connectivityConfig = connectivityConfig;
    }

    public PersonalInfoWSClient(String address) {
        this.address = address;
    }

    public PersonalInfoWSClient(String address, String wsId, String wsPassword) {
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

        this.address = destinationConfig.getProperty("URL");

        // Get the destination URL
        this.address = destinationConfig.getProperty("URL");
        this.wsId = destinationConfig.getProperty("User");
        this.wsPassword = destinationConfig.getProperty("Password");
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

    public Employee getProfiles(String pid, String date, String lang) {
        return getProfiles(pid, date, lang, pid);
    }

    public Employee getProfiles(String pid, String date, String lang, String tPid) {
        String langSpras = "3";
        try {

            if (lang.equals("en"))
                langSpras = "E";
            else if (lang.equals("zh"))
                langSpras = "1";

            LOGGER.info("End Point : " + address + urlQuery);

            PersonalInfo_All_EHR_SOServiceStub stub = new PersonalInfo_All_EHR_SOServiceStub(address + urlQuery);
            setOption(stub._getServiceClient().getOptions());

            DT_PersonalInfo_All_EHR dtParam = new DT_PersonalInfo_All_EHR();
            dtParam.setPERNR(pid);
            dtParam.setDATE(date);
            dtParam.setSPRAS(langSpras);
            dtParam.setDPERN(tPid);

            MT_PersonalInfo_All_EHR mtParam = new MT_PersonalInfo_All_EHR();
            mtParam.setMT_PersonalInfo_All_EHR(dtParam);

            MT_PersonalInfo_All_EHR_response res = stub.personalInfo_All_EHR_SO(mtParam);
            BasicInfo_type0 basicInfo = res.getMT_PersonalInfo_All_EHR_response().getBasicInfo();
            if (basicInfo.getEmpName() != null) {
                return new Employee(basicInfo.getEmpName(), basicInfo.getComName(), basicInfo.getOrgName(), basicInfo.getEmpType(), basicInfo.getJobGroup(),
                        basicInfo.getCallingTitle());
            } else {
                LOGGER.warn("No data founded");
                return null;
            }

        } catch (RemoteException e) {
            e.printStackTrace(System.err);
        }
        return null;
    }

    public static void main(String[] args) {

        try {
            PersonalInfoWSClient client = new PersonalInfoWSClient();
            Employee emp = client.getProfiles("1453983", "20180202", "3");

            System.out.println(emp.getEmpName());
            System.out.println(emp.getOrgName());
            System.out.println(emp.getComName());
            System.out.println(emp.getJobGroup());
            System.out.println(emp.getEmpType());
            System.out.println(emp.getCallingTitle());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace(System.err);
        }
    }
}
