<link href="assets/css/admin.css" rel="stylesheet">

<div class='mainContent'>

    <div id = "centeredContainer">
	<ul class="nav nav-tabs">
	  <li><a href="adminCreate">Add New Pizza/Topping</a></li>
	  <li><a href="adminAddStock">Add Stock</a></li>
	  <li class="active"><a href="#">Edit Topping Name</a></li>
	  <li><a href="adminCurrentOrders">Current Orders</a></li>
	</ul>
	<br>
	<legend>Edit name of toppings</legend>


	<#if toppings?has_content>
        <ul><div class="pre-scrollable">
        <#list toppings as p>
            <li>
            	<form name="topping" modelAttribute="topping" action="updateTopping" method="POST">
            		<div class="row-fluid">
	            		<div id="heightSpacing">
							<div class="col-sm-4" style="background-color:#f5f5f5;">
		            			${p.name}
		            		</div>
		            		<div class="col-sm-4" style="background-color:#f5f5f5;">
		            			<input type="text" name="name" />   
		            			<input type="hidden" name="id"  value="${p.id}"/> 
		            		</div
		            		<div class="col-sm-3" style="background-color:#f5f5f5;">        		
		            			<button type="submit">Edit</button>
		            		</div>
		            	</div>
            		
            	</form>    
<#--            	<form name="topping" modelAttribute="topping" action="deleteTopping" method="POST">
            		<input type="hidden" name="name" value="${p.name}"/>   
            		<input type="hidden" name="id"  value="${p.id}"/>         		
            		<button type="submit">Delete</button>
            	</form>  -->
            </li>
        </#list>
        </ul>
        </div>
    </#if>
     </div>
</div>


