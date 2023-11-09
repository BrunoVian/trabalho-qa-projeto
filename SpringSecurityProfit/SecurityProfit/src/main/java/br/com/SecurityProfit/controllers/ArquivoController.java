package br.com.SecurityProfit.controllers;


import br.com.SecurityProfit.models.escolta.ArquivoEditDTO;
import br.com.SecurityProfit.models.escolta.ArquivoInsertDTO;
import br.com.SecurityProfit.services.ArquivoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/escolta/arquivo")
@Api(tags = "Escolta arquivos", description ="Endpoints relativos aos arquivos da escolta")
public class ArquivoController {

    @Autowired
    ArquivoService arquivoService;

    @ApiOperation(value = "Upload de Arquivo")
    @PostMapping(consumes = {"multipart/form-data"}, path = "/upload")
    public ResponseEntity<?> upload(@RequestPart(value = "file", required = true) MultipartFile file,
                                          @RequestParam(value = "descricao") String descricao,
                                          @RequestParam(value = "escoltaId") Long escoltaId) throws Exception {
        ArquivoInsertDTO arquivoInsertDTO = new ArquivoInsertDTO(file.getBytes(), descricao, escoltaId);
        return ResponseEntity.status(HttpStatus.CREATED).body(arquivoService.upload(arquivoInsertDTO));
    }

    @GetMapping(path = "/{escoltaId}")
    @ApiOperation(value = "Busca os Anexos por Escolta")
    public ResponseEntity<?> findArquivoByEscolta(@PathVariable Long escoltaId) throws Exception {
        return ResponseEntity.ok(arquivoService.findArquivoByEscolta(escoltaId));
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Deleta o anexo da escolta")
    public void delete(@PathVariable Long id) throws Exception {
        arquivoService.delete(id);
    }

    @PutMapping(path = "/alterar-descricao")
    @ApiOperation(value = "Ajusta a descrição do anexo da escolta")
    public ResponseEntity<?> updateDescricao(@Valid @RequestBody ArquivoEditDTO arquivoEditDTO) throws Exception {
        return ResponseEntity.ok(arquivoService.updateDescricao(arquivoEditDTO));
    }
}
