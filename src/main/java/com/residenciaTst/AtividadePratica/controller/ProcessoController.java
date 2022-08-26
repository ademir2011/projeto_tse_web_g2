package com.residenciaTst.AtividadePratica.controller;

import com.residenciaTst.AtividadePratica.model.Processo;
import com.residenciaTst.AtividadePratica.service.ProcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api")
public class ProcessoController {

    @Autowired
    ProcessoService processoService;

    @GetMapping(path = "/processos")
    public ResponseEntity<List<Processo>> listarTodos(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(processoService.listarTodos());
    }

    @GetMapping(path = "/processo/{id}")
    public ResponseEntity<Processo> listarPeloId(@PathVariable UUID id){
        Processo processo = processoService.listarPeloId(id);
        if(processo != null){
            return ResponseEntity.status(HttpStatus.OK).body(processo);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping(path = "/processo")
    public ResponseEntity<Processo> salvar(@RequestBody Processo processo){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(processoService.salvar(processo));
    }

    @PutMapping(path = "/processo/{id}")
    public ResponseEntity<Processo> atualizar(@PathVariable UUID id,  @RequestBody Processo processo){
        Processo processoAtualizado = processoService.atualizar(id, processo);
        if(processoAtualizado != null){
            return ResponseEntity.status(HttpStatus.OK).body(processo);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/processo/{id}")
    public ResponseEntity<?> deletarPeloId(@PathVariable UUID id){
        if(processoService.deletarPeloId(id)){
            return ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado");
        }
    }

}
