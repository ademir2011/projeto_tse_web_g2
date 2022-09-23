package com.residenciaTst.AtividadePratica.dto;


import com.residenciaTst.AtividadePratica.model.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {


    @NonNull
    private String username;

    @NonNull
    private String email;

    @Builder.Default
    private Set<Role> roles = new HashSet<>();
}
