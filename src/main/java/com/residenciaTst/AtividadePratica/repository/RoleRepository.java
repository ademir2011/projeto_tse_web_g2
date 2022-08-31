package com.residenciaTst.AtividadePratica.repository;

import com.residenciaTst.AtividadePratica.enums.ERole;
import com.residenciaTst.AtividadePratica.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
