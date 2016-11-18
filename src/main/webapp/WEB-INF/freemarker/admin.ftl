<div class='mainContent'>

    <p>Welcome to the admin page</p>   
    
    
    <table>
    	<tr>
    		<th></th>
    		<th>Pizza Name</th>
    		<th>Quantity</th>
    		<th>Add Stock</th>
    	</tr>
    	<form name="addPizzaStock" action="addPizzaStock" method="POST">
    	<#if pizzas?has_content>
    		<#list pizzas as p>
    			<tr>
    				<td>Image Here</td>
    				<td>${p.name}</td>
    				<td>${p.quantity}</td>
    				<td><input id='quantity' type='text' name=${p.name} value='0'></td>    				
    			</tr>    	
    		</#list>
    		<button type='submit' name='submit' value='Add Stock'>Add Stock</button>    	
    	</#if>
    	</form>    	
    </table>    
</div>
