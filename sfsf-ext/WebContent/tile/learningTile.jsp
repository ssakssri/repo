<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.Cookie" %><%
    // For the tile to download properly, the CORS response headers must be present
    response.setHeader("Access-Control-Allow-Origin", "https://pmsalesdemo8.successfactors.com");
    response.setHeader("Access-Control-Allow-Credentials", "true");
 
    // Output the JSON that will be used to overwrite properties on the Tile
    response.setHeader("Content-Type", "application/json");
    String userName = request.getUserPrincipal().getName();
%>
[{
	"tileVersion": "NEW_HOME_PAGE",
	"tiles": [{
			"name": "New Test Application",
			"section": "news",
			"metadata": [{
				"title": "My Learning",
				"subTitle": "This is learning tile",
				"locale": "en_US"
			}],
			"type": "static",
			"configuration": {
				"icon": "sap-icon://learning-assisstant"
			},
			"navigation": {
				"type": "iframe-popover",
				"configuration": {
					"contentSize": "responsive",
					"URL": "/tile/learningHome.jsp"
				}
			}
		}]
}]	
