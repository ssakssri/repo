<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.Cookie" %><%
    // For the tile to download properly, the CORS response headers must be present
    response.setHeader("Access-Control-Allow-Origin", "https://pmsalesdemo8.successfactors.com");
    response.setHeader("Access-Control-Allow-Credentials", "true");
 
    // Output the JSON that will be used to overwrite properties on the Tile
    response.setHeader("Content-Type", "application/json");
%>
{
  "tileType": "sap.sf.homepage3.tiles.DynamicTile",
  "properties": {
    "title": "EHS",
    "subtitle": "2021년 무재해",
    "numberValue": "689",
    "numberState": "Positive",
    "numberDigits": 1,
    "stateArrow": "Up",
    "numberFactor": "일",
    "icon": "sap-icon://family-care",
    "info": "소사~원시 2공구",
    "subinfo": "연속달성"
  }
}
