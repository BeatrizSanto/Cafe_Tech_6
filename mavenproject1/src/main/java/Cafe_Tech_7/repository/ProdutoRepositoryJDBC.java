package Cafe_Tech_7.repository;


import Cafe_Tech_7.model.Produto;
import Cafe_Tech_7.model.StatusProduto;
import Cafe_Tech_7.service.exception.RepositorioException;
import Cafe_Tech_7.util.ConexaoFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class ProdutoRepositoryJDBC implements ProdutoRepository {

    private static final String SQL_LISTAR = "SELECT id, nome, valor, status FROM produtos";
    private static final String SQL_INSERIR = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
    private static final String SQL_ATUALIZAR = "UPDATE produtos SET nome=?, valor=?, status=? WHERE id=?";
    private static final String SQL_EXCLUIR = "DELETE FROM produtos WHERE id=?";

    @Override
    public List<Produto> listarTodos() throws RepositorioException {
        List<Produto> lista = new ArrayList<>();

        try (
            Connection conn = ConexaoFactory.obterConexao();
            PreparedStatement pstm = conn.prepareStatement(SQL_LISTAR);
            ResultSet rs = pstm.executeQuery();
        ) {
            while (rs.next()) {
                Produto p = new Produto(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getDouble("valor"),
                    StatusProduto.fromTexto(rs.getString("status"))
                );
                lista.add(p);
            }
        } catch (Exception e) {
            throw new RepositorioException("Erro ao listar produtos.", e);
        }

        return lista;
    }

    @Override
    public void inserir(Produto p) throws RepositorioException {
        try (
            Connection conn = ConexaoFactory.obterConexao();
            PreparedStatement pstm = conn.prepareStatement(SQL_INSERIR)
        ) {
            pstm.setString(1, p.getNome());
            pstm.setDouble(2, p.getValor());
            pstm.setString(3, p.getStatus().name());
            pstm.executeUpdate();
        } catch (Exception e) {
            throw new RepositorioException("Erro ao cadastrar produto.", e);
        }
    }

    @Override
    public void atualizar(Produto p) throws RepositorioException {
        try (
            Connection conn = ConexaoFactory.obterConexao();
            PreparedStatement pstm = conn.prepareStatement(SQL_ATUALIZAR)
        ) {
            pstm.setString(1, p.getNome());
            pstm.setDouble(2, p.getValor());
            pstm.setString(3, p.getStatus().name());
            pstm.setInt(4, p.getId());
            pstm.executeUpdate();
        } catch (Exception e) {
            throw new RepositorioException("Erro ao atualizar produto.", e);
        }
    }

    @Override
    public void excluir(int id) throws RepositorioException {
        try (
            Connection conn = ConexaoFactory.obterConexao();
            PreparedStatement pstm = conn.prepareStatement(SQL_EXCLUIR)
        ) {
            pstm.setInt(1, id);
            pstm.executeUpdate();
        } catch (Exception e) {
            throw new RepositorioException("Erro ao excluir produto.", e);
        }
    }
}
