package Cafe_Tech_7.servicee;

import Cafe_Tech_7.model.Produto;
import Cafe_Tech_7.repository.ProdutoRepository;
import Cafe_Tech_7.service.exception.ProdutoInvalidoException;
import Cafe_Tech_7.service.exception.RepositorioException;

import java.util.List;

public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public List<Produto> listarProdutos() throws RepositorioException {
        return repository.listarTodos();
    }

    public void cadastrarProduto(Produto produto) throws RepositorioException {
        validar(produto);
        repository.inserir(produto);
    }

    public void atualizarProduto(Produto produto) throws RepositorioException {
        if (produto.getId() <= 0) {
            throw new ProdutoInvalidoException("ID do produto inválido.");
        }
        validar(produto);
        repository.atualizar(produto);
    }

    public void excluirProduto(int id) throws RepositorioException {
        if (id <= 0) {
            throw new ProdutoInvalidoException("ID do produto inválido.");
        }
        repository.excluir(id);
    }

   
    private void validar(Produto produto) {
        if (produto.getNome() == null || produto.getNome().isBlank()) {
            throw new ProdutoInvalidoException("Nome do produto é obrigatório.");
        }
        if (produto.getValor() <= 0) {
            throw new ProdutoInvalidoException("Valor deve ser maior que zero.");
        }
        if (produto.getStatus() == null) {
            throw new ProdutoInvalidoException("Status do produto é obrigatório.");
        }
    }
}
