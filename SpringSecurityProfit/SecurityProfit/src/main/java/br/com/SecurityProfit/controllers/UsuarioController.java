package br.com.SecurityProfit.controllers;

import br.com.SecurityProfit.models.escolta.ArquivoInsertDTO;
import br.com.SecurityProfit.models.user.*;
import br.com.SecurityProfit.services.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/usuario")
@Api(tags = "Usuários", description = "Endpoints relativos aos usuários do sistema")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    @ApiOperation(value = "Retorna uma lista de usuários")
    public ResponseEntity<List<UsuarioDTO>> findAll() {

        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping
    @ApiOperation(value = "Retorna uma lista de usuários teste")
    public ResponseEntity<List<UsuarioDTO>> findAllUers() {

        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Retorna os dados de um usuário")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @PutMapping
    @ApiOperation(value = "Altera os dados do usuário ajustesssss")
    public ResponseEntity<UsuarioDTO> edit(@Valid @RequestBody UsuarioEditDTO usuarioEditDTO) throws Exception {
        return ResponseEntity.ok(usuarioService.edit(usuarioEditDTO));
    }

    @PutMapping(path = "/alterar-senha")
    @ApiOperation(value = "Altera a Senha do Usuário")
    public ResponseEntity<UsuarioDTO> alterarSenha(@RequestBody UsuarioSenhaDTO usuarioSenhaDTO) throws Exception {
        return ResponseEntity.ok(usuarioService.alterarSenha(usuarioSenhaDTO));
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Deleta um Usuário")
    public void remove(@PathVariable Long id) throws Exception {
        usuarioService.remove(id);
    }

}
