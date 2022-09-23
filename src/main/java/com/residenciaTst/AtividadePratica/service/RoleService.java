package com.residenciaTst.AtividadePratica.service;

import com.residenciaTst.AtividadePratica.model.Processo;
import com.residenciaTst.AtividadePratica.model.Role;
import com.residenciaTst.AtividadePratica.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Role salvar(Role role){
        return roleRepository.save(role);
    }

    public List<Role> listarTodos(){
        return roleRepository.findAll();
    }

    public Role listarPeloId(Long id){
        if(roleRepository.findById(id).isPresent()){
            return roleRepository.findById(id).get();
        }else{
            return null;
        }
    }

    public Role atualizar(Long id, Role role){
        if(roleRepository.findById(id).isPresent()){
            return roleRepository.save(role);
        }else{
            return null;
        }
    }

    public boolean deletarPeloId(Long id){
        if(roleRepository.findById(id).isPresent()){
            roleRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

}
