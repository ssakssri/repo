<%@page import="com.ssakssri.api.connectivity.SFODataAPIConnector"%>
<%@page import="com.ssakssri.api.connectivity.helper.SFUser"%>
<%@page contentType="text/html; charset=UTF-8" %>

<!doctype html>
<%

String userId = request.getParameter("userId");
SFUser sfUser = null;

if (userId == null)
	userId = "100040";

sfUser = SFODataAPIConnector.getInstance().getUserProfileByUserID(userId);

%>

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
      .row {
    	margin-bottom: 5px;
	  }
      
      dt.col-sm {text-align: right;}
      
      @media (min-width: 1580px) {
		.container { max-width: 1540px;}
	  }
	  
	  .col, .col-1, .col-10, .col-11, .col-12, .col-2, .col-3, .col-4, .col-5, .col-6, .col-7, .col-8, .col-9, .col-auto, .col-lg, .col-lg-1, .col-lg-10, .col-lg-11, .col-lg-12, .col-lg-2, .col-lg-3, .col-lg-4, .col-lg-5, .col-lg-6, .col-lg-7, .col-lg-8, .col-lg-9, .col-lg-auto, .col-md, .col-md-1, .col-md-10, .col-md-11, .col-md-12, .col-md-2, .col-md-3, .col-md-4, .col-md-5, .col-md-6, .col-md-7, .col-md-8, .col-md-9, .col-md-auto, .col-sm, .col-sm-1, .col-sm-10, .col-sm-11, .col-sm-12, .col-sm-2, .col-sm-3, .col-sm-4, .col-sm-5, .col-sm-6, .col-sm-7, .col-sm-8, .col-sm-9, .col-sm-auto, .col-xl, .col-xl-1, .col-xl-10, .col-xl-11, .col-xl-12, .col-xl-2, .col-xl-3, .col-xl-4, .col-xl-5, .col-xl-6, .col-xl-7, .col-xl-8, .col-xl-9, .col-xl-auto {
    	padding-right: 10px;
    	padding-left: 10px;
	  }
      .bd-tabsub {
	    position: relative;
	    padding: 1rem;
	    margin-right: 0;
	    margin-left: 0;
	    border: 1px solid #dee2e6;
	    border-top: none;
	  }
	  
	  dt.col-sm-1 {text-align: right;}
	  
	  .hide {display: none;}
    </style>
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
      <h3 style="margin-bottom: 15px;"> <%= sfUser.displayName %> 직원 정보 </h3>
      <div class="row">
	    <div class="col-2">
			<img src="https://hcm50preview.sapsf.com/eduPhoto/view?companyId=EYPAST1&photo_type=liveProfile&user_id=<%=userId%>" width="130px" class="rounded" alt="임직원 사진">
	    </div>
	    
	    <div class="col-10">
			<dl class="row">
			  <dt class="col-sm-1">법인</dt>
			  <dd class="col-sm-3"><%= sfUser.custom01 %></dd>
			  <dt class="col-sm-1">직위/직책</dt>
			  <dd class="col-sm-3"><%= sfUser.title %></dd>
			  <dt class="col-sm-1">상태 </dt>
			  <dd class="col-sm-3"><%= sfUser.custom05 %></dd>
			</dl>
			<dl class="row">
			  <dt class="col-sm-1">사업부</dt>
			  <dd class="col-sm-3"><%= sfUser.custom02 %></dd>
			  <dt class="col-sm-1">이메일</dt>
			  <dd class="col-sm-3"><%= sfUser.email %> </dd>
			  <dt class="col-sm-1">입사일 </dt>
			  <dd class="col-sm-3"><%= sfUser.hireDate %></dd>
			</dl>
			<dl class="row">
			  <dt class="col-sm-1">부문</dt>
			  <dd class="col-sm-3"><%= sfUser.division %></dd>
			  <dt class="col-sm-1">연락처</dt>
			  <dd class="col-sm-3"><%= sfUser.businessPhone %></dd>
			  <dt class="col-sm-1">그룹 입사일 </dt>
			  <dd class="col-sm-3"><%= sfUser.hireDate %></dd>
			</dl>
			<dl class="row">
			  <dt class="col-sm-1">부서</dt>
			  <dd class="col-sm-3"><%= sfUser.department %></dd>
			  <dt class="col-sm-1">휴대폰</dt>
			  <dd class="col-sm-3"><%= sfUser.cellPhone %></dd>
			  <dt class="col-sm-1">근속 시작일</dt>
			  <dd class="col-sm-3"><%= sfUser.hireDate %></dd>
			</dl>
			<dl class="row">
			  <dt class="col-sm-1">사업장</dt>
			  <dd class="col-sm-3"><%= sfUser.location %></dd>
			  <dt class="col-sm-1">성별</dt>
			  <dd class="col-sm-3"><%= sfUser.gender.equals("F")?"여성":"남성" %></dd>
			  <dt class="col-sm-1">생년월일</dt>
			  <dd class="col-sm-3"><%= sfUser.dateOfBirth %></dd>
			</dl>
	    </div>
      </div>
    </div>
  </div>

<div class="container">
	<ul id="myTab" class="nav nav-tabs">
	  <li class="nav-item">
	    <a id="loadEmpJob" class="nav-link active" href="#">발령이력</a>
	  </li>
	  <li class="nav-item">
	    <a class="nav-link disabled" href="#">신상정보</a>
	  </li>
	  <li class="nav-item">
	    <a id="loadEducation" class="nav-link" href="#">학력</a>
	  </li>
	  <li class="nav-item">
	    <a class="nav-link disabled" href="#">자격증</a>
	  </li>
	</ul>
	
	<div id="empJobDiv" class="bd-tabsub container-fluid">
	<table class="table table-hover">
	  <thead>
	    <tr>
	      <th scope="col">발령시작일</th>
	      <th scope="col">발령종료일</th>
	      <th scope="col">발령사유</th>
	      <th scope="col">사업부</th>
	      <th scope="col">부문</th>
	      <th scope="col">부서</th>
	    </tr>
	  </thead>
	  <tbody id="empJobHistory">

	  </tbody>
	</table>
	</div>

	<div id="educationDiv" class="hide bd-tabsub container-fluid">
	<table class="table table-hover">
	  <thead>
	    <tr>
	      <th scope="col">시작일</th>
	      <th scope="col">종료일</th>
	      <th scope="col">학교</th>
	      <th scope="col">전공</th>
	      <th scope="col">학위</th>
	    </tr>
	  </thead>
	  <tbody id="educationList">

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
	
    <form id="empSearchFrm">
    </form>

    <!-- Option 1: jQuery and Bootstrap Bundle (includes Popper) -->
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"   integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="  crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>

    <script>
  	function nvl(value, newVal) {
  		if ( isNull(value) ) {
  			return newVal;
  		}
  		return value;
  	}
    
  	function isNull(value) {
  		if (value == 'undefined' || value == undefined || value == null || (typeof(value) == "string" && trim(value).length == 0) ) {
  			return true;
  		}
  		return false;
  	}  
  	function trim(str) {
  		return str.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
  	}
  	
  	function convertUnixTimeToDateFormat(input) {
  	    // Use regex to extract the timestamp
  	    const match = /\/Date\((\d+)\)\//.exec(input);
  	    
  	    
  	    if (match) {
  	        // Extract the timestamp and convert it to a Date object
  	        const date = new Date(Number(match[1]));
  	        
  	        // Extract the year, month, and day and pad them to ensure they are in the correct format
  	        const yyyy = date.getFullYear();
  	        const mm = String(date.getMonth() + 1).padStart(2, '0'); // JavaScript months start from 0
  	        const dd = String(date.getDate()).padStart(2, '0');

  	  	    console.log(input + " : " + yyyy + "/" + mm + "/" + dd);
  	        
  	        return yyyy + "/" + mm + "/" + dd;
  	    } else {
  	        throw new Error('Invalid Unix time format');
  	    }
  	}
  	
  	
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

  			loadEducation : function innerEducation () {
  				var empJobQuery = "odata/v2/EmpJob?$format=json&$select=seqNumber,startDate,endDate,event,eventReason,businessUnit,division,department,eventReasonNav/nameTranslationNav/value_ko_KR,departmentNav/name_ko_KR&$expand=eventNav,eventReasonNav/nameTranslationNav,departmentNav&$filter=userId eq '#userId#'&$orderby=startDate desc,seqNumber&fromDate=2000-01-01&toDate=9999-12-31";
  				empJobQuery = empJobQuery.replaceAll("#userId#", "<%= sfUser.userId %>");

  				console.log(empJobQuery);
  				
  				$.ajax({
					url : empJobQuery,
					data: $("#empSearchFrm").serialize(),
					contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
					async :true,
					dataType :"json",
					success : function(data) {
						
						var results = data.d.results;
						
						console.log("Reuslt Size : " + results.length);
						
	 						var stringBuilder = [];
	 						var size = results.length;
	 			         	for (i=0 ; i < size ; i++) {
	 			             	var row = results[i];
	 			             	stringBuilder.push("<tr><td>"+convertUnixTimeToDateFormat(nvl(row.startDate,""))+"</td><td>"+convertUnixTimeToDateFormat(nvl(row.endDate,""))+"</td>")
	 			             	stringBuilder.push("<td>"+nvl(row.eventReasonNav.nameTranslationNav.value_ko_KR,"")+"</td><td>"+nvl(row.businessUnit,"")+"</td>")
	 			             	stringBuilder.push("<td>"+nvl(row.division,"")+"</td><td>"+nvl(row.departmentNav.name_ko_KR,"")+"</td></tr>")
	 			         	}	
	
	 			         	$("#empJobHistory").html(stringBuilder.join(""));
					},
					error:function (xhr, ajaxOptions, thrownError){
						alert(xhr);
					}
				});
  				
  			},
  			
  			loadEmpJob : function innerLoadEmpJob () {
  				var empJobQuery = "odata/v2/EmpJob?$format=json&$select=seqNumber,startDate,endDate,event,eventReason,businessUnit,division,department,eventReasonNav/nameTranslationNav/value_ko_KR,departmentNav/name_ko_KR&$expand=eventNav,eventReasonNav/nameTranslationNav,departmentNav&$filter=userId eq '#userId#'&$orderby=startDate desc,seqNumber&fromDate=2000-01-01&toDate=9999-12-31";
  				empJobQuery = empJobQuery.replaceAll("#userId#", "<%= sfUser.userId %>");

  				console.log(empJobQuery);
  				
  				$.ajax({
					url : empJobQuery,
					data: $("#empSearchFrm").serialize(),
					contentType: "application/x-www-form-urlencoded; charset=UTF-8", 
					async :true,
					dataType :"json",
					success : function(data) {
						
						var results = data.d.results;
						
						console.log("Reuslt Size : " + results.length);
						
	 						var stringBuilder = [];
	 						var size = results.length;
	 			         	for (i=0 ; i < size ; i++) {
	 			             	var row = results[i];
	 			             	stringBuilder.push("<tr><td>"+convertUnixTimeToDateFormat(nvl(row.startDate,""))+"</td><td>"+convertUnixTimeToDateFormat(nvl(row.endDate,""))+"</td>")
	 			             	stringBuilder.push("<td>"+nvl(row.eventReasonNav.nameTranslationNav.value_ko_KR,"")+"</td><td>"+nvl(row.businessUnit,"")+"</td>")
	 			             	stringBuilder.push("<td>"+nvl(row.division,"")+"</td><td>"+nvl(row.departmentNav.name_ko_KR,"")+"</td></tr>")
	 			         	}	
	
	 			         	$("#empJobHistory").html(stringBuilder.join(""));
					},
					error:function (xhr, ajaxOptions, thrownError){
						alert(xhr);
					}
				});
  				
  			}
  			
  	}

  	$(document).ready(MYHR.onReady);   		
  	$(document).ready(MYHR.loadEmpJob); 

  	$('#myTab a').on('click', function (e) {
			  e.preventDefault();
			  $(this).tab('show');
			  console.log ($(this).id);
	});

    </script>   
    
  </body>
</html>
