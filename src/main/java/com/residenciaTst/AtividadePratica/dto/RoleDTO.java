package com.residenciaTst.AtividadePratica.dto;

import com.residenciaTst.AtividadePratica.enums.ERole;
import lombok.*;

import javax.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    private ERole name;
}
