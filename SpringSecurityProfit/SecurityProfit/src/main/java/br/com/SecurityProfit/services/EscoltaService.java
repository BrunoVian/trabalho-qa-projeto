package br.com.SecurityProfit.services;

import br.com.SecurityProfit.mail.EmailGmailService;
import br.com.SecurityProfit.models.checklist.Checklist;
import br.com.SecurityProfit.models.checklist.Pergunta;
import br.com.SecurityProfit.models.checklist.Resposta;
import br.com.SecurityProfit.models.checklist.TipoRespostaEnum;
import br.com.SecurityProfit.models.escolta.*;
import br.com.SecurityProfit.models.pessoa.*;
import br.com.SecurityProfit.models.user.Usuario;
import br.com.SecurityProfit.models.user.UsuarioDTO;
import br.com.SecurityProfit.models.user.UsuarioRole;
import br.com.SecurityProfit.repositories.EmpresaRepository;
import br.com.SecurityProfit.repositories.EscoltaRepository;
import br.com.SecurityProfit.services.ChecklistService;
import br.com.SecurityProfit.services.PessoaService;
import br.com.SecurityProfit.services.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

@Service
public class EscoltaService {

    @Autowired
    EscoltaRepository escoltaRepository;

    @Autowired
    ChecklistService checklistService;

    @Autowired
    PessoaService pessoaService;

    @Autowired
    VeiculoService veiculoService;

    @Autowired
    PerguntaService perguntaService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    EmpresaRepository empresaRepository;


    @Autowired
    EmailGmailService emailService;


    public Escolta abrirEscolta(EscoltaAbrirDTO escoltaAbrirDTO) throws Exception {

        if (escoltaAbrirDTO.getPessoaDestinoId().equals(escoltaAbrirDTO.getPessoaOrigemId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A pessoa de Origem é a " +
                    "mesma pessoa de destino. Verifique!");
        }
        if (escoltaAbrirDTO.getAgentesIds() == null || escoltaAbrirDTO.getVeiculosIds() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados nulos. Verifique!");
        }

        Escolta escolta = new Escolta();
        escolta.setPessoaOrigem(pessoaService.findById(escoltaAbrirDTO.getPessoaOrigemId()));
        escolta.setPessoaDestino(pessoaService.findById(escoltaAbrirDTO.getPessoaDestinoId()));
        escolta.setVeiculos(veiculoService.findAllByIds(escoltaAbrirDTO.getVeiculosIds()));
        escolta.setAgentes(pessoaService.findAllByIds(escoltaAbrirDTO.getAgentesIds()));
        escolta.setChecklist(checklistService.findById(escoltaAbrirDTO.getChecklistId()));
        escolta.setDataHoraViagem(escoltaAbrirDTO.getDataHoraViagem());
        escolta.setStatusViagemEnum(StatusViagemEnum.ABERTO);
        escolta.setDataHoraAbertura(LocalDateTime.now());
        escolta.setEmpresa(empresaRepository.findAll().get(0));

        List<Pergunta> perguntas = escolta.getChecklist().getPerguntas();
        escolta.setRespostas(new ArrayList<>());

        if (perguntas.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "vazio kkkkkk");
        } else {
            for (int i = 0; i < perguntas.size(); i++) {

                Resposta resposta = new Resposta();
                resposta.setEscolta(escolta);
                resposta.setDescricao(null);
                resposta.setSimnao(null);
                resposta.setPergunta(perguntas.get(i));
                escolta.getRespostas().add(resposta);

            }
        }

        //emailService.enviarEmailHtml(escolta); Aqui é disparado um e-mail de aviso da abertura da escolta.


        return escoltaRepository.saveAndFlush(escolta);

    }

    public Escolta findById(Long id) throws Exception {
        Optional<Escolta> retorno = escoltaRepository.findById(id);

        if (retorno.isPresent())
            return retorno.get();
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Escolta com id " + id
                    + " não identificada");
    }

    public void isAdicionado(Escolta escolta, Long id, boolean isVeiculo) throws Exception {

        if (isVeiculo) {
            List<Veiculo> veiculos = escolta.getVeiculos();
            for (Veiculo veiculo : veiculos) {
                if (veiculo.getId().equals(id)) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O veículo já foi " +
                            "adicionado na escolta");
                }
            }
        }
        if (!isVeiculo) {
            List<Pessoa> agentes = escolta.getAgentes();
            for (Pessoa pessoa : agentes) {
                if (pessoa.getId().equals(id)) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O agente já foi " +
                            "adicionado na escolta");
                }
            }
        }

    }

    public Escolta adicionarVeiculo(EscoltaVeiculoDTO escoltaVeiculoDTO) throws Exception {

        Escolta escolta = findById(escoltaVeiculoDTO.getEscoltaId());
        Veiculo veiculo = veiculoService.findById(escoltaVeiculoDTO.getVeiculoId());

        isEscoltaAberta(escolta, true, false);
        isAdicionado(escolta, veiculo.getId(), true);

        escolta.getVeiculos().add(veiculo);

        return escoltaRepository.saveAndFlush(escolta);

    }


    public Escolta adicionarAgente(EscoltaAgenteDTO escoltaAgenteDTO) throws Exception {
        Escolta escolta = findById(escoltaAgenteDTO.getEscoltaId());
        Pessoa pessoa = pessoaService.findAgenteById(escoltaAgenteDTO.getPessoaAgenteId());

        isEscoltaAberta(escolta, false, false);
        isAdicionado(escolta, pessoa.getId(), false);

        escolta.getAgentes().add(pessoa);

        return escoltaRepository.saveAndFlush(escolta);
    }

    public void isVazio(Escolta escolta, boolean isVeiculo) throws Exception {

        if (isVeiculo && escolta.getVeiculos().size() == 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A escolta não pode ficar sem veículos, verifique!");
        }
        if (!isVeiculo && escolta.getAgentes().size() == 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A escolta não pode ficar sem agentes, verifique!");
        }
    }


    public String removerAgente(EscoltaAgenteDTO escoltaAgenteDTO) throws Exception {

        Escolta escolta = findById(escoltaAgenteDTO.getEscoltaId());
        Pessoa agente = pessoaService.findAgenteById(escoltaAgenteDTO.getPessoaAgenteId());

        isEscoltaAberta(escolta, false, true);
        isVazio(escolta, false);

        escolta.getAgentes().remove(agente);

        escoltaRepository.saveAndFlush(escolta);
        return "Agente removido com sucesso.";
    }

    public String removerVeiculo(EscoltaVeiculoDTO escoltaVeiculoDTO) throws Exception {

        Escolta escolta = findById(escoltaVeiculoDTO.getEscoltaId());
        Veiculo veiculo = veiculoService.findById(escoltaVeiculoDTO.getVeiculoId());

        isEscoltaAberta(escolta, true, true);
        isVazio(escolta, true);

        escolta.getAgentes().remove(veiculo);
        escoltaRepository.saveAndFlush(escolta);

        return "Veiculo removido com sucesso.";
    }

    public Escolta iniciarViagem(Long id) throws Exception {

        Escolta escolta = findById(id);


        List<HorarioEscolta> horarios = escolta.getHorarioEscoltas();

        boolean isEmViagem = false;

        for (HorarioEscolta horario : horarios) {
            if (horario.getDataHoraFim() == null) {
                isEmViagem = true;
                break;
            }
        }
        if (isEmViagem) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "A escolta id " + id + " já está em viagem");
        } else if (escolta.getStatusViagemEnum() != StatusViagemEnum.ABERTO) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "A escolta id " + id + " não está aberta");
        } else {
            HorarioEscolta horarioEscolta = new HorarioEscolta();
            horarioEscolta.setDataHoraInicio(LocalDateTime.now());
            escolta.getHorarioEscoltas().add(horarioEscolta);
            escolta.setStatusViagemEnum(StatusViagemEnum.EMVIAGEM);
            return escoltaRepository.saveAndFlush(escolta);
        }
    }


    private void isEscoltaAberta(Escolta escolta, boolean isVeiculo, boolean isRemovendo) throws Exception {
        String tipo;
        String status;
        String acao;

        if (isVeiculo)
            tipo = "veículo";
        else
            tipo = "agente";

        if (escolta.getStatusViagemEnum() == StatusViagemEnum.FECHADO)
            status = "fechada";
        else
            status = "cancelada";

        if (isRemovendo)
            acao = "remover";
        else
            acao = "incluir";

        if (escolta.getStatusViagemEnum() == StatusViagemEnum.CANCELADO ||
                escolta.getStatusViagemEnum() == StatusViagemEnum.FECHADO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível " + acao + " o " + tipo + ". A escolta está " + status + ".");

        }
    }


    public Escolta registrarParada(Long id) throws Exception {

        Escolta escolta = findById(id);
        List<HorarioEscolta> horarios = escolta.getHorarioEscoltas();

        boolean isEmViagem = false;

        for (HorarioEscolta horario : horarios) {
            if (horario.getDataHoraFim() == null) {
                isEmViagem = true;
                horario.setDataHoraFim(LocalDateTime.now());
                escolta.setStatusViagemEnum(StatusViagemEnum.ABERTO);
                break;
            }
        }

        if (isEmViagem) {
            return escoltaRepository.saveAndFlush(escolta);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A escolta id " + id + " não está em viagem");
        }
    }

    public Escolta responderPergunta(RespostaPerguntaDTO respostaPerguntaDTO) throws Exception {

        if (respostaPerguntaDTO == null ||
                (respostaPerguntaDTO.getDescricao() == null && respostaPerguntaDTO.getSimnao() == null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe ao menos uma resposta.");
        }


        Escolta escolta = findById(respostaPerguntaDTO.getEscoltaId());

        List<Resposta> respostas = escolta.getRespostas();
        boolean respostaEncontrada = false;

        for (Resposta resposta : respostas) {

            if (respostaPerguntaDTO.getRespostaId() == resposta.getId()) {

                respostaEncontrada = true;

                if (resposta.getEscolta().getId() == respostaPerguntaDTO.getEscoltaId()) {

                    if (respostaPerguntaDTO.getDescricao() != null && !respostaPerguntaDTO.getDescricao().equals("")) {

                        if (resposta.getPergunta().getTipoRespostaEnum() == TipoRespostaEnum.DESCRITIVA) {
                            resposta.setDescricao(respostaPerguntaDTO.getDescricao());
                            break;
                        } else {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O tipo da resposta informado não corresponde com o tipo de resposta da pergunta.");
                        }

                    } else if (respostaPerguntaDTO.getSimnao() != null) {
                        if (resposta.getPergunta().getTipoRespostaEnum() == TipoRespostaEnum.OBJETIVA) {
                            resposta.setSimnao(respostaPerguntaDTO.getSimnao());
                            break;
                        } else {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O tipo da resposta informado não corresponde com o tipo de resposta da pergunta.");
                        }
                    }

                }
            }
        }

        if (!respostaEncontrada) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "RespostaId não encontrado.");
        }


        return escoltaRepository.saveAndFlush(escolta);
    }


    public List<EscoltaCardDTO> findAllEscoltaCard(Long usuarioAgenteId) throws Exception {

        UsuarioDTO usuarioDTO = usuarioService.findById(usuarioAgenteId);
        List<Escolta> escoltas;

        if (usuarioDTO.getRole() == UsuarioRole.ADMIN) {

            escoltas = escoltaRepository.findAll();

        } else if (usuarioDTO.getRole() == UsuarioRole.USER) {

            escoltas = escoltaRepository.findByAgentes_Id(usuarioDTO.getId());


        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "O usuário não tem permissão para acessar as escoltas.");
        }

        List<EscoltaCardDTO> escoltaCardDTOS = new ArrayList<>();

        for (Escolta escolta : escoltas) {
            EscoltaCardDTO escoltaCardDTO = new EscoltaCardDTO();
            escoltaCardDTO.setEscoltaId(escolta.getId());
            Pessoa pessoaDestino = escolta.getPessoaDestino();

            if (!pessoaDestino.getEnderecos().isEmpty()) {
                Endereco endereco = pessoaDestino.getEnderecos().get(0);
                Cidade cidade = endereco.getCidade();
                Estado estado = cidade.getEstado();
                escoltaCardDTO.setEndereco(endereco.getRua() + ", " + endereco.getNumero() + ", " + endereco.getBairro());
                escoltaCardDTO.setCidadeEstado(cidade.getNome() + " - " + estado.getNome());
            }

            escoltaCardDTO.setNomeRazao(pessoaDestino.getNomeRazao());
            escoltaCardDTO.setCpfCnpj(pessoaDestino.getCpfCnpj());
            escoltaCardDTO.setStatusViagem(escolta.getStatusViagemEnum());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm 'hrs'");
            String dtHoraViagem = escolta.getDataHoraViagem().format(formatter);
            escoltaCardDTO.setDataHoraViagem(dtHoraViagem);
            escoltaCardDTOS.add(escoltaCardDTO);
        }

        return escoltaCardDTOS;
    }

    public Escolta fecharEscolta(Long escoltaId) throws Exception {

        Escolta escolta = findById(escoltaId);

        isEscoltaAptaFechamento(escolta);

        escolta.setStatusViagemEnum(StatusViagemEnum.FECHADO);

        return escoltaRepository.saveAndFlush(escolta);
    }

    private void isEscoltaAptaFechamento(Escolta escolta) throws Exception {

        if (escolta.getStatusViagemEnum() == StatusViagemEnum.EMVIAGEM) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A escolta está em viagem, finalize-a para fechá-la.");
        }
        if (escolta.getStatusViagemEnum() == StatusViagemEnum.CANCELADO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A escolta está cancelada, não é possível fechá-la.");
        }
        if (escolta.getStatusViagemEnum() == StatusViagemEnum.FECHADO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A escolta já está fechada.");
        }
        if (escolta.getAgentes().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A escolta não tem agentes. Verifique!");
        }
        if (escolta.getVeiculos().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A escolta não tem veículos. Verifique!");
        }

        List<Resposta> respostas = escolta.getRespostas();

        for (Resposta resposta : respostas) {

            if ((resposta.getDescricao() == null && resposta.getSimnao() == null || resposta.getDescricao().equals(""))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O checklist não foi respondido completamente. Verifique!");
            }

            if (resposta.getPergunta().getTipoRespostaEnum() == TipoRespostaEnum.DESCRITIVA) {
                if (resposta.getDescricao().equals(null) || resposta.getDescricao().equals("")) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O checklist não foi respondido completamente. Verifique!");
                }
            }
            if (resposta.getPergunta().getTipoRespostaEnum() == TipoRespostaEnum.DESCRITIVA) {

                if (resposta.getSimnao().equals(null)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O checklist não foi respondido completamente. Verifique!");
                }
            }

        }
    }

    public Escolta cancelarEscolta(Long id) throws Exception {
        Escolta escolta = findById(id);

        if (escolta.getStatusViagemEnum() == StatusViagemEnum.EMVIAGEM) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível cancelar escoltas em viagem.");
        }
        if (escolta.getStatusViagemEnum() == StatusViagemEnum.FECHADO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível cancelar escoltas em fechadas.");
        }
        if (escolta.getStatusViagemEnum() == StatusViagemEnum.CANCELADO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A escolta já está cancelada.");
        }

        escolta.setStatusViagemEnum(StatusViagemEnum.CANCELADO);

        return escoltaRepository.saveAndFlush(escolta);

    }

    public Escolta reabrirEscolta(Long id) throws Exception {

        Escolta escolta = findById(id);

        if (escolta.getStatusViagemEnum() != StatusViagemEnum.FECHADO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível reabrir a escolta.");
        }

        escolta.setStatusViagemEnum(StatusViagemEnum.ABERTO);

        return escoltaRepository.saveAndFlush(escolta);

    }


    public List<Resposta> findRespostasByEscoltaId(Long id) throws Exception {

        Escolta escolta = findById(id);

        return escolta.getRespostas();

    }
}
