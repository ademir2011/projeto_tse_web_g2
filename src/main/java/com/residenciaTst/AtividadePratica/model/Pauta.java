package com.residenciaTst.AtividadePratica.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.residenciaTst.AtividadePratica.enums.MeioJulgamento;
import com.residenciaTst.AtividadePratica.enums.SistemaPauta;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@Entity
@Table(name = "tb_pauta")
public class Pauta extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String orgaoJudicante;

    private SistemaPauta sistemaPauta;

    private MeioJulgamento meioJulgamento;

    private LocalDateTime dataSessao;

    private LocalDateTime dataDivulgacao;

    private LocalDateTime dataPublicacao;

    private LocalDateTime dataCriacao;

    @ManyToMany
    @JoinTable(
            name = "tb_relatorio",
            joinColumns = @JoinColumn(name = "pauta_id"),
            inverseJoinColumns = @JoinColumn(name = "processo_id"))
    private Set<Processo> processos;

}
