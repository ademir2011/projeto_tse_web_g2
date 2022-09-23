package com.residenciaTst.AtividadePratica.controller;

import com.residenciaTst.AtividadePratica.dto.MinistroDTO;
import com.residenciaTst.AtividadePratica.dto.ProcessoDto;
import com.residenciaTst.AtividadePratica.model.Ministro;
import com.residenciaTst.AtividadePratica.model.ParteDoProcesso;
import com.residenciaTst.AtividadePratica.model.Processo;
import com.residenciaTst.AtividadePratica.service.MinistroService;
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
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Tag(name = "Ministros", description = "Rotas sobre Ministros")
@RestController
@RequestMapping(path = "/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MinistroController {

    @Autowired
    MinistroService ministroService;

    @Operation(summary = "Rota para buscar todos os ministros")
    @GetMapping(path = "/ministros")
    public ResponseEntity<List<Ministro>> listarTodos(){
        return ResponseEntity.status(HttpStatus.OK).body(ministroService.listarTodos());
    }

    @Operation(summary = "Rota para salvar um ministro", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "401", description = "Não autorizado",
            content = @Content)
    @PostMapping(path = "/ministro")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Ministro> salvar(@RequestBody @Valid MinistroDTO ministroDTO){
        Ministro ministro = Ministro.builder().build();
        BeanUtils.copyProperties(ministroDTO, ministro);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ministroService.salvar(ministro));
    }

    @Operation(summary = "Rota para atualizar um ministro", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "401", description = "Não autorizado",
            content = @Content)
    @PutMapping(path = "/ministro/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Ministro> atualizar(@PathVariable UUID id,  @RequestBody Ministro ministro){
        Ministro ministroAtualizado = ministroService.atualizar(id, ministro);
        if(ministroAtualizado != null){
            return ResponseEntity.status(HttpStatus.OK).body(ministroAtualizado);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Rota para deletar um ministro", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "401", description = "Não autorizado",
            content = @Content)
    @DeleteMapping("/ministro/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deletarPeloId(@PathVariable UUID id){
        if(ministroService.deletarPeloId(id)){
            return ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado");
        }
    }

}

