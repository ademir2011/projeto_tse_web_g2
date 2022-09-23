package com.residenciaTst.AtividadePratica.repository;

import com.residenciaTst.AtividadePratica.model.ParteDoProcesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ParteDoProcessoRepository extends JpaRepository<ParteDoProcesso, UUID> {

}

