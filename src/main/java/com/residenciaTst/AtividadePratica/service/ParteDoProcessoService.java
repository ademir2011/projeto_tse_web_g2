package com.residenciaTst.AtividadePratica.service;

import com.residenciaTst.AtividadePratica.model.ParteDoProcesso;
import com.residenciaTst.AtividadePratica.repository.ParteDoProcessoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ParteDoProcessoService {

//    private final ProcessRepository processRepository;
//
//    private final MinisterRepository ministerRepository;
//
//    private final ProcessHistoryRepository processHistoryRepository;
    private final ParteDoProcessoRepository parteDoProcessoRepository;

    public ParteDoProcessoService(ParteDoProcessoRepository parteDoProcessoRepository){
        this.parteDoProcessoRepository = parteDoProcessoRepository;
    }

    public ParteDoProcesso salvar(ParteDoProcesso parteDoProcesso){
        return parteDoProcessoRepository.save(parteDoProcesso);
    }

/*

    public ProcessEntity addPartToProcess(UUID id, ProcessPartEntity processPartEntity) throws Exception {

        if(processPartEntity.getLawyer() == null || processPartEntity.getLawyer().isEmpty()) {
            throw new RuntimeException("Nome do advogado nulo ou vazio!");
        }

        if(processPartEntity.getAggravating() == null || processPartEntity.getAggravating().isEmpty()) {
            throw new RuntimeException("Nome do agravante nulo ou vazio!");
        }

        if(id != null && !processRepository.findById(id).isPresent()){
            throw new RuntimeException("Não existe processo salvo com este ID!");
        }

        var processEntity = processRepository.findById(id).get();

        processEntity.getProcessParts().add( processPartRepository.save(processPartEntity) );

        return processRepository.save(processEntity);

    }
*/

}
