package br.com.SecurityProfit.controllers;

import br.com.SecurityProfit.models.checklist.Pergunta;
import br.com.SecurityProfit.models.checklist.PerguntaDTO;
import br.com.SecurityProfit.models.checklist.PerguntaEditDTO;
import br.com.SecurityProfit.models.checklist.PerguntaInsertDTO;
import br.com.SecurityProfit.services.PerguntaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/checklist/pergunta")
@Api(tags = "Checklist", description = "Endpoints para operações relacionadas a checklist")
public class PerguntaController {

    @Autowired
    PerguntaService perguntaService;

    @PostMapping
    @ApiOperation(value = "Inclui uma nova pergunta")
    public ResponseEntity<Pergunta> insert(@RequestBody @Valid PerguntaInsertDTO perguntaInsertDTO) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(perguntaService.insert(perguntaInsertDTO));
    }

    @GetMapping(path = "/{checklistId}")
    public ResponseEntity<List<PerguntaDTO>> findPerguntasByChecklisId(@PathVariable Long checklistId) throws Exception {
        return ResponseEntity.ok(perguntaService.findPerguntasByChecklistId(checklistId));
    }

    @PutMapping
    @ApiOperation(value = "Edita uma Pergunta do Checklist")
    public ResponseEntity<Pergunta> edit(@RequestBody @Valid PerguntaEditDTO perguntaEditDTO) throws Exception {
        return ResponseEntity.ok(perguntaService.edit(perguntaEditDTO));
    }

    @DeleteMapping(path = "/remover/{id}")
    @ApiOperation(value = "Remove uma pergunta")
    public void remove(@PathVariable Long id) throws Exception {
        perguntaService.remove(id);
    }

    @DeleteMapping(path = "/inativar/{id}")
    public void inativar(@PathVariable Long id) throws Exception {
        perguntaService.inativar(id);
    }

}
