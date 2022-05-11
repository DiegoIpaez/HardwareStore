$(document).ready(function() {

   $("#buttonAddToCart").on("click", function(e){
      addToCart();
   });
   
    function addToCart(){
    cantidad = $("#cantidad" + productoId).val();
    url = "http://localhost:8080/carrito/add/"+productoId+"/"+cantidad;

    $.ajax({
       type: "POST",
       url: url
    })
    .done(function(res){

        Swal.fire({
         text: res,
         icon: "success",
         timer: 1600,
         confirmButtonColor: "green",
         showCloseButton: true,
        });
 
    }) 
    .fail(function(){

        Swal.fire({
         text: "Error al agregar su producto a su carrito",
         icon: "error",
         timer: 1600,
         confirmButtonColor: "red",
         showCloseButton: true,
        });
    })  
 
   } 
      
});
