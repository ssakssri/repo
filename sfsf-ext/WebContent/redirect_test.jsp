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
      <div class="py-5 text-center">
<!--         <img class="d-block mx-auto mb-4" src="/img/myhr_logo2_w250.png" alt="My HR Logo" width="250" height="60"> -->
        <h2>Hunet Redirect - <%= userId %> <%= username %> (<%= encUsername %>)</h2>
      </div>
      <p class="lead">Device is : <span id="deviceDesc"></span> </p>

      <p class="lead">Current Studying Lecture YN: <span><%= currentStudyingYn %></span></p>

	  <h1 class="mt-5">수강중인 과정</h1>
      <p><%=urlPrefix %><%= encUsername %><%=urlType1 %> <a href="<%=urlPrefix %><%= encUsername %><%=urlType1 %>" target="_blank">바로가기</a></p>
      
      <h1 class="mt-5">과정 리스트</h1>
      <p><%=urlPrefix %><%= encUsername %><%=urlType2 %> <a href="<%=urlPrefix %><%= encUsername %><%=urlType2 %>" target="_blank">바로가기</a></p>

      <h1 class="mt-5">모바일 Android</h1>
      <p>hunetmlc://?cSeq=5024&userId=woomi<%=username%>&action=classroom <a href="hunetmlc://?cSeq=5024&userId=woomi<%=username%>&action=classroom" target="_blank">바로가기</a></p>
      
      <h1 class="mt-5">모바일 iOS</h1>
      <p>hunetmlc5024://?cSeq=5024&userId=woomi<%=username%>&action=classroom <a href="hunetmlc5024://?cSeq=5024&userId=woomi<%=username%>&action=classroom" target="_blank">바로가기</a></p>

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


  				$("#deviceDesc").text("isMobile : " + isMobile + "," + navigator.userAgent);
			    

			    // now I can use is_mobile to run javascript conditionally

			    if (is_mobile == true) {
			        //Conditional script here
			    }
  			},
  			onContentLoad : function innerOnContentLoad () {
  			}
  	}

  	$(document).ready(ssakssri.onReady); 
    </script>   
    
  </body>
</html>
