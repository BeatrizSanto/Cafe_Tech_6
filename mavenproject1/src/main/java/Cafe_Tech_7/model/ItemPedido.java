package Cafe_Tech_7.model;


public class ItemPedido {

    private final Produto produto;
    private final int quantidade;

    public ItemPedido(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    /** Subtotal deste item (valor unitário do produto x quantidade). */
    public double getSubtotal() {
        return produto.getValor() * quantidade;
    }
}
