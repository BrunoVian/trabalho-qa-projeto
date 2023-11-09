package br.com.SecurityProfit.services;
import br.com.SecurityProfit.models.checklist.*;
import br.com.SecurityProfit.repositories.PerguntaRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PerguntaService {

    @Autowired
    PerguntaRespository perguntaRepository;

    @Autowired
    ChecklistService checklistService;

    public Pergunta insert(PerguntaInsertDTO perguntaInsertDTO) throws Exception{

        Pergunta pergunta = new Pergunta();
        Checklist checklist = checklistService.findById(perguntaInsertDTO.getChecklistId());

        pergunta.setDescricao(perguntaInsertDTO.getDescricao());
        pergunta.setTipoRespostaEnum(perguntaInsertDTO.getTipoRespostaEnum());
        pergunta.setStatus(true);
        pergunta.setChecklist(checklist);

        return perguntaRepository.saveAndFlush(pergunta);
    }


    public Pergunta edit(PerguntaEditDTO perguntaEditDTO) throws Exception {

        Pergunta pergunta = findById(perguntaEditDTO.getId());

        pergunta.setDescricao(perguntaEditDTO.getDescricao());

        return perguntaRepository.saveAndFlush(pergunta);

    }

    public Pergunta findById(Long id) throws Exception {
        Optional<Pergunta> retornoPergunta = perguntaRepository.findById(id);

        if(retornoPergunta.isPresent())
            return retornoPergunta.get();
        else
            throw new Exception("Pergunta com id "+ id + " não encontrada.");
    }

    public void remove(Long id) throws Exception{

        perguntaRepository.delete(findById(id));

    }

    public void inativar(Long id) throws Exception{
        Pergunta pergunta = findById(id);

        pergunta.setStatus(false);

        perguntaRepository.saveAndFlush(pergunta);

    }

    public List<PerguntaDTO> findPerguntasByChecklistId(Long checklistId) throws Exception {

            Checklist checklist = checklistService.findById(checklistId);

            List<Pergunta> perguntas = checklist.getPerguntas();

            List<PerguntaDTO> perguntaDTOs = new ArrayList<>();

            for (Pergunta pergunta : perguntas) {
                PerguntaDTO perguntaDTO = new PerguntaDTO(
                        pergunta.getId(),
                        pergunta.getDescricao(),
                        pergunta.getTipoRespostaEnum(),
                        pergunta.isStatus()
                );
                perguntaDTOs.add(perguntaDTO);
            }

            return perguntaDTOs;

    }

    public void delete(Long id) throws Exception {
        perguntaRepository.delete(findById(id));
    }
}
