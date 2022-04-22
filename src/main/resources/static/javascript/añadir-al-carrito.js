$(document).ready(function() {

   $("#buttonAddToCart").on("click", function(e){
      addToCart();
   });
   
    function addToCart(){
    cantidad = $("#cantidad" + productoId).val();
    url = "http://localhost:8080/carrito/add/"+productoId+"/"+cantidad;

    $.ajax({
       type: "POST",
       url: url,
       beforeSend: function(xhr){
          xhr.setRequestHeader(crsfHeaderName, crsfValue);
       }
    })
    .done(function(res){
        alert(res); 
    }) 
    .fail(function(){
        alert("Error al agregar su producto a su carrito");
    })  
 
   } 
      
});
