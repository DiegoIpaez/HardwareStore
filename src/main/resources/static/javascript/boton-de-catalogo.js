const urlApi = "http://localhost:8080/api";
            const ulC = document.getElementById("categorias");
            const liC = document.createElement("li");
            const ulM = document.getElementById("marcas");
            const liM = document.createElement("li");
            fetch(urlApi+"/categorias")
            .then((response) => response.json())
            .then((data) => {
            let datos = "";
            for (let i = 0; i < data.length; i++) {
            datos += `<li><a class="dropdown-item" th:href="@{/producto/categoria(categoriaId=${data[i].id})}">${data[i].nombre}</a></li>`;
            }

            liC.innerHTML = datos;
            ulC.appendChild(liC);
            });

            fetch(urlApi+"/marcas")
            .then((response) => response.json())
            .then((data) => {
            let datos = "";
            for (let i = 0; i < data.length; i++) {
            datos += `<li><a class="dropdown-item" th:href="@{/producto/marca(marcaId=${data[i].id})}">${data[i].nombre}</a></li>`;
            }

            liM.innerHTML = datos;
            ulM.appendChild(liM);
            });
