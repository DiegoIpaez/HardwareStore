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
