<#import "../spring.ftl" as spring />
<#assign baseUrl><@spring.url ""/></#assign>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
	<meta http-equiv="Content-Type" content="text/html"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <!-- Bootstrap -->
    <link href="${baseUrl}/assets/css/bootstrap.min.css" rel="stylesheet">
	<!--<link href="${baseUrl}/assets/css/home.css" rel="stylesheet">-->
	
	<script src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.12.0/jquery.validate.min.js" 
	type="text/javascript"></script>
	
	<!--<script src="http://form-serialize.googlecode.com/svn/trunk/serialize-0.2.min.js" type="text/javascript"></script>--> <!-- For Serialization Function -->
	
	<script src="https://code.jquery.com/jquery-3.0.0.min.js" integrity="sha256-JmvOoLtYsmqlsWxa7mDSLMwa6dZ9rrIdtrrVYRnDRH0=" crossorigin="anonymous"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script> 
	<!--<script src="${baseUrl}/js/serialize.js"></script>-->

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
	<link rel="icon" href="${baseUrl}/assets/images/pizzaIcon.ico">
	<script src="https://maps.googleapis.com/maps/api/js"></script>
    <!--<#if page == 'login'>
        <link href="${baseUrl}/assets/css/loginform.css" rel="stylesheet">
    </#if>-->

    <title>Freeza Pizza</title>
    
    


</head>
<body data-spy="scroll" data-target=".navbar" data-offset="50">
