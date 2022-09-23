package com.residenciaTst.AtividadePratica.controller;

import com.residenciaTst.AtividadePratica.dto.*;
import com.residenciaTst.AtividadePratica.enums.ERole;
import com.residenciaTst.AtividadePratica.jwt.JwtUtils;
import com.residenciaTst.AtividadePratica.model.Ministro;
import com.residenciaTst.AtividadePratica.model.Role;
import com.residenciaTst.AtividadePratica.model.User;
import com.residenciaTst.AtividadePratica.repository.RoleRepository;
import com.residenciaTst.AtividadePratica.repository.UserRepository;
import com.residenciaTst.AtividadePratica.service.UserDetailsImpl;
import com.residenciaTst.AtividadePratica.service.UserDetailsServiceImpl;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

//@CrossOrigin(originPatterns = "${spring.application.originPatterns}", allowCredentials = "true", maxAge = 3600)
@Tag(name = "Usuários", description = "Rotas sobre Usuários")
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Operation(summary = "Rota para buscar todos os usuários")
    @GetMapping(path = "/user")
    public ResponseEntity<List<UserDTO>> listarTodos(){
        List<User> userList = userDetailsService.listarTodos();
        List<UserDTO> userDtoList = new ArrayList<UserDTO>();
        for (User user : userList) {
            UserDTO userDTO = UserDTO.builder()
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .roles(user.getRoles())
                    .build();
//            BeanUtils.copyProperties(UserDTO.builder().build(), user);
            userDtoList.add(userDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(userDtoList);
    }


    @Operation(summary = "Rota para deletar um usuário", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "401", description = "Não autorizado",
            content = @Content)
    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletarPeloId(@PathVariable Long id){
        if(userDetailsService.deletarPeloId(id)){
            return ResponseEntity.status(HttpStatus.OK).body("Deletado com sucesso");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado");
        }
    }

    @Operation(summary = "Rota para atualizar um usuário", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "401", description = "Não autorizado",
            content = @Content)
    @PutMapping(path = "/user/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<UserDTO> atualizar(@PathVariable Long id,  @RequestBody User user){
        user.setPassword(encoder.encode(user.getPassword()));
        User userAtualizado = userDetailsService.atualizar(id, user);
        UserDTO userDTO = UserDTO.builder()
                .username(userAtualizado.getUsername())
                .email(userAtualizado.getEmail())
                .roles(userAtualizado.getRoles())
                .build();
        if(userDetailsService != null){
            return ResponseEntity.status(HttpStatus.OK).body(userDTO);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Rota para realizar autenticação de um usuário")
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @Operation(summary = "Rota para realizar o cadastro de um usuário", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/signup")
    // Por enquanto que não tem a parte do cadastro de usuário no frontend
    // comentar esta linha posteriormente
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(String.valueOf(ERole.ROLE_USER))
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(String.valueOf(ERole.ROLE_ADMIN))
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(String.valueOf(ERole.ROLE_MODERATOR))
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(String.valueOf(ERole.ROLE_USER))
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


}
