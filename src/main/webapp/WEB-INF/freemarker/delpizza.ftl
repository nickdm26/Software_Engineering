<div class='mainContent'>
    <p>Select the pizza(s) to delete</p>
    <fieldset>
        <legend>Delete Pizza</legend>
        <form name="delPizza" action="del" method="post">
            <table>
                <thead>
                <tr>
                    <th>Pizza</th>
                    <th>Toppings</th>
                    <th>Select All<input type="checkbox" value="selectAll"></th>
                </tr>
                </thead>
            <#if pizzas?has_content>
                <#list pizzas as p>
                <tr>
                    <td>${p.name}</td>
                    <#if p.pizzaToppings?has_content>
                        <td>
                        <#list p.pizzaToppings as pt>
                               ${pt.topping.name}
                            <#if pt_has_next>, </#if>
                        </#list>
                    </td>
                    </#if>
                    <td><input type="checkbox" name="formToppings" value="${p.name}"/></td>
                </tr>
                </#list>
            </#if>
            </table>
            <input type="submit" value="Delete Pizza(s)"/>
        </form>
    </fieldset>
</div>