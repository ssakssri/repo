package com.ssakssri.api.connectivity.http;

import java.util.Map;

import com.sap.core.connectivity.api.configuration.ConnectivityConfiguration;
import com.sap.core.connectivity.api.configuration.DestinationConfiguration;

public class ConnectivityConfigurationLocal implements ConnectivityConfiguration {

	DestinationConfiguration destConfig = new DestinationConfigurationLocal();

	@Override
	public void clearCache() {
		// TODO Auto-generated method stub

	}

	@Override
	public DestinationConfiguration getConfiguration(String input) {

		return null;

	}

	@Override
	public DestinationConfiguration getConfiguration(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, DestinationConfiguration> getConfigurations(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
