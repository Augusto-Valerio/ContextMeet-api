package br.com.fiap.contextmeet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> inicio() {
        return Map.of(
                "aplicacao", "ContextMeet API",
                "status", "online",
                "endpoints", List.of("/usuarios", "/funcionarios", "/reunioes", "/transcricoes", "/insights")
        );
    }
}
