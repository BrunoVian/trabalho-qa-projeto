package br.com.SecurityProfit.controllers;

import br.com.SecurityProfit.models.escolta.Veiculo;
import br.com.SecurityProfit.services.VeiculoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/veiculo")
@Api(tags = "Veículos", description = "Endpoints para operações relacionadas a veículos")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @PostMapping
    @ApiOperation(value = "Insere o Veículo")
    public ResponseEntity<Veiculo> insert(@Valid @RequestBody Veiculo veiculo) throws Exception{
        return ResponseEntity.status(HttpStatus.CREATED).body(veiculoService.insert(veiculo));
    }

    @PutMapping
    @ApiOperation(value = "Edita os dados do veículo")
    public ResponseEntity<Veiculo> edit(@Valid @RequestBody Veiculo veiculo) throws Exception{
        return ResponseEntity.ok(veiculoService.edit(veiculo));
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Remove o Veículo")
    public ResponseEntity<?> delete(@PathVariable Long id) throws Exception{
        veiculoService.remove(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Retorna um veículo pelo id")
    public ResponseEntity<Veiculo> findById(@PathVariable Long id) throws Exception {
        Veiculo veiculo = veiculoService.findById(id);
        return ResponseEntity.ok(veiculo);
    }

    @GetMapping
    @ApiOperation(value = "Retorna uma lista de veículos")
    public ResponseEntity<List<Veiculo>> findAll(){
        return ResponseEntity.ok(veiculoService.findAll());
    }

    @GetMapping("/filter")
    @ApiOperation(value = "Retorna uma lista de veículos através de um termo de busca, filtra pelo modelo e placa")
    public ResponseEntity<List<Veiculo>> findByFilters(@RequestParam("termobusca") String termoBusca){
        return ResponseEntity.ok(veiculoService.findByFilters(termoBusca));
    }
}
