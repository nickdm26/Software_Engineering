<link href="assets/css/admin.css" rel="stylesheet">
<script>
    $(document).ready(function(){
    console.log("script loaded");
    	$("button[name = pizzaSubmit]").click(function(e){
    		e.preventDefault();
    		console.log("Button Clicked");
    		var pizzaName = $("input#pizzaName").val();
    		console.log("Pizza Name Is: " + pizzaName);
    		var pizzaSize = $("input[name=pizzaSize]:checked").val();
    		var pizzaSpicy = $("input[name=spicy]:checked").val();
            console.log(pizzaSize +  " " + pizzaSpicy);
    		var fullPizza = {"pizzaName": pizzaName, "pizzaSize": pizzaSize, "pizzaSpicy": pizzaSpicy};
    		console.log(fullPizza);
    		var array = [];
    				
    		var newPizza = $("input[name=toppings]:checked");
    		console.log(newPizza);
			
    		$.each(newPizza, function(){
    			var self = $(this);
    			console.log(self.val());
    			var parent = self.parents(".parent");
    			var topsPPizza = parent.find(".toppingPerPizza");
    			console.log(topsPPizza.val());
    			var obj = {"toppingID":self.val(), "toppingQuantity":topsPPizza.val()}
    			array.push(obj);
    			console.log(array);
    		});
			
    		//Add toppings array to the fullPizza object
    		fullPizza["toppings"] = array;
    		console.log(fullPizza);  
    				
    		$.post("createPizza",
       			{
          			"formData" : JSON.stringify(fullPizza)
        		},
    			function()
        		{
            		url = "http://localhost:8080/soften2016/adminCreate";
      				$( location ).attr("href", url);
        		});
    		});
		});    	
</script>
	<div class='mainContent'>

		
		<div id = "centeredContainer">
			<ul class="nav nav-tabs">
			  <li class="active"><a href="#">Add New Pizza/Topping</a></li>
			  <li><a href="adminAddStock">Add Stock</a></li>
			  <li><a href="adminDeleteUpdate">Edit Topping Name</a></li>
			  <li><a href="adminCurrentOrders">Current Orders</a></li>
			</ul>    
		<br>
			<legend>Add new pizzas and toppings</legend>
		
			<div class="row-fluid">
				<div class="col-sm-12" style="background-color:#ddd; padding: 20px;">
				
					<form name="createTopping"  modelAttribute="topping" action="createTopping" method="POST">
					
						<fieldset>
					
							<legend>Add a new Topping</legend>
					
							<label for='name'>Topping Name:</label>
							<input id='toppingName' type='text' name='name'>
								
							<br><br>
					
				
							<button type='submit' name='submit'>Submit Your Topping</button>
							<br><br>
						</fieldset>
					</form>
				</div>
			
			
				<div class="col-sm-6" style="background-color:#f5f5f5; padding: 20px;">
					<div id = "matchScroll">
						<legend>Add a new Pizza</legend>
					
						<form name="createPizza" action="createPizza" method="POST">
				
							<label for='pizzaName'>Pizza Name:</label>
							<input id='pizzaName' type='text' name='pizzaName'>    
							<br>
							<label for='pizzaSize'>Pizza Size:</label><br>
							<input type='radio' name='pizzaSize' value='Small'>Small<br>
							<input type='radio' name='pizzaSize' value='Medium'>Medium<br>
							<input type='radio' name='pizzaSize' value='Large'>Large<br><br>
							<label for='spicy'>Is The Pizza Spicy?</label>
							<br>
							<input type='radio' name='spicy' value='spicy'>Spicy
							<br>
							<input type='radio' name='spicy' value='notSpicy'>Not Spicy
							<br><br><br><br>
							<button type='submit' name='pizzaSubmit' value='Submit Your Pizza'>Submit Your Pizza</button>
					</div>
				</div>
			</div>
	  
			<div class="row-fluid">
				<div class="col-sm-6" style="background-color:#f5f5f5; padding: 20px;">
					<div id = "matchScroll">
						<div class="pre-scrollable">
							<label>Please Select Your Toppings: </label>
							<br>
					
							<#if toppings?has_content>
							
								<#list toppings as topping>
									<div class="parent">
										<div class="row-fluid">
											<div class="col-sm-6" style="background-color:#f5f5f5;">
											<input type='checkbox' name='toppings' value='${topping.id}'>${topping.name}
											</div>
											<div class="col-sm-6" style="background-color:#f5f5f5;">
											<label> &nbsp;&nbsp; Number Required:<br></label>
											<input class = "toppingPerPizza" type="number" name="toppingPerPizza" value="0"><br>
											</div>
										</div>
								</#list>              
							</#if>
									</div>
							<br>
						</div> 
					</div>
				</div>
					

			</div>

				</form>    
		</div>
	</div>