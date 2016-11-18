<!doctype html>
<html>
	<head>
	<title>Form Processing</title>
	 <meta charset="UTF-8">
	 <link type = "text/css" rel="stylesheet" href="style.css">
	</head>
<body >
	<img src = "images/pizzaBanner.jpg" alt = "Pizza Banner">
	
	
  <fieldset>
  	<legend>Add User</legend>
  <form name="user" action="add.html" method="post">
  	Firstname: <input type="text" name="firstname" />	<br/>
  	Lastname: <input type="text" name="lastname" />	<br/>
  	<input type="submit" value="   Save   " />
  </form>
  </fieldset>
  <br/>
  <table class="datatable">
  	<tr>
  		<th>Firstname</th>  <th>Lastname</th>
  	</tr>
    <#list model["userList"] as user>
  	<tr>
  		<td>${user.firstname}</td> <td>${user.lastname}</td>
  	</tr>
    </#list>
  </table>


</body>
</html>