package br.com.fiap.contextmeet.controller;

import br.com.fiap.contextmeet.bo.TranscricaoBo;
import br.com.fiap.contextmeet.entities.Transcricao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping("/transcricoes")
public class TranscricaoController {

    TranscricaoBo transcricaoBo = new TranscricaoBo();

    // Select
    @GetMapping
    public ArrayList<Transcricao> selecionarRs() throws SQLException, ClassNotFoundException {
        return transcricaoBo.selecionarBo();
    }

    // Buscar por ID
    @GetMapping("/{idTranscricao}")
    public Transcricao buscarPorIdRs(@PathVariable int idTranscricao) throws SQLException, ClassNotFoundException {
        return transcricaoBo.buscarPorIdBo(idTranscricao);
    }

    // Buscar transcrições de uma reunião
    @GetMapping("/reuniao/{idReuniao}")
    public ArrayList<Transcricao> buscarPorIdReuniaoRs(@PathVariable int idReuniao) throws SQLException, ClassNotFoundException {
        return transcricaoBo.buscarPorIdReuniaoBo(idReuniao);
    }

    // Insert
    @PostMapping
    public ResponseEntity<Void> inserirRs(@RequestBody Transcricao transcricao) throws SQLException, ClassNotFoundException {
        int idTranscricao = transcricaoBo.inserirBo(transcricao);
        URI location = URI.create("/transcricoes/" + idTranscricao);

        return ResponseEntity.created(location).build();
    }

    // Update
    @PutMapping("/{idTranscricao}")
    public ResponseEntity<Void> atualizarRs(@PathVariable int idTranscricao, @RequestBody Transcricao transcricao) throws SQLException, ClassNotFoundException {
        transcricao.setIdTranscricao(idTranscricao);
        transcricaoBo.atualizarBo(transcricao);

        return ResponseEntity.ok().build();
    }

    // Delete
    @DeleteMapping("/{idTranscricao}")
    public ResponseEntity<Void> deletarRs(@PathVariable int idTranscricao) throws SQLException, ClassNotFoundException {
        transcricaoBo.deletarBo(idTranscricao);

        return ResponseEntity.noContent().build();
    }
}
