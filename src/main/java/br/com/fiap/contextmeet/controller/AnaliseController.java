package br.com.fiap.contextmeet.controller;

import br.com.fiap.contextmeet.bo.InsightBo;
import br.com.fiap.contextmeet.entities.Insight;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
@RequestMapping("/transcricoes")
public class AnaliseController {

    InsightBo insightBo = new InsightBo();

    // Analisar uma transcrição
    @PostMapping("/{idTranscricao}/analisar")
    public ResponseEntity<ArrayList<Insight>> analisarRs(@PathVariable int idTranscricao) throws SQLException, ClassNotFoundException {
        ArrayList<Insight> insights = insightBo.analisarTranscricaoBo(idTranscricao);

        return ResponseEntity.ok(insights);
    }
}
