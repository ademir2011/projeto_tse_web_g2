package com.residenciaTst.AtividadePratica.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.residenciaTst.AtividadePratica.model.ParteDoProcesso;
import com.residenciaTst.AtividadePratica.model.Pauta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessoDto {

    @NotBlank
    private String numero;

    private Set<ParteDoProcessoDTO> parteDoProcessoDTO;

    @NotBlank
    private String relator;

    @NotBlank
    private String resumo;

}
