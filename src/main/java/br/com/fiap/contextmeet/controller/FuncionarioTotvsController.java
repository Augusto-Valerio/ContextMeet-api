package br.com.fiap.contextmeet.controller;

import br.com.fiap.contextmeet.bo.FuncionarioTotvsBo;
import br.com.fiap.contextmeet.entities.FuncionarioTotvs;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioTotvsController {

    FuncionarioTotvsBo funcionarioBo = new FuncionarioTotvsBo();

    // Select
    @GetMapping
    public ArrayList<FuncionarioTotvs> selecionarRs() throws SQLException, ClassNotFoundException {
        return funcionarioBo.selecionarBo();
    }

    // Buscar por ID
    @GetMapping("/{idFuncionario}")
    public FuncionarioTotvs buscarPorIdRs(@PathVariable int idFuncionario) throws SQLException, ClassNotFoundException {
        return funcionarioBo.buscarPorIdBo(idFuncionario);
    }

    // Buscar pelo ID do usuário
    @GetMapping("/usuario/{idUsuario}")
    public FuncionarioTotvs buscarPorIdUsuarioRs(@PathVariable int idUsuario) throws SQLException, ClassNotFoundException {
        return funcionarioBo.buscarPorIdUsuarioBo(idUsuario);
    }

    // Insert
    @PostMapping
    public ResponseEntity<Void> inserirRs(@RequestBody FuncionarioTotvs funcionario) throws SQLException, ClassNotFoundException {
        int idFuncionario = funcionarioBo.inserirBo(funcionario);
        URI location = URI.create("/funcionarios/" + idFuncionario);

        return ResponseEntity.created(location).build();
    }

    // Update
    @PutMapping("/{idFuncionario}")
    public ResponseEntity<Void> atualizarRs(@PathVariable int idFuncionario, @RequestBody FuncionarioTotvs funcionario) throws SQLException, ClassNotFoundException {
        funcionario.setIdFuncionario(idFuncionario);
        funcionarioBo.atualizarBo(funcionario);

        return ResponseEntity.ok().build();
    }

    // Delete
    @DeleteMapping("/{idFuncionario}")
    public ResponseEntity<Void> deletarRs(@PathVariable int idFuncionario) throws SQLException, ClassNotFoundException {
        funcionarioBo.deletarBo(idFuncionario);

        return ResponseEntity.noContent().build();
    }
}
