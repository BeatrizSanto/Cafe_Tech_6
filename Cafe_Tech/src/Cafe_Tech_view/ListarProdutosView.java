
package Cafe_Tech_view;

import Cafe_Tech_6.model.Produto;
import Cafe_Tech_6.model.StatusProduto;
import Cafe_Tech_6.repository.ProdutoRepositoryJDBC;
import Cafe_Tech_6.service.ProdutoService;
import Cafe_Tech_6.service.exception.ProdutoInvalidoException;
import Cafe_Tech_6.service.exception.RepositorioException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class ListarProdutosView extends JFrame {

    private final ProdutoService produtoService;

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JTextField campoId;
    private JTextField campoNome;
    private JTextField campoValor;
    private JComboBox<StatusProduto> campoStatus;

    public ListarProdutosView(ProdutoService produtoService) {
        this.produtoService = produtoService;
        initComponents();
        carregarTabela();
    }

    private void initComponents() {
        setTitle("Lista de Produtos - Cafe Tech");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        modeloTabela = new DefaultTableModel(new String[]{"ID", "Nome", "Status", "Valor"}, 0);
        tabela = new JTable(modeloTabela);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel painelFormulario = new JPanel(new GridLayout(4, 2, 5, 5));
        painelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        campoId = new JTextField();
        campoNome = new JTextField();
        campoValor = new JTextField();
        campoStatus = new JComboBox<>(StatusProduto.values());

        painelFormulario.add(new JLabel("ID (atualizar/excluir):"));
        painelFormulario.add(campoId);
        painelFormulario.add(new JLabel("Nome:"));
        painelFormulario.add(campoNome);
        painelFormulario.add(new JLabel("Valor:"));
        painelFormulario.add(campoValor);
        painelFormulario.add(new JLabel("Status:"));
        painelFormulario.add(campoStatus);

        JPanel painelBotoes = new JPanel(new FlowLayout());
        JButton botaoCadastrar = new JButton("Cadastrar");
        JButton botaoAtualizar = new JButton("Atualizar");
        JButton botaoExcluir = new JButton("Excluir");

        botaoCadastrar.addActionListener(e -> cadastrar());
        botaoAtualizar.addActionListener(e -> atualizar());
        botaoExcluir.addActionListener(e -> excluir());

        painelBotoes.add(botaoCadastrar);
        painelBotoes.add(botaoAtualizar);
        painelBotoes.add(botaoExcluir);

        JPanel painelInferior = new JPanel(new BorderLayout());
        painelInferior.add(painelFormulario, BorderLayout.CENTER);
        painelInferior.add(painelBotoes, BorderLayout.SOUTH);

        add(painelInferior, BorderLayout.SOUTH);

        setSize(560, 420);
        setLocationRelativeTo(null);
    }

    private void carregarTabela() {
        try {
            List<Produto> lista = produtoService.listarProdutos();
            modeloTabela.setRowCount(0);
            for (Produto p : lista) {
                modeloTabela.addRow(new Object[]{p.getId(), p.getNome(), p.getStatus(), p.getValor()});
            }
        } catch (RepositorioException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + e.getMessage());
        }
    }

    private void cadastrar() {
        try {
            Produto produto = new Produto(
                campoNome.getText(),
                parseValor(campoValor.getText()),
                (StatusProduto) campoStatus.getSelectedItem()
            );
            produtoService.cadastrarProduto(produto);
            JOptionPane.showMessageDialog(this, "Produto cadastrado!");
            carregarTabela();
        } catch (ProdutoInvalidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (RepositorioException e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    private void atualizar() {
        try {
            Produto produto = new Produto(
                Integer.parseInt(campoId.getText()),
                campoNome.getText(),
                parseValor(campoValor.getText()),
                (StatusProduto) campoStatus.getSelectedItem()
            );
            produtoService.atualizarProduto(produto);
            JOptionPane.showMessageDialog(this, "Produto atualizado!");
            carregarTabela();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Informe um ID válido.");
        } catch (ProdutoInvalidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (RepositorioException e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar produto: " + e.getMessage());
        }
    }

    private void excluir() {
        try {
            int id = Integer.parseInt(campoId.getText());
            produtoService.excluirProduto(id);
            JOptionPane.showMessageDialog(this, "Produto excluído com sucesso!");
            carregarTabela();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Informe um ID válido.");
        } catch (ProdutoInvalidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (RepositorioException e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir produto: " + e.getMessage());
        }
    }

    private double parseValor(String texto) {
        try {
            return Double.parseDouble(texto.replace(",", "."));
        } catch (NumberFormatException e) {
            throw new ProdutoInvalidoException("Informe um valor numérico válido.");
        }
    }

    public static void main(String[] args) {
        ProdutoService produtoService = new ProdutoService(new ProdutoRepositoryJDBC());
        SwingUtilities.invokeLater(() -> new ListarProdutosView(produtoService).setVisible(true));
    }
}
