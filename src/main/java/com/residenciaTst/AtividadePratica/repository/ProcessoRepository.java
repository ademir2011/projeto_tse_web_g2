package com.residenciaTst.AtividadePratica.repository;

import com.residenciaTst.AtividadePratica.model.Processo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProcessoRepository extends JpaRepository <Processo, UUID> {
}
