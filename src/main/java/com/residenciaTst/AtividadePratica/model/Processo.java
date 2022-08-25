package com.residenciaTst.AtividadePratica.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
// JPA
@Entity
@Table(name = "tb_processo")
public class Processo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String numero;
    @Column(columnDefinition = "TEXT")
    private String partes;
    private String relator;
    @Column(columnDefinition = "TEXT")
    private String resumo;
    private LocalDateTime dataCriacao;
    @ManyToOne
    @JoinColumn(name = "pauta_id")
    private Pauta pauta;

}
