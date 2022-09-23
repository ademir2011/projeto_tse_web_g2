package com.residenciaTst.AtividadePratica.repository;

import com.residenciaTst.AtividadePratica.model.Pauta;
import com.residenciaTst.AtividadePratica.model.Processo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PautaRepository extends JpaRepository <Pauta, UUID> {
    public List<Pauta> findByOrgaoJudicante(String orgaoJudicante);
}
