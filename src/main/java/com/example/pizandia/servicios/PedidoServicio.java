package com.example.pizandia.servicios;

import com.example.pizandia.entidades.Pedido;
import com.example.pizandia.repositorios.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoServicio {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido savePedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }
}
