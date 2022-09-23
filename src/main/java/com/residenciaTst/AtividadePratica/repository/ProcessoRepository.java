package com.residenciaTst.AtividadePratica.repository;

import com.residenciaTst.AtividadePratica.model.Processo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProcessoRepository extends JpaRepository <Processo, UUID> {
    @Query(value = "SELECT * FROM tb_processo WHERE id NOT IN (SELECT id_processo FROM tb_vinculacao)", nativeQuery = true)
    public List<Processo> findAllSemViculo();

    @Query(value = " SELECT * from Processo u where u.relator = ?1",nativeQuery = true)
    public List<Processo> findProcessoByRelatorAndNumero( String relator);
}
