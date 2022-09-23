package com.residenciaTst.AtividadePratica.service;

import com.residenciaTst.AtividadePratica.model.Ministro;
import com.residenciaTst.AtividadePratica.model.Processo;
import com.residenciaTst.AtividadePratica.repository.MinistroRepository;
import com.residenciaTst.AtividadePratica.repository.ProcessoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MinistroService {

    private final MinistroRepository ministerRepository;

    public MinistroService(
                           MinistroRepository ministerRepository
    ){

        this.ministerRepository = ministerRepository;
    }

//    public Processo addReporterToProcess(UUID id, Ministro ministerEntity) throws Exception {
//
//        if(ministerEntity.getName() == null || ministerEntity.getName().isEmpty()) {
//            throw new RuntimeException("Nome do ministro nulo ou vazio!");
//        }
//
//        if(id != null && !processRepository.findById(id).isPresent()){
//            throw new RuntimeException("Não existe processo salvo com este ID!");
//        }
//
//        Processo processEntity = processRepository.findById(id).get();
//
////        processEntity.setReporter(ministerRepository.save(ministerEntity));
//
//        return processRepository.save(processEntity);
//
//    }

    public List<Ministro> listarTodos() {
        return ministerRepository.findAll();
    }

    public Ministro salvar(Ministro ministro) {
        return ministerRepository.save(ministro);
    }

    public Ministro atualizar(UUID id, Ministro ministro) {
        ministro.setId(id);
        if(ministerRepository.findById(id).isPresent()){
            return ministerRepository.save(ministro);
        }else{
            return null;
        }
    }

    public boolean deletarPeloId(UUID id) {

        if(ministerRepository.findById(id).isPresent()){
            ministerRepository.deleteById(id);
            return true;
        }else{
            return false;
        }

    }
}
