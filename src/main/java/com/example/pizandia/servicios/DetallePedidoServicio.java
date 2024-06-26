package com.example.pizandia.servicios;

import com.example.pizandia.entidades.DetallePedido;
import com.example.pizandia.repositorios.DetallePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetallePedidoServicio {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    public DetallePedido saveDetallePedido(DetallePedido detallePedido) {
        return detallePedidoRepository.save(detallePedido);
    }
}
