
package Cafe_Tech_6.service;

import Cafe_Tech_6.model.Usuario;


public class UsuarioService {

    public void cadastrar(Usuario usuario) {
        System.out.println("Usuário cadastrado: " + usuario.getNome());
    }
}
