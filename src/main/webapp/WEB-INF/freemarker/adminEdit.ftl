<div class="container">
  		 
        <#if pizzas?has_content>
            <ul>
                <#list pizzas as p>
                    <tbody>
                            <tr>
                                <td>${p.name}</td>
                            </tr>
                    </tbody>
                </#list>
            </ul>
        </#if> 	
 		
  	 				
  						  
</div>
