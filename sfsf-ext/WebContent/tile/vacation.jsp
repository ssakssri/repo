<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.Cookie" %><%
    // For the tile to download properly, the CORS response headers must be present
    response.setHeader("Access-Control-Allow-Origin", "https://pmsalesdemo8.successfactors.com");
    response.setHeader("Access-Control-Allow-Credentials", "true");
 
    // Output the JSON that will be used to overwrite properties on the Tile
    response.setHeader("Content-Type", "application/json");
%>
{
  "tileType": "sap.sf.homepage3.tiles.ArcChartTile",
  "properties": {
    "title": "근태현황",
    "subtitle": "2021년 연차소진율",
    "arcValue": 24,
    "icon": "sap-icon://learning-assisstant",
    "info": "Due in 3 weeks",
    "subinfo": "팀 연차 소진율"
  }
}
