package Cafe_Tech_7.servicee;

import Cafe_Tech_7.model.Usuario;


public class UsuarioService {

    public void cadastrar(Usuario usuario) {
        System.out.println("Usuário cadastrado: " + usuario.getNome());
    }
}
