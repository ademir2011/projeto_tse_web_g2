package com.residenciaTst.AtividadePratica.controller;

import com.residenciaTst.AtividadePratica.model.Pauta;
import com.residenciaTst.AtividadePratica.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api")
public class PautaController {

    @Autowired
    PautaService pautaService;

    @GetMapping(path = "/pautas")
    public ResponseEntity<List<Pauta>> listarTodos(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(pautaService.listarTodos());
    }

    @GetMapping(path = "/pauta/{id}")
    public ResponseEntity<Pauta> listarPeloId(@PathVariable UUID id){
        Pauta pauta = pautaService.listarPeloId(id);
        if(pauta != null){
            return ResponseEntity.status(HttpStatus.OK).body(pauta);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping(path = "/pauta")
    public ResponseEntity<Pauta> salvar(@RequestBody Pauta pauta){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pautaService.salvar(pauta));
    }

    @PutMapping(path = "/pauta/{id}")
    public ResponseEntity<Pauta> atualizar(@PathVariable UUID id,  @RequestBody Pauta pauta){
        Pauta pautaAtualizado = pautaService.atualizar(id, pauta);
        if(pautaAtualizado != null){
            return ResponseEntity.status(HttpStatus.OK).body(pauta);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/pauta/{id}")
    public ResponseEntity<?> deletarPeloId(@PathVariable UUID id){
        if(pautaService.deletarPeloId(id)){
            return ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado");
        }
    }

}
