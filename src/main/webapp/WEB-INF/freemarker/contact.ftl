<div class='container'> 
	<div class='mainContent'>
	    <p>Welcome to the contact page</p>
	    
	    <#if myStrings?has_content>
	    	<#list myStrings as S>
	    		${S}<br/>
	    	</#list>
	    </#if>
	    
	</div>
</div> 
