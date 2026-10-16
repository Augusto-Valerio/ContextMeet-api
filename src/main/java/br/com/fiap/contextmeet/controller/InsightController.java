package br.com.fiap.contextmeet.controller;

import br.com.fiap.contextmeet.bo.InsightBo;
import br.com.fiap.contextmeet.entities.Insight;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping("/insights")
public class InsightController {

    InsightBo insightBo = new InsightBo();

    // Select
    @GetMapping
    public ArrayList<Insight> selecionarRs() throws SQLException, ClassNotFoundException {
        return insightBo.selecionarBo();
    }

    // Buscar por ID
    @GetMapping("/{idInsight}")
    public Insight buscarPorIdRs(@PathVariable int idInsight) throws SQLException, ClassNotFoundException {
        return insightBo.buscarPorIdBo(idInsight);
    }

    // Buscar insights de uma transcrição
    @GetMapping("/transcricao/{idTranscricao}")
    public ArrayList<Insight> buscarPorIdTranscricaoRs(@PathVariable int idTranscricao) throws SQLException, ClassNotFoundException {
        return insightBo.buscarPorIdTranscricaoBo(idTranscricao);
    }

    // Insert
    @PostMapping
    public ResponseEntity<Void> inserirRs(@RequestBody Insight insight) throws SQLException, ClassNotFoundException {
        int idInsight = insightBo.inserirBo(insight);
        URI location = URI.create("/insights/" + idInsight);

        return ResponseEntity.created(location).build();
    }

    // Update
    @PutMapping("/{idInsight}")
    public ResponseEntity<Void> atualizarRs(@PathVariable int idInsight, @RequestBody Insight insight) throws SQLException, ClassNotFoundException {
        insight.setIdInsight(idInsight);
        insightBo.atualizarBo(insight);

        return ResponseEntity.ok().build();
    }

    // Delete
    @DeleteMapping("/{idInsight}")
    public ResponseEntity<Void> deletarRs(@PathVariable int idInsight) throws SQLException, ClassNotFoundException {
        insightBo.deletarBo(idInsight);

        return ResponseEntity.noContent().build();
    }
}
