package com.residenciaTst.AtividadePratica.controller;

import com.residenciaTst.AtividadePratica.dto.PautaDto;
import com.residenciaTst.AtividadePratica.model.Pauta;
import com.residenciaTst.AtividadePratica.model.Processo;
import com.residenciaTst.AtividadePratica.repository.PautaRepository;
import com.residenciaTst.AtividadePratica.service.PautaService;
import com.residenciaTst.AtividadePratica.service.ProcessoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Pautas", description = "Rotas sobre Pautas")
@RestController
@RequestMapping(path = "/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PautaController {

    @Autowired
    PautaService pautaService;

    @Autowired
    ProcessoService processoService;

    @Autowired
    PautaRepository pautaRepository;

    @Operation(summary = "Rota para buscar todas as pautas")
    @GetMapping(path = "/pautas")
    public ResponseEntity<List<Pauta>> listarTodos(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(pautaService.listarTodos());
    }

    @Operation(summary = "Rota para buscar uma pauta pelo ID", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "401", description = "Não autorizado",
            content = @Content)
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


    @Operation(summary = "Rota para buscar os processos no orgão judicante")
    @GetMapping(path = "/orgaoJudicante/{nameOrgao}")
    public ResponseEntity<Object> listarPautasPorOrgao(@PathVariable("nameOrgao") String name){
        List<Pauta> pautas = pautaRepository.findByOrgaoJudicante(name);

        if(pautas != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(pautas);
        } else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("nao foi encontrada nenhuma pauta!");
        }

    }

    @Operation(summary = "Rota para salvar uma pauta", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "401", description = "Não autorizado",
            content = @Content)
    @PostMapping(path = "/pauta")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Pauta> salvar(@RequestBody @Valid PautaDto pautaDto){
        Pauta pauta = Pauta.builder().build();
        BeanUtils.copyProperties(pautaDto, pauta);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pautaService.salvar(pauta));
    }

    @Operation(summary = "Rota para atualizar uma pauta", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "401", description = "Não autorizado",
            content = @Content)
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

    @Operation(summary = "Rota para deletar uma pauta pelo ID", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "401", description = "Não autorizado",
            content = @Content)
    @DeleteMapping("/pauta/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deletarPeloId(@PathVariable UUID id){
        if(pautaService.deletarPeloId(id)){
            return ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado");
        }
    }

    @Operation(summary = "Rota para vincular um processo a uma pauta", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "401", description = "Não autorizado",
            content = @Content)
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

    @Operation(summary = "Rota para vincular vários processos a uma pauta", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "401", description = "Não autorizado",
            content = @Content)
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

    @Operation(summary = "Rota para desvincular um processo a uma pauta", security = @SecurityRequirement(name = "bearerAuth"))
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
