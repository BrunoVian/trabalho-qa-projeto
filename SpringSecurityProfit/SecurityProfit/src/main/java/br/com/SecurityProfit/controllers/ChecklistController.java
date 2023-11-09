package br.com.SecurityProfit.controllers;

import br.com.SecurityProfit.models.checklist.Checklist;
import br.com.SecurityProfit.models.checklist.ChecklistEditDTO;
import br.com.SecurityProfit.models.checklist.Pergunta;
import br.com.SecurityProfit.models.checklist.PerguntaInsertDTO;
import br.com.SecurityProfit.services.ChecklistService;
import br.com.SecurityProfit.services.PerguntaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/checklist")
@Api(tags = "Checklist", description = "Endpoints para operações relacionadas a checklist")
public class ChecklistController {

    @Autowired
    ChecklistService checklistService;

    @Autowired
    PerguntaService perguntaService;

    @PostMapping
    @ApiOperation(value = "Insere o Checklist")
    public ResponseEntity<Checklist> insert(@Valid @RequestBody Checklist checklist) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(checklistService.insert(checklist));
    }

    @PostMapping(path = "/nova-pergunta")
    @ApiOperation(value = "Insere uma nova pergunta ao Checklist")
    public ResponseEntity<Pergunta> insertPergunta(@Valid @RequestBody PerguntaInsertDTO perguntaInsertDTO) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body((perguntaService.insert(perguntaInsertDTO)));
    }

    @DeleteMapping(path = "{id}/excluir-pergunta")
    @ApiOperation(value = "Exclui uma pergunta do Checklist")
    public ResponseEntity<?> deletePergunta(@PathVariable Long id) throws Exception {
        perguntaService.delete(id);
        return ResponseEntity.ok(null);
    }


    @PutMapping
    @ApiOperation(value = "Edita o nome e status do Cheklist")
    public ResponseEntity<Checklist> edit(@Valid @RequestBody ChecklistEditDTO checklistEditDto) throws Exception {
        return ResponseEntity.ok(checklistService.edit(checklistEditDto));
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Deleta um checklist")
    public void delete(@Valid @PathVariable Long id) throws Exception {
        checklistService.delete(id);
    }

    @GetMapping
    @ApiOperation(value = "Retorna os dados de todos os Checklists Cadastrados")
    public ResponseEntity<List<Checklist>> findAll(){
        return ResponseEntity.ok(checklistService.findAll());
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Retorna os dados de um checklist pelo Id")
    public ResponseEntity<Checklist> findById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(checklistService.findById(id));
    }


}
