
$("button").on("click", function(e){
		alert("!");
		$.post("updateTopping", {id:p.id,name:"My name"}, function(){
		
		});
		return false;
	
	});
