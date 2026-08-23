package Cafe_Tech_view;



import Cafe_Tech_6.model.Pedido;
import Cafe_Tech_6.model.Produto;
import Cafe_Tech_6.model.StatusProduto;
import Cafe_Tech_6.model.Usuario;
import Cafe_Tech_6.repository.ProdutoRepository;
import Cafe_Tech_6.repository.ProdutoRepositoryEmMemoria;
import Cafe_Tech_6.repository.ProdutoRepositoryJDBC;
import Cafe_Tech_6.service.PedidoService;
import Cafe_Tech_6.service.ProdutoService;
import Cafe_Tech_6.service.UsuarioService;
import Cafe_Tech_6.service.exception.ProdutoInvalidoException;
import Cafe_Tech_6.service.exception.RepositorioException;

import java.util.List;


public class Main {

    public static void main(String[] args) {
        testarRegrasDeNegocioSemBanco();
        testarConexaoComBancoReal();
        testarUsuarioEPedido();
    }

    private static void testarRegrasDeNegocioSemBanco() {
        System.out.println("=== Testes de regra de negócio (repositório em memória) ===");

        ProdutoRepository repositorio = new ProdutoRepositoryEmMemoria();
        ProdutoService produtoService = new ProdutoService(repositorio);

        // Teste 1: cadastro válido
        try {
            produtoService.cadastrarProduto(new Produto("Café Expresso", 8.50, StatusProduto.ATIVO));
            System.out.println("[OK] Produto válido cadastrado com sucesso.");
        } catch (Exception e) {
            System.out.println("[FALHA] " + e.getMessage());
        }

        // Teste 2: nome vazio deve ser rejeitado
        try {
            produtoService.cadastrarProduto(new Produto("", 5.0, StatusProduto.ATIVO));
            System.out.println("[FALHA] Deveria ter lançado exceção para nome vazio.");
        } catch (ProdutoInvalidoException e) {
            System.out.println("[OK] Validação de nome vazio funcionando: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[FALHA] Exceção inesperada: " + e.getMessage());
        }

        // Teste 3: valor negativo deve ser rejeitado
        try {
            produtoService.cadastrarProduto(new Produto("Café Gelado", -3.0, StatusProduto.ATIVO));
            System.out.println("[FALHA] Deveria ter lançado exceção para valor negativo.");
        } catch (ProdutoInvalidoException e) {
            System.out.println("[OK] Validação de valor inválido funcionando: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[FALHA] Exceção inesperada: " + e.getMessage());
        }

        // Teste 4: listagem
        try {
            List<Produto> lista = produtoService.listarProdutos();
            System.out.println("[OK] Produtos no repositório em memória: " + lista.size());
            lista.forEach(p -> System.out.println("       " + p));
        } catch (Exception e) {
            System.out.println("[FALHA] " + e.getMessage());
        }

        // Teste 5: atualização
        try {
            List<Produto> lista = produtoService.listarProdutos();
            Produto primeiro = lista.get(0);
            primeiro.setValor(9.90);
            produtoService.atualizarProduto(primeiro);
            System.out.println("[OK] Produto atualizado com sucesso (novo valor: " + primeiro.getValor() + ").");
        } catch (Exception e) {
            System.out.println("[FALHA] " + e.getMessage());
        }

        // Teste 6: exclusão
        try {
            List<Produto> lista = produtoService.listarProdutos();
            int idParaExcluir = lista.get(0).getId();
            produtoService.excluirProduto(idParaExcluir);
            System.out.println("[OK] Produto " + idParaExcluir + " excluído com sucesso. Restantes: "
                + produtoService.listarProdutos().size());
        } catch (Exception e) {
            System.out.println("[FALHA] " + e.getMessage());
        }

        System.out.println();
    }

    private static void testarConexaoComBancoReal() {
        System.out.println("=== Teste com banco de dados real (MySQL) ===");
        try {
            ProdutoRepository repositorioReal = new ProdutoRepositoryJDBC();
            ProdutoService servicoReal = new ProdutoService(repositorioReal);
            List<Produto> produtosReais = servicoReal.listarProdutos();
            System.out.println("[OK] Conexão com o banco funcionando. Produtos encontrados: " + produtosReais.size());
        } catch (RepositorioException e) {
            System.out.println("[AVISO] Não foi possível conectar ao banco (esperado se o MySQL "
                + "não estiver rodando localmente): " + e.getMessage());
        }
        System.out.println();
    }

    private static void testarUsuarioEPedido() {
        System.out.println("=== Testes das classes Usuario e Pedido ===");

        UsuarioService usuarioService = new UsuarioService();
        usuarioService.cadastrar(new Usuario(1, "Beatriz", "bia@email.com"));

        PedidoService pedidoService = new PedidoService();
        pedidoService.finalizar(new Pedido(101, 250.00));
    }
}
