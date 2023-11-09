package br.com.SecurityProfit.services;

import br.com.SecurityProfit.models.checklist.Checklist;
import br.com.SecurityProfit.models.checklist.ChecklistEditDTO;
import br.com.SecurityProfit.repositories.ChecklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ChecklistService {

    @Autowired
    ChecklistRepository checklistRepository;

    public Checklist insert(Checklist checklist) throws Exception{
        return checklistRepository.saveAndFlush(checklist);
    }

    public Checklist edit(ChecklistEditDTO checklistEditDto) throws Exception{

        Checklist checklist = findById(checklistEditDto.getChecklistId());
        checklist.setNome(checklistEditDto.getNome());
        checklist.setStatus(checklistEditDto.isStatus());
        ;
        checklistRepository.saveAndFlush(checklist);

        return checklist;

    }

    public Checklist findById(Long id) throws Exception {
        Optional<Checklist> retornoChecklist = checklistRepository.findById(id);

        if(retornoChecklist.isPresent())
            return retornoChecklist.get();
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Checklist com id "+ id + " não encontrado.");
    }


    public void delete(Long id) throws Exception {

        checklistRepository.deleteById(id);
    }

    public List<Checklist> findAll() {
        return checklistRepository.findAll();
    }


}
