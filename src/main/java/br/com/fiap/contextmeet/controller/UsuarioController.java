package br.com.fiap.contextmeet.controller;

import br.com.fiap.contextmeet.bo.UsuarioBo;
import br.com.fiap.contextmeet.entities.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    UsuarioBo usuarioBo = new UsuarioBo();

    // Select
    @GetMapping
    public ArrayList<Usuario> selecionarRs() throws SQLException, ClassNotFoundException {

        ArrayList<Usuario> usuarios = usuarioBo.selecionarBo();

        for (Usuario usuario : usuarios) {
            usuario.setSenha(null);
        }

        return usuarios;
    }

    // Buscar por ID
    @GetMapping("/{idUsuario}")
    public Usuario buscarPorIdRs(@PathVariable int idUsuario) throws SQLException, ClassNotFoundException {
        Usuario usuario = usuarioBo.buscarPorIdBo(idUsuario);
        usuario.setSenha(null);

        return usuario;
    }

    // Insert
    @PostMapping
    public ResponseEntity<Void> inserirRs(@RequestBody Usuario usuario) throws SQLException, ClassNotFoundException {
        int idUsuario = usuarioBo.inserirBo(usuario);
        URI location = URI.create("/usuarios/" + idUsuario);

        return ResponseEntity.created(location).build();
    }

    // Update
    @PutMapping("/{idUsuario}")
    public ResponseEntity<Void> atualizarRs(@PathVariable int idUsuario, @RequestBody Usuario usuario) throws SQLException, ClassNotFoundException {
        usuario.setIdUsuario(idUsuario);
        usuarioBo.atualizarBo(usuario);

        return ResponseEntity.ok().build();
    }

    // Delete
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> deletarRs(@PathVariable int idUsuario) throws SQLException, ClassNotFoundException {
        usuarioBo.deletarBo(idUsuario);

        return ResponseEntity.noContent().build();
    }
}
