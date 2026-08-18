package Cafe_Tech_6.model;

import java.util.Objects;

public class Produto {

    private int id;
    private String nome;
    private double valor;
    private StatusProduto status;

    public Produto() {
    }

    public Produto(String nome, double valor, StatusProduto status) {
        this.nome = nome;
        this.valor = valor;
        this.status = status;
    }

    public Produto(int id, String nome, double valor, StatusProduto status) {
        this(nome, valor, status);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public StatusProduto getStatus() {
        return status;
    }

    public void setStatus(StatusProduto status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produto)) return false;
        Produto produto = (Produto) o;
        return id == produto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Produto{id=" + id + ", nome='" + nome + "', valor=" + valor + ", status=" + status + "}";
    }
}
