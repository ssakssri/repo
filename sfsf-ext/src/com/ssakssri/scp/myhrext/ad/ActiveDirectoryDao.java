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
 * JNDI/LDAP? ?΄?©?΄? MS Active Directory?? ?°??? ?΄??€.
 * 
 * @version 1.0
 */
public class ActiveDirectoryDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActiveDirectoryDao.class);

    private static final String AD_DESTINATION_NAME = "DOOSAN_AD";

    private Auth auth;
    private ConnectivityConfiguration connectivityConfig;

    /**
     * κΈ°λ³Έ λ£¨νΈ.
     */
    // protected DirContext ldapContext;

    /**
     * Active Directory ?λ²μ ?Έ?€?Έ.
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
     * ?¬?©?κ°? μ‘΄μ¬??μ§? ??Έ??€.
     * 
     * @param name
     *            ?¬?©? ??΄?
     * @return ?¬?©?κ°? μ‘΄μ¬?λ©? trueλ₯?, μ‘΄μ¬?μ§? ??Όλ©? falseλ₯? λ°ν??€.
     */
    public boolean hasUser(String name) {
        UserEntry ue = auth.GetUserEntry(name);
        if (ue != null)
            return true;
        return false;
    }

    /**
     * ?¬?©? ? λ³΄λ?? java.util.Map κ°μ²΄? ?΄?? λ°ν??€. ?¬?©?κ°? μ‘΄μ¬?μ§? ??Όλ©?
     * java.util.Collections.EMPTY_MAP κ°μ²΄λ₯? λ°ν??€.
     * 
     * @param name
     *            ?¬?©? ??΄?
     * @return ?¬?©? ? λ³΄λ?? ?΄κ³? ?? java.util.Map κ°μ²΄
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