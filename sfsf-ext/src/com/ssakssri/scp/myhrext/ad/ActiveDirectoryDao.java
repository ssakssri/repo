/*
 * Copyright (c) 2018 Doosan Corporation. All rights reserved.
 *
 * This software is the confidential and proprietary information of Doosan Corporation.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance wih the terms of the license agreement you entered into
 * with Doosan Corporation.
 */

package com.ssakssri.scp.myhrext.ad;

import javax.ws.rs.InternalServerErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sap.core.connectivity.api.configuration.ConnectivityConfiguration;
import com.sap.core.connectivity.api.configuration.DestinationConfiguration;
import com.ssakssri.scp.myhrext.connectivity.ConnectivityProvider;

import DSGAuthPkg.Auth;
import DSGAuthPkg.UserEntry;
import DSGAuthPkg.UserNotFoundException;

/**
 * JNDI/LDAP?�� ?��?��?��?�� MS Active Directory?? ?��?��?��?�� ?��?��?��.
 * 
 * @version 1.0
 */
public class ActiveDirectoryDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActiveDirectoryDao.class);

    private static final String AD_DESTINATION_NAME = "DOOSAN_AD";

    private Auth auth;
    private ConnectivityConfiguration connectivityConfig;

    /**
     * 기본 루트.
     */
    // protected DirContext ldapContext;

    /**
     * Active Directory ?��버의 ?��?��?��.
     */
    private String host = "";
    private String username = "";
    private String password = "";

    public ActiveDirectoryDao() {
        this(ConnectivityProvider.getConnectivityConfiguration());
    }

    public ActiveDirectoryDao(ConnectivityConfiguration connectivityConfig) {
        this.connectivityConfig = connectivityConfig;

        DestinationConfiguration destinationConfig = connectivityConfig.getConfiguration(AD_DESTINATION_NAME);
        if (destinationConfig == null) {
            throw new InternalServerErrorException();
        }

        // Get the destination URL
        this.host = destinationConfig.getProperty("ldap.domain");
        this.username = destinationConfig.getProperty("ldap.user");
        this.password = destinationConfig.getProperty("ldap.password");

        LOGGER.info("Destination URL : " + host);
        LOGGER.info("Destination User : " + username);

        auth = new Auth(host, this.username, this.password);
    }

    public boolean validateUser(String username, String password) throws UserNotFoundException {
        return auth.validateUser(username, password);
    }

    /**
     * ?��?��?���? 존재?��?���? ?��?��?��?��.
     * 
     * @param name
     *            ?��?��?�� ?��?��?��
     * @return ?��?��?���? 존재?���? true�?, 존재?���? ?��?���? false�? 반환?��?��.
     */
    public boolean hasUser(String name) {
        UserEntry ue = auth.GetUserEntry(name);
        if (ue != null)
            return true;
        return false;
    }

    /**
     * ?��?��?�� ?��보�?? java.util.Map 객체?�� ?��?��?�� 반환?��?��. ?��?��?���? 존재?���? ?��?���?
     * java.util.Collections.EMPTY_MAP 객체�? 반환?��?��.
     * 
     * @param name
     *            ?��?��?�� ?��?��?��
     * @return ?��?��?�� ?��보�?? ?���? ?��?�� java.util.Map 객체
     */
    public UserEntry getUser(String name) {
        return auth.GetUserEntry(name);
    }

    public static void main(String[] args) throws Exception {
        ActiveDirectoryDao dao = new ActiveDirectoryDao();
        System.out.println(dao.hasUser("sakssri"));
        System.out.println(dao.getUser("ssakssri").GetValue("description"));
    }
}