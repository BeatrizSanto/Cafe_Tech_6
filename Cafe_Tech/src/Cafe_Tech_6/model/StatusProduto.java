package Cafe_Tech_6.model;

public enum StatusProduto {
    ATIVO,
    INATIVO;

 
    public static StatusProduto fromTexto(String texto) {
        if (texto == null) {
            return null;
        }
        return StatusProduto.valueOf(texto.trim().toUpperCase());
    }
}
