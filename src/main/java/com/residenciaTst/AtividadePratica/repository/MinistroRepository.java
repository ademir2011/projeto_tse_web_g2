package com.residenciaTst.AtividadePratica.repository;

import com.residenciaTst.AtividadePratica.model.Ministro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface MinistroRepository extends JpaRepository<Ministro, UUID> {

}