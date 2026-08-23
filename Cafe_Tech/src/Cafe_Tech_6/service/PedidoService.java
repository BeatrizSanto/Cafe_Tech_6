
package Cafe_Tech_6.service;

import Cafe_Tech_6.model.Pedido;


public class PedidoService {

    public void finalizar(Pedido pedido) {
        System.out.println("Pedido finalizado. Valor: R$ " + pedido.getValorTotal());
    }
}
