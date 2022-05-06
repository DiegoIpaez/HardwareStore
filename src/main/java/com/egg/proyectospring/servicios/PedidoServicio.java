package com.egg.proyectospring.servicios;

import com.egg.proyectospring.entidades.Detalle;
import com.egg.proyectospring.entidades.Pedido;
import com.egg.proyectospring.entidades.Producto;
import com.egg.proyectospring.entidades.Usuario;
import com.egg.proyectospring.enumeraciones.EstadoPedido;
import com.egg.proyectospring.repositorios.PedidoRepositorio;
import com.egg.proyectospring.repositorios.ProductoRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PedidoServicio {

    @Autowired
    private PedidoRepositorio pedidoRepositorio;
    @Autowired
    private ProductoServicio productoServicio;
    @Autowired
    private ProductoRepository productoRepositorio;

    public Pedido realizarPedido(Usuario u, List<Detalle> carrito) throws Exception {
        Pedido pedido = new Pedido();

        for (Detalle d : carrito) {
            Producto p = productoServicio.buscarProductoPorId(d.getProducto().getId());
            if (d.getCantidad() - 1 < p.getStock()) {
                p.setStock(p.getStock() - d.getCantidad());
                p.setStockVendido(d.getCantidad());
                productoRepositorio.save(p);
                if (p.getStock() == 0) {
                    p.setDisponible(false);
                    productoRepositorio.save(p);
                }
            } else {
                throw new Exception("No hay suficiente stock de " + p.getNombre() + ", intentelo de nuevo.");
            }
        }

        pedido.setEstado(EstadoPedido.PROCESANDO);
        pedido.setFecha(new Date());
        pedido.setTotal(calcularTotal(carrito));
        pedido.setDetalle(detalleDePedido(carrito));
        pedido.setUsuario(u);

        return pedidoRepositorio.save(pedido);
    }

    public List<Pedido> pedidos() {
        return pedidoRepositorio.findAll();
    }

    public Pedido mostrarPedidoPorId(String id) {
        return pedidoRepositorio.getById(id);
    }

    public Page<Pedido> mostrarPedidosPorUsuario(String id, Pageable pageable) {
        return pedidoRepositorio.pedidosPorUsuario(id, pageable);
    }

    public Page<Pedido> getAll(Pageable pageable) {
        return pedidoRepositorio.getAll(pageable);
    }

    public void modificarEstadoPedido(String id, EstadoPedido estado) throws Exception {
        Optional<Pedido> res = pedidoRepositorio.findById(id);
        if (res != null) {
            Pedido pedido = res.get();
            pedido.setEstado(estado);
            pedidoRepositorio.save(pedido);
        } else {
            throw new Exception("No se encontró el pedido");
        }
    }

    public String detalleDePedido(List<Detalle> carrito) {

        String productos = "";
        List<String> lista = new ArrayList<>();

        for (Detalle carrito1 : carrito) {
            String item = carrito1.getProducto().getId() + ", " + carrito1.getCantidad() + ", " + carrito1.getSubtotal();
            lista.add(item);
        }

        productos = lista.toString();
        productos = productos.substring(1, productos.length() - 1);

        return productos;
    }

    public List<Detalle> mostrarDetalle(String detalle) throws Exception {

        String[] aux = detalle.split(",");
        List<String> aux2 = new ArrayList<String>(Arrays.asList(aux));
        List<Detalle> carrito = new ArrayList<>();

        Integer cantidad = aux2.size() / 3;

        for (int i = 0; i < cantidad * 3; i = i + 3) {
            Detalle c = new Detalle();
            String id = aux2.get(i).replace(" ", "");
            String ctd = aux2.get(i + 1).replace(" ", "");
            String sub = aux2.get(i + 2).replace(" ", "");
            Producto p = productoServicio.buscarProductoPorId(id);
            c.setProducto(p);
            c.setCantidad(Integer.parseInt(ctd));
            c.setSubtotal(Double.parseDouble(sub));

            carrito.add(c);
        }

        return carrito;
    }

    public String palabraEliminar(String oracion, String palabra) {
        if (oracion.contains(palabra)) {
            return oracion.replaceAll(palabra, "");
        }
        return oracion;
    }

    public Double calcularTotal(List<Detalle> carrito) {

        Double total = 0.0;
        Double aux = 0.0;
        for (Detalle carrito1 : carrito) {
            aux = carrito1.getSubtotal();
            total = aux + total;
        }
        return total;
    }

}
