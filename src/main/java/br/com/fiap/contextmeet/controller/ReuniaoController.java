package br.com.fiap.contextmeet.controller;

import br.com.fiap.contextmeet.bo.ReuniaoBo;
import br.com.fiap.contextmeet.entities.Reuniao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping("/reunioes")
public class ReuniaoController {

    ReuniaoBo reuniaoBo = new ReuniaoBo();

    // Select
    @GetMapping
    public ArrayList<Reuniao> selecionarRs() throws SQLException, ClassNotFoundException {
        return reuniaoBo.selecionarBo();
    }

    // Buscar por ID
    @GetMapping("/{idReuniao}")
    public Reuniao buscarPorIdRs(@PathVariable int idReuniao) throws SQLException, ClassNotFoundException {
        return reuniaoBo.buscarPorIdBo(idReuniao);
    }

    // Buscar reuniões de um funcionário
    @GetMapping("/funcionario/{idFuncionario}")
    public ArrayList<Reuniao> buscarPorIdFuncionarioRs(@PathVariable int idFuncionario) throws SQLException, ClassNotFoundException {
        return reuniaoBo.buscarPorIdFuncionarioBo(idFuncionario);
    }

    // Insert
    @PostMapping
    public ResponseEntity<Void> inserirRs(@RequestBody Reuniao reuniao) throws SQLException, ClassNotFoundException {
        int idReuniao = reuniaoBo.inserirBo(reuniao);

        URI location = URI.create("/reunioes/" + idReuniao);

        return ResponseEntity.created(location).build();
    }

    // Update
    @PutMapping("/{idReuniao}")
    public ResponseEntity<Void> atualizarRs(@PathVariable int idReuniao, @RequestBody Reuniao reuniao) throws SQLException, ClassNotFoundException {
        reuniao.setIdReuniao(idReuniao);

        reuniaoBo.atualizarBo(reuniao);

        return ResponseEntity.ok().build();
    }

    // Delete
    @DeleteMapping("/{idReuniao}")
    public ResponseEntity<Void> deletarRs(@PathVariable int idReuniao) throws SQLException, ClassNotFoundException {
        reuniaoBo.deletarBo(idReuniao);

        return ResponseEntity.noContent().build();
    }
}
