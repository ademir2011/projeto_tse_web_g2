package com.residenciaTst.AtividadePratica.model;

import com.residenciaTst.AtividadePratica.enums.ERole;
import lombok.*;

import javax.persistence.*;

@Builder
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;
}
