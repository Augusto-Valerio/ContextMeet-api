package br.com.fiap.contextmeet.controller;

import br.com.fiap.contextmeet.bo.UsuarioBo;
import br.com.fiap.contextmeet.entities.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.sql.SQLException;

@RestController
@RequestMapping("/auth")
public class LoginController {

    UsuarioBo usuarioBo = new UsuarioBo();

    // Login
    @PostMapping("/login")
    public ResponseEntity<Usuario> loginRs(@RequestBody Usuario credenciais) throws SQLException, ClassNotFoundException {
        Usuario usuario = usuarioBo.autenticarBo(credenciais.getEmail(), credenciais.getSenha());
        usuario.setSenha(null);

        return ResponseEntity.ok(usuario);
    }

    // Cadastro
    @PostMapping("/cadastro")
    public ResponseEntity<Void> cadastrarRs(@RequestBody Usuario usuario) throws SQLException, ClassNotFoundException {
        int idUsuario = usuarioBo.inserirBo(usuario);
        URI location = URI.create("/usuarios/" + idUsuario);

        return ResponseEntity.created(location).build();
    }
}
