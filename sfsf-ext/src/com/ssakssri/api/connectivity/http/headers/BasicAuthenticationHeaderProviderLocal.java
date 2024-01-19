package com.ssakssri.api.connectivity.http.headers;

import java.nio.charset.StandardCharsets;

import javax.xml.bind.DatatypeConverter;

import com.sap.core.connectivity.api.authentication.AuthenticationHeader;
import com.sap.core.connectivity.api.configuration.DestinationConfiguration;

@SuppressWarnings("nls")
public class BasicAuthenticationHeaderProviderLocal {

    private static final String BASIC_AUTHENTICATION_PREFIX = "Basic ";
    private static final String SEPARATOR = ":";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String PASSWORD_PROPERTY = "Password";
    private static final String USER_PROPERTY = "User";

    public AuthenticationHeader getAuthenticationHeader(DestinationConfiguration destinationConfiguration) {
        StringBuilder userPass = new StringBuilder();
        userPass.append("");
        userPass.append(SEPARATOR);
        userPass.append("1234Qwer.");
        String encodedPassword = DatatypeConverter.printBase64Binary(userPass.toString().getBytes(StandardCharsets.UTF_8));
        AuthenticationHeaderImpl basicAuthentication = new AuthenticationHeaderImpl(AUTHORIZATION_HEADER, BASIC_AUTHENTICATION_PREFIX
                + encodedPassword);
        return basicAuthentication;
    }

}
