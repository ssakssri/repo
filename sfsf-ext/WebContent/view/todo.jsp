<!doctype html>
<html lang="en">
<%

String userName = "No User Credential";
if (request.getUserPrincipal() != null) {
	userName = request.getUserPrincipal().getName();
}

%>
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <title>My HR Extension</title>
 
  </head>
  <body class="bg-light">
    <div class="container">
      <div class="py-5 text-center">
<!--         <img class="d-block mx-auto mb-4" src="/img/myhr_logo2_w250.png" alt="My HR Logo" width="250" height="60"> -->
        <h2>SFSF - SCP - OMPA Extension Demo - <%= userName %></h2>
        <p class="lead">Below is an example form built entirely with Bootstrap's form controls. Each required form group has a validation state that can be triggered by attempting to submit the form without completing it.</p>
      </div>
      
      <div class="row">
        <div class="col-md-8 order-md-2 mb-4">
          <h4 class="d-flex justify-content-between align-items-center mb-3">
            <span class="text-muted">Employee Information</span>
<!--             <span class="badge badge-secondary badge-pill">3</span> -->
          </h4>
          <ul class="list-group mb-3">
            <li class="list-group-item d-flex justify-content-between lh-condensed">
              <div>
                <small class="text-muted">First Name</small>
                <h6 id="firstName" class="my-0"> </h6>
              </div>
            </li>
            <li class="list-group-item d-flex justify-content-between lh-condensed">
              <div>
                <small class="text-muted">Last Name</small>
                <h6 id="lastName" class="my-0"> </h6>
              </div>
            </li>
            <li class="list-group-item d-flex justify-content-between lh-condensed">
              <div>
                <small class="text-muted">Country</small>
                <h6 id="country" class="my-0"> </h6>
              </div>
            </li>
            <li class="list-group-item d-flex justify-content-between lh-condensed">
              <div>
                <small class="text-muted">Title</small>
                <h6 id="title" class="my-0"> </h6>
              </div>
            </li>
            <li class="list-group-item d-flex justify-content-between lh-condensed">
              <div>
                <small class="text-muted">Hire Date</small>
                <h6 id="hireDate" class="my-0"> </h6>
              </div>
            </li>
            <li class="list-group-item d-flex justify-content-between lh-condensed">
              <div>
                <small class="text-muted">Department</small>
                <h6 id="department" class="my-0"> </h6>
              </div>
            </li>
          </ul>
        </div>
        
        <div class="col-md-4 order-md-1">
        
          <h4 class="mb-3">Employee Search</h4>
          
          <form id="empSearchFrm" class="card p-2">
            <div class="input-group">
              <input id="pernrTxt" type="text" class="form-control" name="p_pernr" placeholder="input username">
              <div class="input-group-append">
                <button id="empSearchBtn" type="button" class="btn btn-secondary">Search</button>
              </div>
            </div>
          </form>

        </div>
      </div>      
      
    </div>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"   integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="  crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <script>
  	var MYHR = {
  			onReady : function innerOnReady() {
	  			$('#empSearchBtn').on('click', function (event) {

	  				var urlQuery = "https://sfsfextensiono8oy8p89y4.us1.hana.ondemand.com/odata/v2/User?$format=json&$select=username,firstName,userId,hireDate,country,businessPhone,title,status,gender,dateOfBirth,lastName,email,department&$filter=username eq '#username#'";
	  				urlQuery = urlQuery.replace("#username#", $('#pernrTxt').val())

	  				$.ajax({
							url : urlQuery,
							data: $("#empSearchFrm").serialize(),
							contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
							async :true,
							dataType :"json",
							success : function(data) {
								
								var result = data.d.results[0];
								
								if (data != "null") {
									$("#firstName").text(result.firstName);
									$("#lastName").text(result.lastName);
									$("#country").text(result.country);
									$("#title").text(result.title);
									
	  								var hDate = result.hireDate;
	  								var regExp = /\(([^)]+)\)/;
	  								var matches = regExp.exec(hDate);
									
									console.log (matches[1])
									var hDate = new Date(eval(matches[1]));
									
									$("#hireDate").text(hDate.toDateString());
									$("#department").text(result.department);
								}
							},
							error:function (xhr, ajaxOptions, thrownError){
								alert(xhr);
							}
						});	
	  			});	 
	  			
	  			$('#pernrTxt').keypress(function (e) {
	  				 var key = e.which;
	  				 if(key == 13) {
	  					$('#empSearchBtn').click();
	  				    return false;  
	  				  }
	  			});   
  			},
  			
  			onContentLoad : function innerOnContentLoad () {
  			}
  	}

  	$(document).ready(MYHR.onReady); 
    </script>   
    
  </body>
</html>
