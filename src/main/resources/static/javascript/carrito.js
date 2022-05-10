$(document).ready(function() {

   $(".minusButton").on("click", function(e){
      e.preventDefault();
      reducirCantidad($(this))
    })
       
   $(".plusButton").on("click", function(e){
       e.preventDefault();
       incrementarCantidad($(this))
    })
 
   $(".link-remove").on("click", function(e){
      e.preventDefault();
      eliminarDelCarrito($(this));
   })

    actualizarTotal();    
});

function eliminarDelCarrito(link){
    url = link.attr("href");

    $.ajax({
       type: "POST",
       url: url
    })
    .done(function(res){
        Swal.fire({
         text: res,
         icon: "info",
         timer: 1600,
         confirmButtonColor: "gray",
         showCloseButton: true,
        });

        rowNumber = link.attr("rowNumber");
        eliminarProducto(rowNumber);
        actualizarTotal();  
    }) 
    .fail(function(){
        Swal.fire({
         text: "Error al eliminar su producto a su carrito",
         icon: "error",
         timer: 1600,
         confirmButtonColor: "red",
         showCloseButton: true,
        });
    }) 
}

function eliminarProducto(rowNumber){
   rowId = "row" + rowNumber;
   $("#" + rowId).remove();
}

function reducirCantidad(link){
    productoId = link.attr("pid");
      qtyInput = $("#cantidad"+ productoId);

      newQty = parseInt(qtyInput.val()) - 1;
      if (newQty > 0){ 
        qtyInput.val(newQty);
        actualizarCantidad(productoId,newQty);
      }
}

function incrementarCantidad(link){
    productoId = link.attr("pid");
    stock = link.attr("stock");
    qtyInput = $("#cantidad"+ productoId);

    newQty = parseInt(qtyInput.val()) + 1;
    if (newQty > 0){ 
       if(newQty < parseInt(stock)+1){
       qtyInput.val(newQty);
       actualizarCantidad(productoId,newQty);}
    }
}

function actualizarCantidad(productoId,cantidad){
  url = "http://localhost:8080/carrito/actualizar/"+productoId+"/"+cantidad;

    $.ajax({
       type: "POST",
       url: url
    })
    .done(function(res){
        actualizarSubtotal(res,productoId)
        actualizarTotal()
    }) 
    .fail(function(){
        alert("Error al agregar su producto a su carrito");
    }) 
}

function actualizarTotal(){
   total = 0.0;

   $(".subtotalProd").each((index, element)=>{
     total = total + parseFloat(element.innerHTML);
   });

   $("#cantidadTotal").text("$" + total);

   if($("#cantidadTotal").text() == "$0"){  
    setTimeout(() => {
    window.location.href = "http://localhost:8080/carrito"
    }, 1000);
   }
}

function actualizarSubtotal(newSubtotal, productoId){
   $("#subtotal"+productoId).text(newSubtotal);
}