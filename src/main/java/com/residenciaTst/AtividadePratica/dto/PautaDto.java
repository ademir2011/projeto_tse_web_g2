package com.residenciaTst.AtividadePratica.dto;

import com.residenciaTst.AtividadePratica.enums.MeioJulgamento;
import com.residenciaTst.AtividadePratica.enums.SistemaPauta;
import com.residenciaTst.AtividadePratica.model.Processo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PautaDto {

    @NotBlank
    private String orgaoJudicante;
    @NotNull
    private SistemaPauta sistemaPauta;
    @NotNull
    private MeioJulgamento meioJulgamento;
    @NotNull
    private LocalDateTime dataSessao;
    @NotNull
    private LocalDateTime dataDivulgacao;
    @NotNull
    private LocalDateTime dataPublicacao;

}
