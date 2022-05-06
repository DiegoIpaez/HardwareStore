const swalObject = {
  title: "Felicidades!",
  icon: "success",
  timer: 5000,
  timerProgressBar: true,
  confirmButtonColor: "green",
  showCloseButton: true,
};

const {
  title,
  icon,
  timer,
  timerProgressBar,
  confirmButtonColor,
  showCloseButton,
} = swalObject;

const succesCategory = document.querySelector("#success-category");
if (succesCategory != null) {
  const text = document.getElementById("success-category").textContent;

  const sweetAlet = async () => {
    await Swal.fire({
      title,
      text,
      icon,
      timer,
      timerProgressBar,
      confirmButtonColor,
      showCloseButton,
    });
    window.location.href = "http://localhost:8080/categoria/list";
  };

  sweetAlet();
}

const succesMarca = document.querySelector("#success-marca");
if (succesMarca != null) {
  const text = document.getElementById("success-marca").textContent;

  const sweetAlet = async () => {
    await Swal.fire({
      title,
      text,
      icon,
      timer,
      timerProgressBar,
      confirmButtonColor,
      showCloseButton,
    });
    window.location.href = "http://localhost:8080/marca/list";
  };

  sweetAlet();
}

const succesProduct = document.querySelector("#success-product");
if (succesProduct != null) {
  const text = document.getElementById("success-product").textContent;

  const sweetAlet = async () => {
    await Swal.fire({
      title,
      text,
      icon,
      timer,
      timerProgressBar,
      confirmButtonColor,
      showCloseButton,
    });
    window.location.href = "http://localhost:8080/producto/list";
  };

  sweetAlet();
}

const succesUser = document.querySelector("#success-user");
if (succesUser != null) {
  const text = document.getElementById("success-user").textContent;

  const sweetAlet = async () => {
    await Swal.fire({
      title,
      text,
      icon,
      timer,
      timerProgressBar,
      confirmButtonColor,
      showCloseButton,
    });

    if (text === 'Se ha registrado correctamente') {
      window.location.href = "http://localhost:8080/login";
    }else{
      window.location.href = "http://localhost:8080/usuario"
    }
    
  };

  sweetAlet();
}

const succesPay = document.querySelector("#success-pay");
if (succesUser != null) {
  const text = document.getElementById("success-pay").textContent;

  const sweetAlet = async () => {
    await Swal.fire({
      title,
      text,
      icon,
      timer,
      timerProgressBar,
      confirmButtonColor,
      showCloseButton,
    });

    window.location.href = "http://localhost:8080/pedido/usuario"
  };

  sweetAlet();
}

const errorPay = document.querySelector("#error-pay");
if (errorPay != null) {
  const text = document.getElementById("error-pay").textContent;

  const sweetAlet = async () => {
    await Swal.fire({
      title,
      text,
      icon:"error",
      timer,
      timerProgressBar,
      confirmButtonColor,
      showCloseButton,
    });

    window.location.href = "http://localhost:8080/carrito"
  };

  sweetAlet();
}