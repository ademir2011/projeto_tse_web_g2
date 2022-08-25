package com.residenciaTst.AtividadePratica.model;

import com.residenciaTst.AtividadePratica.enums.MeioJulgamento;
import com.residenciaTst.AtividadePratica.enums.SistemaPauta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    @OneToMany(mappedBy = "pauta")
    private Set<Processo> processos;

}
