<%@page import="com.ssakssri.api.connectivity.SFODataAPIConnector"%>
<%@page import="com.ssakssri.api.connectivity.helper.SFUser"%>
<%@page contentType="text/html; charset=UTF-8" %>

<!doctype html>

<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>BTP Employee Profile</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="style/style.css?20190210v2" />

    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
      }
      .bg-green {background-color: #009a93;}

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
      
      .jumbotron {   background-color: #f0f4f7;  
      	padding: 1rem 2rem; margin-bottom: 1rem;
      }
      
      dt.col-sm {text-align: right;}
      
    </style>
    <!-- Custom styles for this template -->
    <link href="navbar.css" rel="stylesheet">
  </head>
  <body>

<nav class="navbar navbar-expand-lg navbar-dark bg-green">
  <a class="navbar-brand" href="#">SK Ecoplant Demo</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExample05" aria-controls="navbarsExample05" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarsExample05">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">Link</a>
      </li>
      <li class="nav-item">
        <a class="nav-link disabled" href="#">Disabled</a>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="dropdown05" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Dropdown</a>
        <div class="dropdown-menu" aria-labelledby="dropdown05">
          <a class="dropdown-item" href="#">Action</a>
          <a class="dropdown-item" href="#">Another action</a>
          <a class="dropdown-item" href="#">Something else here</a>
        </div>
      </li>
    </ul>
    <form class="form-inline my-2 my-md-0">
      <input class="form-control" type="text" placeholder="Search">
    </form>
  </div>
</nav>


<main role="main">
  <!-- Main jumbotron for a primary marketing message or call to action -->
  <div class="jumbotron">

    <div class="container">
      <h3> OOO 직원 정보 </h3>
      <div class="row">
	    <div class="col-2">
			<img src="https://hcm50preview.sapsf.com/eduPhoto/view?companyId=EYPAST1&photo_type=liveProfile&user_id=100040" class="rounded" alt="임직원 사진">
	    </div>
	    
	    <div class="col-10">
			<dl class="row">
			  <dt class="col-sm">Description lists</dt>
			  <dd class="col-sm">A description list is </dd>
			  <dt class="col-sm">Description lists</dt>
			  <dd class="col-sm">A description list is </dd>
			</dl>
			<dl class="row">
			  <dt class="col-sm">Description lists</dt>
			  <dd class="col-sm">A description list is </dd>
			  <dt class="col-sm">Description lists</dt>
			  <dd class="col-sm">A description list is </dd>
			</dl>
			<dl class="row">
			  <dt class="col-sm">Description lists</dt>
			  <dd class="col-sm">A description list is </dd>
			  <dt class="col-sm">Description lists</dt>
			  <dd class="col-sm">A description list is </dd>
			</dl>
			<dl class="row">
			  <dt class="col-sm">Description lists</dt>
			  <dd class="col-sm">A description list is </dd>
			  <dt class="col-sm">Description lists</dt>
			  <dd class="col-sm">A description list is </dd>
			</dl>
			<dl class="row">
			  <dt class="col-sm">Description lists</dt>
			  <dd class="col-sm">A description list is </dd>
			  <dt class="col-sm">Description lists</dt>
			  <dd class="col-sm">A description list is </dd>
			</dl>
	    </div>
      </div>
    </div>
  </div>

<div class="container">
	<ul class="nav nav-tabs">
	  <li class="nav-item">
	    <a class="nav-link active" href="#">Active</a>
	  </li>
	  <li class="nav-item">
	    <a class="nav-link" href="#">Link</a>
	  </li>
	  <li class="nav-item">
	    <a class="nav-link" href="#">Link</a>
	  </li>
	  <li class="nav-item">
	    <a class="nav-link disabled">Disabled</a>
	  </li>
	</ul>
	
	<div class="container-fluid">
	<table class="table table-hover">
	  <thead>
	    <tr>
	      <th scope="col">#</th>
	      <th scope="col">First</th>
	      <th scope="col">Last</th>
	      <th scope="col">Handle</th>
	    </tr>
	  </thead>
	  <tbody>
	    <tr>
	      <th scope="row">1</th>
	      <td>Mark</td>
	      <td>Otto</td>
	      <td>@mdo</td>
	    </tr>
	    <tr>
	      <th scope="row">2</th>
	      <td>Jacob</td>
	      <td>Thornton</td>
	      <td>@fat</td>
	    </tr>
	    <tr>
	      <th scope="row">3</th>
	      <td colspan="2">Larry the Bird</td>
	      <td>@twitter</td>
	    </tr>
	  </tbody>
	</table>
	</div>

</div>
</main>

<div id="overlay">
  <div class="cv-spinner">
    <span class="spinner"></span>
  </div>
</div>

    <!-- Option 1: jQuery and Bootstrap Bundle (includes Popper) -->
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>

    <script>
  	var MYHR = {
  			onReady : function innerOnReady() {
  				
  				$(document).on({
  				    ajaxStart: function(){
  				    	console.log ("ajaxSend event ==="); $("#overlay").fadeIn(150);
  				    },
  				    ajaxStop: function(){ 
  				    	console.log ("ajaxStop event ==="); $("#overlay").fadeOut(150);
  				    }    
  				});
  				
	  			$('#empSearchBtn').on('click', function (event) {

	  				var urlQuery = "odata/v2/User?$format=json&$select=username,firstName,userId,hireDate,country,businessPhone,title,status,gender,dateOfBirth,lastName,email,department&$filter=userId eq '#username#' or username eq '#username#'";
	  				urlQuery = urlQuery.replaceAll("#username#", $('#pernrTxt').val())

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
