<%@ page contentType="text/html;charset=KSC5601" %>
<html>

<body>
<h1>한글테스트</h1>
<pre>


RequestURL=<%= HttpUtils.getRequestURL(request).toString() %>

[Request information]
request.getMethod()=<%=request.getMethod()%>
request.getRequestURI()=<%=request.getRequestURI()%>
request.getProtocol()=<%=request.getProtocol()%>
request.getServletPath()=<%=request.getServletPath()%>
request.getPathInfo()=<%=request.getPathInfo()%>
request.getPathTranslated()=<%=request.getPathTranslated()%>
request.getCharacterEncoding()=<%=request.getCharacterEncoding()%>
request.getQueryString()=<%=request.getQueryString()%>
request.getContentLength()=<%=request.getContentLength()%>
request.getContentType()=<%=request.getContentType()%>
request.getServerName()=<%=request.getServerName()%>
request.getServerPort()=<%=request.getServerPort()%>
request.getRemoteUser()=<%=request.getRemoteUser()%>
request.getRemoteAddr()=<%=request.getRemoteAddr()%>
request.getRemoteHost()=<%=request.getRemoteHost()%>
request.getAuthType()=<%=request.getAuthType()%>
request.getLocale()=<%=request.getLocale()%>

[request.getHeaderNames()]
<%
  java.util.Enumeration enum1 = request.getHeaderNames();
  while(enum1.hasMoreElements()){
        String key = (String)enum1.nextElement();
        String value = request.getHeader(key);
        out.println(key + "=" + value);
  }
%>

[request.getParameterNames()]
<%
enum1 = request.getParameterNames();
  while(enum1.hasMoreElements()){
        String key = (String)enum1.nextElement();
        String value = request.getParameter(key);
        out.println(key + "=" + value);
  }
%>

[request.getCookies()]
<%
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length > 0) {
            for(int i=0; i<cookies.length; i++) {
                out.println(cookies[i].getName() + "=" + cookies[i].getValue());
            }
        }
%>

[request.getAttributeNames()]
<%
enum1 = request.getAttributeNames();
        while(enum1.hasMoreElements()) {
            String key = (String)enum1.nextElement();
            Object value = request.getAttribute(key);
            out.println(key + "=" + value );
        }
%>

[request.getSession(false)]
<%
//HttpSession session = request.getSession(false);
if(session != null) {
%>
session.getId():<%=session.getId()%>
session.getLastAccessedTime(): <%= new java.util.Date(session.getLastAccessedTime()).toString()%>
session.getCreationTime():<%=new java.util.Date(session.getCreationTime()).toString()%>
<%
    String mechanism = "unknown";
    if(request.isRequestedSessionIdFromCookie()) {
	mechanism = "cookie";
    }
    else if(request.isRequestedSessionIdFromURL()) {
	mechanism = "url-encoding";
    }
%>Session-tracking mechanism:<%= mechanism %>
<%
    String[] vals = session.getValueNames();
    if(vals != null) {
	for(int i=0; i<vals.length; i++) {
	    String name = vals[i];
	    out.println(" " + name + ": " + session.getValue(name));
	}
    }
}
%>


[System Properties]
<%
        java.util.Properties props = System.getProperties();
enum1 = props.keys();
        while(enum1.hasMoreElements()){
            String key = (String)enum1.nextElement();
            String value = (String)props.get(key);
            out.println(key + "=" + value);
        }
%>

file.encoding=<%= System.getProperty("file.encoding") %>
[WebSphere Properties]
default.client.encoding=<%= System.getProperty("default.client.encoding") %>
client.encoding.override=<%= System.getProperty("client.encoding.override") %>
</pre>
</body>
</html>
