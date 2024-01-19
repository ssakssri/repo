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
    "title": "매출현황",
    "subtitle": "2021년 누적",
    "numberValue": "134",
    "numberState": "Positive",
    "numberDigits": 1,
    "stateArrow": "Up",
    "numberFactor": "억",
    "icon": "sap-icon://sales-order",
    "info": "성수본점",
    "subinfo": ""
  }
}
