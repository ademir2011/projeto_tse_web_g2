package com.residenciaTst.AtividadePratica.service;

import com.residenciaTst.AtividadePratica.model.Processo;
import com.residenciaTst.AtividadePratica.repository.ProcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ProcessoService {

    @Autowired
    ProcessoRepository processoRepository;

    public Processo salvar(Processo processo){
        processo.setDataCriacao(LocalDateTime.now());
        return processoRepository.save(processo);
    }

    public List<Processo> listarTodos(){
        return processoRepository.findAll();
    }

    public Processo listarPeloId(UUID id){
        if(processoRepository.findById(id).isPresent()){
            return processoRepository.findById(id).get();
        }else{
            return null;
        }
    }

    public Processo atualizar(UUID id, Processo processo){
        if(processoRepository.findById(id).isPresent()){
            return processoRepository.save(processo);
        }else{
            return null;
        }
    }

    public boolean deletarPeloId(UUID id){
        if(processoRepository.findById(id).isPresent()){
            processoRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }


}
