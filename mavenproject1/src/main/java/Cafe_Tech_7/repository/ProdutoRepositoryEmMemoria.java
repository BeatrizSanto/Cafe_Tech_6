
package Cafe_Tech_7.repository;


import Cafe_Tech_7.model.Produto;
import Cafe_Tech_7.service.exception.RepositorioException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class ProdutoRepositoryEmMemoria implements ProdutoRepository {

    private final List<Produto> produtos = new ArrayList<>();
    private final AtomicInteger proximoId = new AtomicInteger(1);

    @Override
    public List<Produto> listarTodos() throws RepositorioException {
        return new ArrayList<>(produtos);
    }

    @Override
    public void inserir(Produto produto) throws RepositorioException {
        produto.setId(proximoId.getAndIncrement());
        produtos.add(produto);
    }

    @Override
    public void atualizar(Produto produto) throws RepositorioException {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getId() == produto.getId()) {
                produtos.set(i, produto);
                return;
            }
        }
        throw new RepositorioException("Produto com id " + produto.getId() + " não encontrado.");
    }

    @Override
    public void excluir(int id) throws RepositorioException {
        boolean removido = produtos.removeIf(p -> p.getId() == id);
        if (!removido) {
            throw new RepositorioException("Produto com id " + id + " não encontrado.");
        }
    }
}