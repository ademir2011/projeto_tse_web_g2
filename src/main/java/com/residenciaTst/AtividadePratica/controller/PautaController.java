package com.residenciaTst.AtividadePratica.controller;

import com.residenciaTst.AtividadePratica.dto.PautaDto;
import com.residenciaTst.AtividadePratica.model.Pauta;
import com.residenciaTst.AtividadePratica.model.Processo;
import com.residenciaTst.AtividadePratica.service.PautaService;
import com.residenciaTst.AtividadePratica.service.ProcessoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

//@CrossOrigin(originPatterns = "${spring.application.originPatterns}",
//        allowCredentials = "true", maxAge = 3600)
@RestController
@RequestMapping(path = "/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PautaController {

    @Autowired
    PautaService pautaService;

    @Autowired
    ProcessoService processoService;

    @GetMapping(path = "/pautas")
    public ResponseEntity<List<Pauta>> listarTodos(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(pautaService.listarTodos());
    }

    @GetMapping(path = "/pauta/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Pauta> listarPeloId(@PathVariable UUID id){
        Pauta pauta = pautaService.listarPeloId(id);
        if(pauta != null){
            return ResponseEntity.status(HttpStatus.OK).body(pauta);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping(path = "/pauta")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Pauta> salvar(@RequestBody @Valid PautaDto pautaDto){
        Pauta pauta = Pauta.builder().build();
        BeanUtils.copyProperties(pautaDto, pauta);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pautaService.salvar(pauta));
    }

    @PutMapping(path = "/pauta/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Pauta> atualizar(@PathVariable UUID id,  @RequestBody Pauta pauta){
        Pauta pautaAtualizado = pautaService.atualizar(id, pauta);
        if(pautaAtualizado != null){
            return ResponseEntity.status(HttpStatus.OK).body(pauta);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/pauta/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deletarPeloId(@PathVariable UUID id){
        if(pautaService.deletarPeloId(id)){
            return ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado");
        }
    }

    @PostMapping(path = "/pauta/{id}/processo")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Pauta> vincularProceso(@PathVariable UUID id, @RequestBody Processo processo){
        Pauta pauta = pautaService.listarPeloId(id);
        Processo processoBuscado = processoService.listarPeloId(processo.getId());
        if((pauta != null) && (processo) != null){
            return ResponseEntity.status(HttpStatus.CREATED)
                            .body(pautaService.vincularProcesso(pauta, processoBuscado));
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping(path = "/pauta/{id}/listProcessos")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Pauta> vincularListProceso(@PathVariable UUID id, @RequestBody List<Processo> processos){
        Pauta pauta = pautaService.listarPeloId(id);

        if((pauta != null)){
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(pautaService.vincularListProcesso(pauta, processos));
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @DeleteMapping(path = "/pauta/{id}/processo")
//    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Pauta> desvincularProcesso(@PathVariable UUID id, @RequestBody Processo processo){
        Pauta pauta = pautaService.listarPeloId(id);
        Processo processoBuscado = processoService.listarPeloId(processo.getId());
        if((pauta != null) && (processo) != null){
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(pautaService.desvincularProcesso(pauta, processoBuscado));
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }



}
