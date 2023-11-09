package br.com.SecurityProfit.controllers;

import br.com.SecurityProfit.models.pessoa.Pessoa;
import br.com.SecurityProfit.models.pessoa.PessoaDTO;
import br.com.SecurityProfit.models.pessoa.PessoaEditDTO;
import br.com.SecurityProfit.models.pessoa.PessoaFuncionarioDTO;
import br.com.SecurityProfit.services.PessoaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/pessoa")
@Api(tags = "Pessoas", description = "Endpoints para operações relacionadas a pessoas")
public class PessoaController {

    @Autowired
    PessoaService pessoaService;

    @PostMapping
    @ApiOperation(value = "Insere uma Pessoa")
    public ResponseEntity<Pessoa> insert(@Valid @RequestBody PessoaDTO pessoaDTO) throws Exception{
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.insert(pessoaDTO));
    }

    @PutMapping
    @ApiOperation(value = "Edita uma pessoa")
    public ResponseEntity<Pessoa> edit(@Valid @RequestBody PessoaDTO pessoaDTO) throws Exception{
        return ResponseEntity.ok(pessoaService.edit(pessoaDTO));
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Inativa uma Pessoa")
    public void remove(@PathVariable Long id) throws Exception{
        pessoaService.remove(id);
    }

    @GetMapping
    @ApiOperation("Retorna uma lista de pessoas")
    public ResponseEntity<List<Pessoa>> findAll(){
        return ResponseEntity.ok(pessoaService.findAll());
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Busca uma pessoa pelo id")
    public ResponseEntity<PessoaDTO> findById(@PathVariable Long id) throws Exception{
        return ResponseEntity.ok(pessoaService.findPessoaDTObyId(id));
    }


}
