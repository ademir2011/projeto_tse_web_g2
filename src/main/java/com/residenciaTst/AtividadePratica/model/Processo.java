package com.residenciaTst.AtividadePratica.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
@Entity
@Table(name = "tb_processo")
public class Processo extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String numero;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="processo_id")
    private Set<ParteDoProcesso> partes;

    private String relator;

    @Column(columnDefinition = "TEXT")
    private String resumo;

    private Long ordem;

    private LocalDateTime dataCriacao;

    @ManyToMany
    @JsonIgnore
    private Set<Pauta> pauta;
}
