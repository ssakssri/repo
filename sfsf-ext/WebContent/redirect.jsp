<%@page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<%@page import="com.ssakssri.api.connectivity.helper.SFUser"%>
<%@page import="com.ssakssri.api.connectivity.SFODataAPIConnector"%>
<%@page import="com.ssakssri.scp.myhrext.util.AES256"%>
<%@page import="java.net.URLEncoder"%>
<html lang="en">
<%

String userId = "No UserId";
String username = "No Username";
String encUsername = "";
String urlPrefix = "https://woomi.hunet.co.kr/Login/__sigUpLOGIN?USERID=";
String urlType1 ="&returnurl=/Classroom/StudyIng?onnoffType=on";
String urlType2 ="&returnurl=/Lecture/OnlineUX/ProcessType2=1";
boolean currentStudyingYn = false;

if (request.getUserPrincipal() != null) {
	userId = request.getUserPrincipal().getName();
	SFUser sfUser = SFODataAPIConnector.getInstance().getUserProfileByUserID(userId);
	
	if (sfUser != null) {
		username = sfUser.username;
	}

	AES256 aes = new AES256();
	encUsername = URLEncoder.encode(aes.encrypt(username), "UTF-8");
}

String type = request.getParameter("type");
if (type != null && type.equals("home")) {	
	response.sendRedirect( urlPrefix + encUsername);
} 

String processcd = request.getParameter("processcd");
if (processcd != null && processcd.length()>0) {
	currentStudyingYn = SFODataAPIConnector.getInstance().getCurrentStudyingYN(username, processcd);
}

%>
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <title>Woomi LMS - 배우미</title>
 
  </head>
  <body class="bg-light">
    <div class="container">
		<div class="d-flex justify-content-center">
		  <div class="spinner-border" role="status">
		    <span class="sr-only">Loading...</span>
		  </div>
		</div>
    </div>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"   integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="  crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <script>
    
    var redirectURL = "https://woomi.hunet.co.kr/Login/__sigUpLOGIN?USERID=BRiqIGClp3ZREvADYFHTIA%3d%3d&returnurl=%2fClassroom%2fStudyIng%3fonnoffType%3don"
    
  	var ssakssri = {
  			onReady : function innerOnReady() {
  				var isMobile = /Android|webOS|iPhone|iPad|iPod|BlackBerry/i.test(navigator.userAgent) ? true : false;

  				var isAndroid = /Android|BlackBerry/i.test(navigator.userAgent) ? true : false;

  				var isIOS = /iPhone|iPad|iPod/i.test(navigator.userAgent) ? true : false;

  				$("#deviceDesc").text("isMobile : " + isMobile + "," + navigator.userAgent);
			    

			    // now I can use is_mobile to run javascript conditionally

			    if (isMobile && isAndroid) {
			    	window.location = "hunetmlc://?cSeq=5024&userId=woomi<%=username%>&action=classroom";
			    } else if (isMobile && isIOS) {
			    	window.location = "hunetmlc5024://?cSeq=5024&userId=woomi<%=username%>&action=classroom";
			    } else {
			    	window.location = "<%=urlPrefix %><%= encUsername %><%=currentStudyingYn?urlType1:urlType2 %>";
			    }
  			},
  			onContentLoad : function innerOnContentLoad () {
  			}
  	}

  	$(document).ready(ssakssri.onReady); 
    </script>   
    
  </body>
</html>
