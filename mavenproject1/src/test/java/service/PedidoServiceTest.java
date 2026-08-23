package service;

import Cafe_Tech_7.model.ItemPedido;
import Cafe_Tech_7.model.Produto;
import Cafe_Tech_7.model.StatusProduto;
import Cafe_Tech_7.servicee.PedidoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("PedidoService - cálculo do valor total do pedido")
class PedidoServiceTest {

    private PedidoService pedidoService;
    private Produto cafeExpresso;
    private Produto paoDeQueijo;

    @BeforeEach
    void configurar() {
        pedidoService = new PedidoService();

        cafeExpresso = new Produto(
            1,
            "Café Expresso",
            8.00,
            StatusProduto.ATIVO
        );

        paoDeQueijo = new Produto(
            2,
            "Pão de Queijo",
            6.00,
            StatusProduto.ATIVO
        );
    }

    @Test
    @DisplayName("Deve somar os subtotais quando o valor bruto não ultrapassa o limite de desconto")
    void deveCalcularTotalSemDesconto() {

        List<ItemPedido> itens =
            Collections.singletonList(
                new ItemPedido(cafeExpresso, 2)
            );

        double total = pedidoService.calcularValorTotal(itens);

        assertEquals(16.00, total, 0.001);
    }

    @Test
    @DisplayName("Deve aplicar 10% de desconto quando o valor bruto ultrapassa R$ 50,00")
    void deveAplicarDescontoAcimaDoLimite() {

        List<ItemPedido> itens = Arrays.asList(
            new ItemPedido(cafeExpresso, 5),
            new ItemPedido(paoDeQueijo, 2)
        );

        double total = pedidoService.calcularValorTotal(itens);

        assertEquals(46.80, total, 0.001);
    }

    @Test
    @DisplayName("Não deve aplicar desconto quando o valor bruto é exatamente igual ao limite (R$ 50,00)")
    void naoDeveAplicarDescontoNoLimiteExato() {

        Produto boloDeCenoura =
            new Produto(
                3,
                "Bolo de Cenoura",
                50.00,
                StatusProduto.ATIVO
            );

        List<ItemPedido> itens =
            Collections.singletonList(
                new ItemPedido(boloDeCenoura, 1)
            );

        double total = pedidoService.calcularValorTotal(itens);

        assertEquals(
            50.00,
            total,
            0.001,
            "O desconto só deve valer para valores ACIMA do limite, não iguais."
        );
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando a lista de itens é nula")
    void deveLancarExcecaoQuandoListaNula() {

        assertThrows(
            IllegalArgumentException.class,
            () -> pedidoService.calcularValorTotal(null)
        );
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException quando a lista de itens é vazia")
    void deveLancarExcecaoQuandoListaVazia() {

        assertThrows(
            IllegalArgumentException.class,
            () -> pedidoService.calcularValorTotal(Collections.emptyList())
        );
    }
}