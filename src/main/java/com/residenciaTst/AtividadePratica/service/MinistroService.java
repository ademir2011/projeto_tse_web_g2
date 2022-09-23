package com.residenciaTst.AtividadePratica.service;

import com.residenciaTst.AtividadePratica.model.Ministro;
import com.residenciaTst.AtividadePratica.model.Processo;
import com.residenciaTst.AtividadePratica.repository.MinistroRepository;
import com.residenciaTst.AtividadePratica.repository.ProcessoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MinistroService {

    private final ProcessoRepository processRepository;

    private final MinistroRepository ministerRepository;

    public MinistroService(ProcessoRepository processRepository,
                           MinistroRepository ministerRepository
    ){

        this.processRepository = processRepository;
        this.ministerRepository = ministerRepository;
    }

    public Processo addReporterToProcess(UUID id, Ministro ministerEntity) throws Exception {

        if(ministerEntity.getName() == null || ministerEntity.getName().isEmpty()) {
            throw new RuntimeException("Nome do ministro nulo ou vazio!");
        }

        if(id != null && !processRepository.findById(id).isPresent()){
            throw new RuntimeException("Não existe processo salvo com este ID!");
        }

        Processo processEntity = processRepository.findById(id).get();

//        processEntity.setReporter(ministerRepository.save(ministerEntity));

        return processRepository.save(processEntity);

    }

}
