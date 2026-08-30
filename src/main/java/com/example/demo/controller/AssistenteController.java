package com.example.demo.controller;

import com.example.demo.service.AssistenteFinanceiroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/orcamento")
public class AssistenteController {

    private final AssistenteFinanceiroService assistenteService;

    public AssistenteController(AssistenteFinanceiroService assistenteService) {
        this.assistenteService = assistenteService;
    }

    @PostMapping("/texto")
    public ResponseEntity<String> processarComandoTexto(@RequestBody String comando) {
        String resposta = assistenteService.processarTexto(comando);
        return ResponseEntity.ok(resposta);
    }

    @PostMapping(value = "/audio", consumes = "multipart/form-data")
    public ResponseEntity<String> processarComandoAudio(@RequestParam("file") MultipartFile arquivo) {
        String resposta = assistenteService.processarAudio(arquivo.getResource());
        return ResponseEntity.ok(resposta);
    }
}