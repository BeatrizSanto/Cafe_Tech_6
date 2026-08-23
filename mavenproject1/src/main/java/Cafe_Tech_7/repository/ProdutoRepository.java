package Cafe_Tech_7.repository;

import Cafe_Tech_7.model.Produto;
import Cafe_Tech_7.service.exception.RepositorioException;
import java.util.List;


public interface ProdutoRepository {

    List<Produto> listarTodos() throws RepositorioException;

    void inserir(Produto produto) throws RepositorioException;

    void atualizar(Produto produto) throws RepositorioException;

    void excluir(int id) throws RepositorioException;
}


