package com.residenciaTst.AtividadePratica.service;

import com.residenciaTst.AtividadePratica.model.Pauta;
import com.residenciaTst.AtividadePratica.model.Processo;
import com.residenciaTst.AtividadePratica.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PautaService {

    @Autowired
    PautaRepository pautaRepository;

    public Pauta salvar(Pauta pauta){
        pauta.setDataCriacao(LocalDateTime.now());
        return pautaRepository.save(pauta);
    }

    public List<Pauta> listarTodos(){
        return pautaRepository.findAll();
    }

    public Pauta listarPeloId(UUID id){
        if(pautaRepository.findById(id).isPresent()){
            return pautaRepository.findById(id).get();
        }else{
            return null;
        }
    }

    public Pauta atualizar(UUID id, Pauta pauta){
        if(pautaRepository.findById(id).isPresent()){
            return pautaRepository.save(pauta);
        }else{
            return null;
        }
    }

    public boolean deletarPeloId(UUID id){
        if(pautaRepository.findById(id).isPresent()){
            pautaRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    public Pauta vincularProcesso(Pauta pauta, Processo processo){
        pauta.getProcessos().add(processo);
        return pautaRepository.save(pauta);

    }

    public Pauta desvincularProcesso(Pauta pauta, Processo processo){
        pauta.getProcessos().remove(processo);
        return pautaRepository.save(pauta);
    }
}
