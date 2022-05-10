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

const sweetAlet = async (text, url) => {
  await Swal.fire({
    title,
    text,
    icon,
    timer,
    timerProgressBar,
    confirmButtonColor,
    showCloseButton,
  });
  window.location.href = url;
};

const succesCategory = document.querySelector("#success-category");
if (succesCategory != null) {
  const text = document.getElementById("success-category").textContent;

  sweetAlet(text,"http://localhost:8080/categoria/list");
}

const succesMarca = document.querySelector("#success-marca");
if (succesMarca != null) {
  const text = document.getElementById("success-marca").textContent;
  sweetAlet(text, "http://localhost:8080/marca/list");
}

const succesProduct = document.querySelector("#success-product");
if (succesProduct != null) {
  const text = document.getElementById("success-product").textContent;
  sweetAlet(text, "http://localhost:8080/producto/list");
}

const succesUser = document.querySelector("#success-user");
if (succesUser != null) {
  const text = document.getElementById("success-user").textContent;

  if (text === 'Se ha registrado correctamente') {
    sweetAlet(text, "http://localhost:8080/login");
  }else{
    sweetAlet(text, "http://localhost:8080/usuario");
  }
}

const succesPay = document.querySelector("#success-pay");
if (succesUser != null) {
  const text = document.getElementById("success-pay").textContent;
  sweetAlet(text,"http://localhost:8080/pedido/usuario");
}

const errorPay = document.querySelector("#error-pay");
if (errorPay != null) {
  const text = document.getElementById("error-pay").textContent;

  const sweetAlet = async () => {
    await Swal.fire({
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

const successForgotPassword = document.querySelector("#success-forgot-password");
if (successForgotPassword != null) {
  const text = document.getElementById("success-forgot-password").textContent;
  Swal.fire({
      title,
      text,
      icon,
      confirmButtonColor,
      showCloseButton,
    })
}

const successCambiarEmail = document.querySelector("#success-cambiar-email");
if (successCambiarEmail != null) {
  const text = document.getElementById("success-cambiar-email").textContent;
  Swal.fire({
      title,
      text,
      icon,
      confirmButtonColor,
      showCloseButton,
    })
}

const successEmailActualizado = document.querySelector("#success-actualizar-email");
if (successEmailActualizado != null) {
  const text = document.getElementById("success-actualizar-email").textContent;
  Swal.fire({
      title,
      text,
      icon,
      confirmButtonColor,
      showCloseButton,
    })
}


const successResetPassword = document.querySelector("#success-reset-password");
if (successResetPassword != null) {
  const text = document.getElementById("success-reset-password").textContent;
  Swal.fire({
      title,
      text,
      icon,
      confirmButtonColor,
      showCloseButton,
    })
}