package com.residenciaTst.AtividadePratica.controller;

import com.residenciaTst.AtividadePratica.dto.MinistroDTO;
import com.residenciaTst.AtividadePratica.dto.RoleDTO;
import com.residenciaTst.AtividadePratica.model.Ministro;
import com.residenciaTst.AtividadePratica.model.Role;
import com.residenciaTst.AtividadePratica.service.MinistroService;
import com.residenciaTst.AtividadePratica.service.RoleService;
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

@Tag(name = "Roles", description = "Rotas sobre Roles")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RoleController {

    @Autowired
    RoleService roleService;

    @Operation(summary = "Rota para buscar todas as roles")
    @GetMapping(path = "/roles")
    public ResponseEntity<List<Role>> listarTodos(){
        return ResponseEntity.status(HttpStatus.OK).body(roleService.listarTodos());
    }

    @Operation(summary = "Rota para salvar uma Role")
    @PostMapping(path = "/role")
    public ResponseEntity<Role> salvar(@RequestBody @Valid RoleDTO roleDTO){
        Role role = Role.builder().build();
        BeanUtils.copyProperties(roleDTO, role);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roleService.salvar(role));
    }

    @Operation(summary = "Rota para atualizar uma role")
    @PutMapping(path = "/role/{id}")
    public ResponseEntity<Role> atualizar(@PathVariable Long id, @RequestBody Role role){
        Role roleAtualizada = roleService.atualizar(id, role);
        if(roleAtualizada != null){
            return ResponseEntity.status(HttpStatus.OK).body(roleAtualizada);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Rota para deletar uma role")
    @DeleteMapping("/role/{id}")
    public ResponseEntity<?> deletarPeloId(@PathVariable Long id){
        if(roleService.deletarPeloId(id)){
            return ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado");
        }
    }
}
