package com.ssakssri.api.connectivity.http;

import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

import com.sap.core.connectivity.api.configuration.DestinationConfiguration;

public class DestinationConfigurationLocal implements DestinationConfiguration {

	private static final Map<String, String> conf = new HashMap<String, String>();
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String PASSWORD_PROPERTY = "Password";
	private static final String USER_PROPERTY = "User";
	private static final String BASIC_AUTHENTICATION_PREFIX = "Basic ";

	public DestinationConfigurationLocal() {
		conf.put(DESTINATION_URL, "https://api50preview.sapsf.com:443/odata/v2");
		conf.put(DESTINATION_USER, "jeong-seok.lee@kr.ey.com");
		conf.put(DESTINATION_PASSWORD, "1234Qwer.");
	}

	@Override
	public Map<String, String> getAllProperties() {
		// TODO Auto-generated method stub
		return conf;
	}

	@Override
	public KeyStore getKeyStore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProperty(String arg0) {
		// TODO Auto-generated method stub
		return (String) conf.get(arg0);
	}

	@Override
	public KeyStore getTrustStore() {
		// TODO Auto-generated method stub
		return null;
	}

}
