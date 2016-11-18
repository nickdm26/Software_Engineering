<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="home">Freeza Pizza</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li class=""><a href="${baseUrl}/home">Home</a></li>
		<li class=""><a href="${baseUrl}/order">Order</a></li>
		<li class=""><a href="${baseUrl}#about">About Us</a></li>
		<li class=""><a href="${baseUrl}#contact">Contact</a></li>
      </ul>
      
      <ul class="nav navbar-nav navbar-right">

      <#-- Campbell's link to admin showing when logged in -->
      <#if isLoggedIn?has_content && isLoggedIn>
      	<li class="active"><a href="${baseUrl}/adminCreate">Admin</a></li>
      </#if>
        
        <#-- <li><a href="${baseUrl}\login"><span class="glyphicon glyphicon-log-in"></span> Login</a></li> -->
        
        <#if !isLoggedIn?has_content || (isLoggedIn?has_content && !isLoggedIn)>
       
       <li><a href="${baseUrl}\login"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
        <#else>
        <li><a href="${baseUrl}\logout">Log Out</a></li>
        </#if>

      </ul>
    </div>
  </div>
</nav>
<div class = "empty"></div>

