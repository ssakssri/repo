<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <title>Hello, world!</title>
  </head>
  <body>
    <h1>Hello, world!</h1>

<div class="container theme-showcase" role="main">
	<div class="page-header" style="margin-top: 80px;" >
      <h3>Search Employee</h3>
   	</div>
   	
	<form id="empFrm" name="empFrm" class="form-inline" onsubmit="return false">
	  <div class="form-group">
	    <label for="inputPersonId">User Name</label>&nbsp;&nbsp;
	    <input type="text" class="form-control" name="username" id="username" placeholder="ex) sfadmin" style="width:350px;" autocomplete="off">
	  </div>
	  <button id="btnSearch" type="submit" class="btn btn-default">Get Employee Info</button>
	  <input type="hidden" name="ompaImageUrl">
	</form>

	<div class="alert alert-success" id="alert_box" style="display:none;">
	  	<a href="#" class="close" data-dismiss="alert">&times;</a>
	   	<span id="alert_messsage"><strong>Success!</strong> Your message has been sent successfully.</span>
	</div>

   	<div class="page-header">
	  <h3>Employee Information</h3>
   	</div>

	<div id="urlTextarea" class="col-md-7">
	    <textarea class="form-control" rows="4"></textarea>
	</div>

</div> <!-- /container -->


    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

	<script>
	var currentPicklist = "";
  	var MYHR = {
  			onReady : function innerOnReady() {
	  			
	  			$('#btnSearch').on('click', function (event) {
	  				
	  				var urlQuery = "https://sfsfextensiono8oy8p89y4.us1.hana.ondemand.com/odata/v2/User?$format=json&$select=username,firstName,userId,hireDate,country,businessPhone,title,status,gender,dateOfBirth,lastName,email,department&$filter=username eq 'sfadmin'";
					
					var param = $("input[name=username]").val();
					console.log("send param : " + param);
					
  		        	$.ajax({
  		                url: urlQuery,
						contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
						dataType :"json",
						success: function(d){
							
							var result = d.results;
							alert ( result.username );


						},
						error:function (xhr, ajaxOptions, thrownError){
							 alert("Fail to retrieve translates!!!");
							 $(this).modal("hide")
						}
  	                });

	  				
	  				$("#urlTextarea").load("ssoUrl.jsp?mode=ajaxGenSSOUrl", { userId: param }, function() {});
	  			});	  		
  			},
  			
  			onContentLoad : function innerOnContentLoad () {

  			}
  	}

  	$(document).ready(MYHR.onReady); 

	</script>
  </body>
</html>


