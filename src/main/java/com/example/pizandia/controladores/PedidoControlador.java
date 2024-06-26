package com.example.pizandia.controladores;

import com.example.pizandia.entidades.DetallePedido;
import com.example.pizandia.entidades.Pedido;
import com.example.pizandia.entidades.Producto;
import com.example.pizandia.repositorios.ClienteRepository;
import com.example.pizandia.repositorios.ProductoRepository;
import com.example.pizandia.servicios.DetallePedidoServicio;
import com.example.pizandia.servicios.PedidoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/pedidos")
@CrossOrigin(origins = "http://localhost:3000")
public class PedidoControlador {

    @Autowired
    private PedidoServicio pedidoServicio;

    @Autowired
    private DetallePedidoServicio detallePedidoServicio;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @PostMapping("/confirmar")
    public ResponseEntity<Pedido> confirmarPedido(@RequestBody Map<String, Object> payload) {
        try {
            Pedido pedido = new Pedido();
            pedido.setCliente(clienteRepository.findById(Long.parseLong(((Map) payload.get("pedido")).get("clienteId").toString())).orElse(null));
            pedido.setFecha(new Date());
            pedido.setEstado("pendiente");

            Pedido nuevoPedido = pedidoServicio.savePedido(pedido);

            List<DetallePedido> detalles = ((List<Map<String, Object>>) payload.get("detalles")).stream().map(detalle -> {
                DetallePedido detallePedido = new DetallePedido();
                detallePedido.setPedido(nuevoPedido);
                Producto producto = productoRepository.findById(Long.parseLong(detalle.get("idproducto").toString())).orElse(null);
                if (producto != null) {
                    detallePedido.setProducto(producto);
                    detallePedido.setCantidad(Integer.parseInt(detalle.get("cantidad").toString()));
                    detallePedido.setTalla(detalle.get("talla").toString());
                    detallePedido.setPrecio(Double.parseDouble(detalle.get("precio").toString()));
                }
                return detallePedido;
            }).collect(Collectors.toList());

            detalles.forEach(detallePedidoServicio::saveDetallePedido);

            return ResponseEntity.ok(nuevoPedido);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
