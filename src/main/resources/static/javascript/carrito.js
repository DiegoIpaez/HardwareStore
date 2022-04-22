$(document).ready(function() {

   $(".minusButton").on("click", function(e){
      e.preventDefault();
      reducirCantidad($(this))
    })
       
   $(".plusButton").on("click", function(e){
       e.preventDefault();
       incrementarCantidad($(this))
    })

    actualizarTotal();    
});

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
    qtyInput = $("#cantidad"+ productoId);

    newQty = parseInt(qtyInput.val()) + 1;
    if (newQty > 0){ 
       qtyInput.val(newQty);
       actualizarCantidad(productoId,newQty);
    }
}

function actualizarCantidad(productoId,cantidad){
  url = "http://localhost:8080/carrito/actualizar/"+productoId+"/"+cantidad;

    $.ajax({
       type: "POST",
       url: url,
       beforeSend: function(xhr){
          xhr.setRequestHeader(crsfHeaderName, crsfValue);
       }
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
}

function actualizarSubtotal(newSubtotal, productoId){
   $("#subtotal"+productoId).text(newSubtotal);
}