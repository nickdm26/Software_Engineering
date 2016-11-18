<link href="assets/css/admin.css" rel="stylesheet">

<div class='mainContent'>

    <div id="centeredContainer">
        <ul class="nav nav-tabs">
            <li><a href="adminCreate">Add New Pizza/Topping</a></li>
            <li><a href="adminAddStock">Add Stock</a></li>
            <li><a href="adminDeleteUpdate">Edit Topping Name</a></li>
            <li class="active"><a href="#">Current Orders</a></li>
        </ul>
		<br>
        <legend>Status of current orders in the system</legend>


        <div class="panel panel-default">
            <div class="panel-heading">Orders:</div>
            <div class="panel-body">

                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>Order Id</th>
                        <th>Date</th>
                        <th>Delivery</th>
                        <th>Address</th>
                        <th>Total Cost</th>
                        <th>Status</th>

                    </tr>
                    </thead>

                <#if orders?has_content>
                    <ul>
                        <#list orders as o>
                            <tr>
                                <td>${o.id}</td>
                                <td>${o.orderDate}</td>

                                <#if o.delivery?has_content && o.delivery>
                                    <td>Yes</td>
                                <#else>
                                    <td>No</td>
                                </#if>

                                <td>
                                	<#if o.address?has_content>
                                		${o.address}
                                	</#if>
                                </td>
                                <td>
                                    <#if o.orderCost?has_content>
                            			${o.orderCost?string.currency}
                            		</#if>
                                </td>
                                <td>
                                    <#if o.orderStatus?has_content>
                            			${o.orderStatus}
                            		</#if>
                                </td>

                                </td>
                                <td>
                                    <form name="order" modelAttribute="order" action="updateOrder" method="POST">
                                        <input type="hidden" name="id" value="${o.id}"/>
                                    <#--    <button type="submit">Edit</button>-->
                                    </form>
                                </td>
                                <td>
                                    <form name="order" modelAttribute="order" name="${o.id}" action="deleteOrder"
                                          method="POST">

                                        <input type="hidden" name="name" value="${o.id}"/>
                                        <input type="hidden" name="theOrderId" value="${o.id}"/>
                                        <button type="submit">Delete</button>
                                    </form>
                                </td>
                            </tr>
                        </#list>
                    </ul>
                </#if>


                </table>
            </div>
        </div>