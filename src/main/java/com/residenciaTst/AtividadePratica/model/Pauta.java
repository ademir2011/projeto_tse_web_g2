package com.residenciaTst.AtividadePratica.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.residenciaTst.AtividadePratica.enums.MeioJulgamento;
import com.residenciaTst.AtividadePratica.enums.SistemaPauta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
// JPA
@Entity
@Table(name = "tb_pauta")
public class Pauta {
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
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tb_vinculacao", joinColumns = @JoinColumn(name = "id_pauta",
            referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_processo", referencedColumnName = "id"))
    private Set<Processo> processos;

}
