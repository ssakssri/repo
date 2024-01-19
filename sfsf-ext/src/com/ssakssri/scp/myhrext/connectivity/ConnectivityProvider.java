package com.ssakssri.scp.myhrext.connectivity;

import com.sap.core.connectivity.api.configuration.ConnectivityConfiguration;

public class ConnectivityProvider {

	private static ConnectivityConfiguration connectivityConfiguration;

	public static ConnectivityConfiguration getConnectivityConfiguration() {
		return connectivityConfiguration;
	}

	static void setConnectivityConfiguration(ConnectivityConfiguration connectivityConfiguration) {
		ConnectivityProvider.connectivityConfiguration = connectivityConfiguration;
	}

}
