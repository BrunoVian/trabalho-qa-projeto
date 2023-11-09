package br.com.SecurityProfit.controllers;

import br.com.SecurityProfit.models.escolta.Empresa;
import br.com.SecurityProfit.services.EmpresaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.jfr.Enabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/empresa")
@Api(tags = "Empresa", description = "Endpoints para operações relacionadas a empresa")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @PutMapping
    @ApiOperation(value = "Edita a Empresa")
    public ResponseEntity<Empresa> edit(@Valid @RequestBody Empresa empresa) throws Exception {
        return ResponseEntity.ok(empresaService.edit(empresa));
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Retorna os dados da empresa")
    public ResponseEntity<Empresa> findById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(empresaService.findById(id));
    }
}
