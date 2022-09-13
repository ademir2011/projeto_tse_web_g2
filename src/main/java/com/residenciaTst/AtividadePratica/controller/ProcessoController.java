package com.residenciaTst.AtividadePratica.controller;

import com.residenciaTst.AtividadePratica.dto.ProcessoDto;
import com.residenciaTst.AtividadePratica.model.Processo;
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

//@CrossOrigin(originPatterns = "${spring.application.originPatterns}", allowCredentials = "true", maxAge = 3600)

@Tag(name = "Processos", description = "Rotas sobre Processos")
@RestController
@RequestMapping(path = "/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProcessoController {

    @Autowired
    ProcessoService processoService;

    @Operation(summary = "Rota para buscar todos os processos")
    @GetMapping(path = "/processos")
    public ResponseEntity<List<Processo>> listarTodos(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(processoService.listarTodos());
    }

    @Operation(summary = "Rota para buscar os processos sem vinculos")
    @GetMapping(path = "/processosSemVinculo")
    public ResponseEntity<List<Processo>> listarTodosSemVinculo(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(processoService.listarTodosSemVinculo());
    }

    @Operation(summary = "Rota para buscar um processo pelo ID", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "401", description = "Não autorizado",
            content = @Content)
    @GetMapping(path = "/processo/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Processo> listarPeloId(@PathVariable UUID id){
        Processo processo = processoService.listarPeloId(id);
        if(processo != null){
            return ResponseEntity.status(HttpStatus.OK).body(processo);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Rota para salvar um processo", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "401", description = "Não autorizado",
            content = @Content)
    @PostMapping(path = "/processo")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Processo> salvar(@RequestBody @Valid ProcessoDto processoDto){
        Processo processo = Processo.builder().build();
        BeanUtils.copyProperties(processoDto, processo);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(processoService.salvar(processo));
    }

    @Operation(summary = "Rota para atualizar um processo", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "401", description = "Não autorizado",
            content = @Content)
    @PutMapping(path = "/processo/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Processo> atualizar(@PathVariable UUID id,  @RequestBody Processo processo){
        Processo processoAtualizado = processoService.atualizar(id, processo);
        if(processoAtualizado != null){
            return ResponseEntity.status(HttpStatus.OK).body(processo);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Rota para deletar um processo", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "401", description = "Não autorizado",
            content = @Content)
    @DeleteMapping("/processo/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deletarPeloId(@PathVariable UUID id){
        if(processoService.deletarPeloId(id)){
            return ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado");
        }
    }

}
