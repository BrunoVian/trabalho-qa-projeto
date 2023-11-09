package br.com.SecurityProfit.controllers;


import br.com.SecurityProfit.mail.EmailGmailDTO;
import br.com.SecurityProfit.mail.EmailGmailService;
import br.com.SecurityProfit.models.escolta.*;
import br.com.SecurityProfit.models.pessoa.PessoaEscoltaDTO;
import br.com.SecurityProfit.services.EscoltaService;
import br.com.SecurityProfit.services.PessoaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/escolta")
@Api(tags = "Escolta", description ="Endpoints relativos às escoltas")
public class EscoltaController {


    @Autowired
    EscoltaService escoltaService;

    @Autowired
    PessoaService pessoaService;

    @PostMapping(path = "/abrir-escolta")
    @ApiOperation(value = "Endpoint para Abrir uma Nova Escolta")
    public ResponseEntity<Escolta> abrirEscolta(@Valid @RequestBody EscoltaAbrirDTO escoltaAbrirDTO) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(escoltaService.abrirEscolta(escoltaAbrirDTO));
    }

    @PostMapping(path = "/adicionar-veiculo")
    @ApiOperation(value = "Endpoint para Adicionar um Veículo à Escolta")
    public ResponseEntity<Escolta> adicionarVeiculo(@Valid @RequestBody EscoltaVeiculoDTO escoltaVeiculoDTO) throws Exception {
        return ResponseEntity.ok(escoltaService.adicionarVeiculo(escoltaVeiculoDTO));
    }

    @PostMapping(path = "/adicionar-agente")
    @ApiOperation(value = "Endpoint para Adicionar um Agente à Escolta")
    public ResponseEntity<Escolta> adicionarAgente(@Valid @RequestBody EscoltaAgenteDTO escoltaAgenteDTO) throws Exception {
        return ResponseEntity.ok(escoltaService.adicionarAgente(escoltaAgenteDTO));
    }

    @GetMapping(path = "/funcionario")
    @ApiOperation(value = "Lista pessoas do tipo funcionario para o utilização nas escoltas")
    public ResponseEntity<List<PessoaEscoltaDTO>> findAllPessoaFuncionario() {
        return ResponseEntity.ok(pessoaService.findAllPessoaEscoltaFuncionario());
    }

    @GetMapping(path = "/cliente")
    @ApiOperation(value = "Lista pessoas do tipo cliente para o utilização nas escoltas")
    public ResponseEntity<List<PessoaEscoltaDTO>> findAllPessoaCliente(@RequestParam(value = "parametro", required = false) String parametro) {
        return ResponseEntity.ok(pessoaService.findAllPessoaEscoltaCliente(parametro));
    }

    @DeleteMapping(path = "/remover-agente")
    @ApiOperation(value = "Remove o Agente da Escolta")
    public ResponseEntity<?> removerAgente(@RequestBody EscoltaAgenteDTO escoltaAgenteDTO) throws Exception {
        return ResponseEntity.ok(escoltaService.removerAgente(escoltaAgenteDTO));
    }

    @DeleteMapping(path = "/remover-veiculo")
    @ApiOperation(value = "Remove o Veiculo da Escolta")
    public ResponseEntity<?> removerVeiculo(@RequestBody EscoltaVeiculoDTO escoltaVeiculoDTO) throws Exception {
        return ResponseEntity.ok(escoltaService.removerVeiculo(escoltaVeiculoDTO));
    }

    @PostMapping(path = "/{id}/iniciar-viagem")
    @ApiOperation(value = "Inicia a Viagem da Escolta")
    public ResponseEntity<?> iniciarViagem(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(escoltaService.iniciarViagem(id));
    }

    @PutMapping(path = "/{id}/registrar-parada")
    @ApiOperation(value = "Registra a Parada da Escolta")
    public ResponseEntity<?> registrarParada(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(escoltaService.registrarParada(id));
    }

    @PutMapping(path = "/responder-pergunta")
    @ApiOperation(value = "Responde a pergunta do checklist da escolta")
    public ResponseEntity<?> responderPergunta(@RequestBody RespostaPerguntaDTO respostaPerguntaDTO) throws Exception {
        return ResponseEntity.ok(escoltaService.responderPergunta(respostaPerguntaDTO));
    }

    @PutMapping(path = "/{id}/fechar-escolta")
    @ApiOperation(value = "Fecha a escolta")
    public ResponseEntity<?> fecharEscolta(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(escoltaService.fecharEscolta(id));
    }

    @GetMapping(path = "/{usuarioAgenteId}/escoltas-cards")
    @ApiOperation(value = "Retorna uma lista de escoltas para exibir os cards")
    public ResponseEntity<?> findAllEscoltasByUsuarioId(@PathVariable Long usuarioAgenteId) throws Exception{
        return ResponseEntity.ok(escoltaService.findAllEscoltaCard(usuarioAgenteId));
    }

    @GetMapping(path = "/escoltas")
    @ApiOperation(value = "Retorna uma lista de escoltas testes")
    public ResponseEntity<?> findAllEscoltas() throws Exception{
        return ResponseEntity.ok(escoltaService.findAllEscoltaCard(1L));
    }

    @PutMapping(path = "/{id}/cancelar-escolta")
    @ApiOperation(value = "Cancela a escolta")
    public ResponseEntity<?> cancelarEscolta(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(escoltaService.cancelarEscolta(id));
    }

    @PutMapping(path = "/{id}/reabrir-escolta")
    @ApiOperation(value = "Reabre a escolta")
    public ResponseEntity<?> reabrirEscolta(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(escoltaService.reabrirEscolta(id));
    }

    @GetMapping("/{id}/checklist")
    @ApiOperation(value = "Retorna os dados de um checklist vinculado à uma Escolta")
    public ResponseEntity<?> findById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(escoltaService.findRespostasByEscoltaId(id));
    }
}
