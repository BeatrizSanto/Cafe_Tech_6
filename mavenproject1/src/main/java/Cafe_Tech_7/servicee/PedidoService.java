
package Cafe_Tech_7.servicee;

import Cafe_Tech_7.model.ItemPedido;
import Cafe_Tech_7.model.Pedido;
import Cafe_Tech_7.model.ItemPedido;
import Cafe_Tech_7.model.Pedido;

import java.util.List;

public class PedidoService {

    private static final double LIMITE_PARA_DESCONTO = 50.0;
    private static final double PERCENTUAL_DESCONTO = 0.10;

    public void finalizar(Pedido pedido) {
        System.out.println("Pedido finalizado. Valor: R$ " + pedido.getValorTotal());
    }

   
    public void finalizarComItens(Pedido pedido) {
        double total = calcularValorTotal(pedido.getItens());
        pedido.setValorTotal(total);
        finalizar(pedido);
    }

    
    public double calcularValorTotal(List<ItemPedido> itens) {
        if (itens == null || itens.isEmpty()) {
            throw new IllegalArgumentException("O pedido precisa ter ao menos um item.");
        }

        double totalBruto = 0.0;
        for (ItemPedido item : itens) {
            totalBruto += item.getSubtotal();
        }

        return aplicarDesconto(totalBruto);
    }

    private double aplicarDesconto(double totalBruto) {
        if (totalBruto > LIMITE_PARA_DESCONTO) {
            return totalBruto - (totalBruto * PERCENTUAL_DESCONTO);
        }
        return totalBruto;
    }
}
