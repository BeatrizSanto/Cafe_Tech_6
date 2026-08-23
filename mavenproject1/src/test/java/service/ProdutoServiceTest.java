package service;

import Cafe_Tech_7.model.Produto;
import Cafe_Tech_7.model.StatusProduto;
import Cafe_Tech_7.repository.ProdutoRepository;
import Cafe_Tech_7.repository.ProdutoRepositoryEmMemoria;
import Cafe_Tech_7.service.exception.ProdutoInvalidoException;
import Cafe_Tech_7.service.exception.RepositorioException;
import Cafe_Tech_7.servicee.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DisplayName("ProdutoService - validações de negócio (sem banco de dados)")
class ProdutoServiceTest {

    private ProdutoService produtoService;

    @BeforeEach
    void configurar() {
        ProdutoRepository repositorioEmMemoria = new ProdutoRepositoryEmMemoria();
        produtoService = new ProdutoService(repositorioEmMemoria);
    }

    @Test
    @DisplayName("Deve cadastrar um produto válido com sucesso")
    void deveCadastrarProdutoValido() throws RepositorioException {
        produtoService.cadastrarProduto(new Produto("Café Expresso", 8.50, StatusProduto.ATIVO));

        List<Produto> produtos = produtoService.listarProdutos();

        assertEquals(1, produtos.size());
        assertEquals("Café Expresso", produtos.get(0).getNome());
    }

    @Test
    @DisplayName("Deve rejeitar produto com nome em branco")
    void deveRejeitarNomeEmBranco() {
        Produto produtoInvalido = new Produto("   ", 5.0, StatusProduto.ATIVO);

        assertThrows(ProdutoInvalidoException.class, () -> produtoService.cadastrarProduto(produtoInvalido));
    }

    @Test
    @DisplayName("Deve rejeitar produto com valor menor ou igual a zero")
    void deveRejeitarValorNaoPositivo() {
        Produto produtoInvalido = new Produto("Café Gelado", 0.0, StatusProduto.ATIVO);

        assertThrows(ProdutoInvalidoException.class, () -> produtoService.cadastrarProduto(produtoInvalido));
    }

    @Test
    @DisplayName("Deve rejeitar produto com status nulo")
    void deveRejeitarStatusNulo() {
        Produto produtoInvalido = new Produto("Café Coado", 6.0, null);

        assertThrows(ProdutoInvalidoException.class, () -> produtoService.cadastrarProduto(produtoInvalido));
    }

    @Test
    @DisplayName("Deve rejeitar exclusão com ID inválido (menor ou igual a zero)")
    void deveRejeitarExclusaoComIdInvalido() {
        assertThrows(ProdutoInvalidoException.class, () -> produtoService.excluirProduto(0));
    }
}
