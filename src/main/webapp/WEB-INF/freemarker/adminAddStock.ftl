<link href="assets/css/admin.css" rel="stylesheet">
<div class='mainContent'>
    <div id="centeredContainer">
        <ul class="nav nav-tabs">
            <li><a href="adminCreate">Add New Pizza/Topping</a></li>
            <li class="active"><a href="#">Add Stock</a></li>
            <li><a href="adminDeleteUpdate">Edit Topping Name</a></li>
            <li><a href="adminCurrentOrders">Current Orders</a></li>
        </ul>
        <br>
        <legend>Add stock to Pizzas</legend>
    <#if pizzas?has_content>
        <table class="table">
            <tr>
                <th>Pizza Name</th>
                <th>Pizza Size</th>
                <th>Pizza Toppings</th>
                <th>Pizza Spicy</th>
                <th>Quantity</th>
                <th>Add Stock</th>
            </tr>
            <form name="addStock" action="addPizzaStock" method="POST">
                <#list pizzas as p>
                    <tr class="pizzaRow">
                        <td>${p.name}</td>
                        <td>${p.size!"no size"}</td>
                        <td>
                            <#if p.pizzaToppings?has_content>
                                (
                                <#list p.pizzaToppings as pt>
                                    <span class="pizzaToppingName">${pt.topping.name}</span>
                                    <span class="pizzaToppingQuantity topping_${p.id}"
                                          data-toppingid="${pt.topping.id}">${pt.toppingsPerPizza}</span>
                                    <#if pt_has_next>, </#if>
                                </#list>
                                )
                            </#if>
                        </td>
                        <#if p.spicy == true>
                            <td>Spicy</td>
                        <#else>
                            <td>Mild</td>
                        </#if>
                        <td class="pizzaQuantity">${p.quantity}</td>
                        <td><input class="pizzaStock" data-pizzaid="${p.id}" type="number" name="${p.id}" value="0">
                        </td>
                    </tr>
                </#list>
        </table>
        <button type='button' name='submit' value='Add Stock'>Add Pizza Stock</button>
        </form>
    </#if>
        <script>
            $(document).ready(function () {
                $("button[name = submit]").click(function (e) {
                    e.preventDefault();
                    var arrayAddStock = [];
                    $(".pizzaStock").each(function (i, e) {
                        var arrayTops = [];
                        var self = $(this);
                        var pizzaID = self.data("pizzaid");
                        /*console.log(pizzaID);     */
                        /*Pizza ID is correct here when printed out*/
                        var quantityAdded = self.val();
                        /*console.log(quantityAdded);*/
                        /*Quantity is correct here when printed out*/
                        var eachPizza = {"pizzaID": pizzaID, "quantityAdded": quantityAdded};
                        $(".topping_" + pizzaID).each(function () {
                            var self = $(this);
                            var pizzaToppingID = self.data("toppingid");
                            var pizzaToppingQuantity = self.text();
                            /*console.log(pizzaToppingID + "," + pizzaToppingQuantity);*/
                            var obj = {"toppingID": pizzaToppingID, "toppingQuantity": pizzaToppingQuantity};
                            arrayTops.push(obj);
                            /*console.log(arrayTops);*/
                        });
                        eachPizza["toppings"] = arrayTops;
                        console.log("Each Pizza" + eachPizza);
                        arrayAddStock.push(eachPizza);
                    });
                    /*console.log("eachPizzaObject");*/
                    /*console.log(arrayAddStock);*/
                    var toppingQuantities = [];
                    var obj = {};
                    $(".toppingQuantity").each(function (i, e) {
                        var self = $(this);
                        var toppingID = self.data("toppingid");
                        /*console.log("Topping id= " + toppingID);*/
                        var toppingQuantity = self.text();
                        /*console.log("Topping Quantity= " + toppingQuantity);*/
                        obj[toppingID] = toppingQuantity;
                    });
                    console.log("ToppingQuans: ", obj);
                    var canPost = true;
                    for (var i = 0; i < arrayAddStock.length; i++) {
                        var canBeAdded = true;
                        var pizzaToBeAdded = arrayAddStock[i]["quantityAdded"];
                        if (pizzaToBeAdded > 0) {
                            console.log("Got passed pizzaToBeAdded if statement");
                            var pizzaID = arrayAddStock[i]["pizzaID"];
                            var toppings = arrayAddStock[i]["toppings"];
                            for (var j = 0; j < toppings.length; j++) {
                                var topsID = toppings[j]["toppingID"];
                                var topsQuan = toppings[j]["toppingQuantity"];
                                var totalToppings = pizzaToBeAdded * topsQuan;
                                console.log("topsID", topsID);
                                console.log("toppingQuan", obj);
                                console.log("totalToppings", totalToppings);
                                console.log("obj[topsID]", obj[topsID]);
                                if (totalToppings > obj[topsID]) {
                                    canBeAdded = false;
                                    canPost = false;
                                    console.log("Cant be added");
                                }
                            }
                        }
                        if (!canBeAdded) {
                            alert("Cannot Add Pizza - Not enough toppings!");
                        }
                        else {
                            console.log("Success", arrayAddStock);
                        }
                    }
                    if (canPost) {
                        var myString = JSON.stringify(arrayAddStock);
                        console.log(myString);
                        $.post("addPizzaStock",
                                {
                                    "formData": myString
                                },
                                function () {
                                    $(".pizzaRow").each(function () {
                                        var $myPizzaQuan = $(this).find(".pizzaQuantity");        //Store the column with pizza quantity in memory
                                        var $myInputField = $(this).find(".pizzaStock");      //Store the column with input in memory
                                        var currentQuantity = parseInt($myPizzaQuan.text());    //Grab the current quantity
                                        var stockToAdd = parseInt($myInputField.val());         //Grab the inputted value
                                        $myPizzaQuan.text(currentQuantity + stockToAdd);        //Add the inputted value to the old quantity and put it in the current stock field
                                        $myInputField.val(0);
                                    });
                                    url = "http://localhost:8080/soften2016/adminAddStock";
                                    $(location).attr("href", url);

                                });
                    }

                });
            });
        </script>
        <br>
        <br>
        <br>
        <legend>Add stock to Toppings</legend>
    <#if toppings?has_content>
        <table class="table">
            <tr>
                <th>Topping Name</th>
                <th>Quantity</th>
                <th>Add Stock</th>
            </tr>
            <form name="addStock" action="addToppingStock" method="POST">
                <#list toppings as t>
                    <tr class="toppingRow">
                        <td>${t.name}</td>
                        <td data-toppingid="${t.id}" class="toppingQuantity">${t.quantity}</td>
                        <td><input data-toppingid="${t.id}" class="toppingStock" type="number" name="${t.id}" value="0">
                        </td>
                    </tr>
                </#list>
        </table>
        <button type='button' name='submitToppingStock' value='Add Stock'>Add Topping Stock</button>
        </form>
    </#if>
        <script>
            $(document).ready(function () {
                $("button[name = submitToppingStock]").click(function (e) {
                    e.preventDefault();
                    var obj = $(".toppingStock").serializeArray();
                    console.log(obj);
                    myString = JSON.stringify(obj);
                    console.log(myString);
                    $.post("addToppingStock",
                            {
                                "formData": myString
                            },
                            function () {
                                $(".toppingRow").each(function () {
                                    var $myToppingQuan = $(this).find(".toppingQuantity");        //Store the column with pizza quantity in memory
                                    var $myInputField = $(this).find(".toppingStock");            //Store the column with input in memory
                                    var currentQuantity = parseInt($myToppingQuan.text());      //Grab the current quantity
                                    var stockToAdd = parseInt($myInputField.val());             //Grab the inputted value
                                    $myToppingQuan.text(currentQuantity + stockToAdd);          //Add the inputted value to the old quantity and put it in the current stock field
                                    $myInputField.val(0);
                                });
                            });
                });
            });
        </script>
    </div>
</div>