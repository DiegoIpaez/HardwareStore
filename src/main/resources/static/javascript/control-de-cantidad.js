$(document).ready(function() {

   $(".minusButton").on("click", function(e){
      e.preventDefault();
      productoId = $(this).attr("pid");
      qtyInput = $("#cantidad"+ productoId);

      newQty = parseInt(qtyInput.val()) - 1;
      if (newQty > 0) qtyInput.val(newQty);
    })
       
   $(".plusButton").on("click", function(e){
       e.preventDefault();
       productoId = $(this).attr("pid");
       qtyInput = $("#cantidad"+ productoId);
       
       newQty = parseInt(qtyInput.val()) + 1;
       stock = document.getElementById("producto-stock").textContent;
       if(newQty > 0){
       if(newQty < parseInt(stock)+1){
       qtyInput.val(newQty);}
       }
    })
      
});
