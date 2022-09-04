package com.residenciaTst.AtividadePratica.service;

import com.residenciaTst.AtividadePratica.model.Pauta;
import com.residenciaTst.AtividadePratica.model.Processo;
import com.residenciaTst.AtividadePratica.repository.PautaRepository;
import com.residenciaTst.AtividadePratica.repository.ProcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PautaService {

    @Autowired
    PautaRepository pautaRepository;

    @Autowired
    ProcessoRepository processoRepository;

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

    // vincular um processo
    public Pauta vincularProcesso(Pauta pauta, Processo processo){
        // Colocar a ordem no processo
        processo.setOrdem(Long.valueOf(pauta.getProcessos().size() + 1));
        processoRepository.save(processo);

        pauta.getProcessos().add(processo);
        return pautaRepository.save(pauta);

    }

    public Pauta vincularListProcesso(Pauta pauta, List<Processo> processos){
        int ordem = 0;
        // Adicionar os processos a pauta
        for(int i = 0; i < processos.size(); i++){
            if(!pauta.getProcessos().contains(processos.get(i))){
                pauta.getProcessos().add(processos.get(i));
            }
            processos.get(i).setOrdem(Long.valueOf(i+1));
            processoRepository.save(processos.get(i));

        }

        return pautaRepository.save(pauta);

    }

    public Pauta desvincularProcesso(Pauta pauta, Processo processo){
        pauta.getProcessos().remove(processo);
        return pautaRepository.save(pauta);
    }
}
