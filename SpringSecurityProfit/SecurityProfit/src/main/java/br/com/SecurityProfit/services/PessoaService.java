package br.com.SecurityProfit.services;

import br.com.SecurityProfit.models.pessoa.*;
import br.com.SecurityProfit.models.pessoa.PessoaFuncionarioDTO;
import br.com.SecurityProfit.repositories.CidadeRepository;
import br.com.SecurityProfit.repositories.EnderecoRepository;
import br.com.SecurityProfit.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    CidadeService cidadeService;

    public Pessoa insert(PessoaDTO pessoaDTO) throws Exception {

        Pessoa pessoa = new Pessoa();
        pessoa.setFisicaJurica(pessoaDTO.getFisicaJuridica());
        pessoa.setNomeRazao(pessoaDTO.getNomeRazao());
        pessoa.setApelidoFantasia(pessoaDTO.getApelidoFantasia());
        pessoa.setCpfCnpj(pessoaDTO.getCpfCnpj());
        pessoa.setEmail(pessoaDTO.getEmail());
        pessoa.setTelefone(pessoaDTO.getTelefone());
        pessoa.setCelular(pessoaDTO.getCelular());
        pessoa.setTipo(pessoaDTO.getTipo());
        pessoa.setStatus(true);
        pessoa.setObservacao(pessoaDTO.getObservacao());

        Endereco endereco = new Endereco();
        endereco.setCep(pessoaDTO.getEndereco().getCep());
        endereco.setRua(pessoaDTO.getEndereco().getRua());
        endereco.setBairro(pessoaDTO.getEndereco().getBairro());
        endereco.setComplemento(pessoaDTO.getEndereco().getComplemento());
        Cidade cidade = cidadeService.findByCodIbge(pessoaDTO.getEndereco().getCidadeIBGE());
        endereco.setStatus(true);
        endereco.setCidade(cidade);

        List<Endereco> enderecos = new ArrayList<>();
        enderecos.add(endereco);
        pessoa.setEnderecos(enderecos);

        return pessoaRepository.saveAndFlush(pessoa);
    }

    public Pessoa edit(PessoaDTO pessoaDTO) throws Exception {

        Pessoa pessoa = findById(pessoaDTO.getId());

        pessoa.setId(pessoaDTO.getId());
        pessoa.setFisicaJurica(pessoaDTO.getFisicaJuridica());
        pessoa.setNomeRazao(pessoaDTO.getNomeRazao());
        pessoa.setApelidoFantasia(pessoaDTO.getApelidoFantasia());
        pessoa.setCpfCnpj(pessoaDTO.getCpfCnpj());
        pessoa.setEmail(pessoaDTO.getEmail());
        pessoa.setTelefone(pessoaDTO.getTelefone());
        pessoa.setCelular(pessoaDTO.getCelular());
        pessoa.setTipo(pessoaDTO.getTipo());
        pessoa.setStatus(true);
        pessoa.setObservacao(pessoaDTO.getObservacao());

        Endereco endereco = pessoa.getEnderecos().get(0);
        endereco.setCep(pessoaDTO.getEndereco().getCep());
        endereco.setRua(pessoaDTO.getEndereco().getRua());
        endereco.setBairro(pessoaDTO.getEndereco().getBairro());
        endereco.setComplemento(pessoaDTO.getEndereco().getComplemento());
        Cidade cidade = cidadeService.findByCodIbge(pessoaDTO.getEndereco().getCidadeIBGE());
        endereco.setStatus(pessoaDTO.getStatus());
        endereco.setCidade(cidade);


        return pessoaRepository.saveAndFlush(pessoa);
    }

    public void remove(Long id) throws Exception{
        Pessoa pessoa = findById(id);
        pessoaRepository.delete(pessoa);
    }

    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }

    public Pessoa findById(Long id) throws Exception{
        Optional<Pessoa> retorno = pessoaRepository.findById(id);

        if(retorno.isPresent())
            return retorno.get();
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa com id " + id + " não identificada");
    }

    public PessoaDTO findPessoaDTObyId(Long id) throws Exception{

        Pessoa pessoa = findById(id);

        PessoaDTO pessoaDTO = new PessoaDTO();

        pessoaDTO.setId(pessoa.getId());
        pessoaDTO.setEmail(pessoa.getEmail());
        pessoaDTO.setTipo(pessoa.getTipo());
        pessoaDTO.setStatus(pessoa.getStatus());
        pessoaDTO.setCelular(pessoa.getCelular());
        pessoaDTO.setObservacao(pessoa.getObservacao());
        pessoaDTO.setApelidoFantasia(pessoa.getApelidoFantasia());
        pessoaDTO.setCpfCnpj(pessoa.getCpfCnpj());
        pessoaDTO.setFisicaJuridica(pessoa.getFisicaJurica());
        pessoaDTO.setTelefone(pessoa.getTelefone());
        pessoaDTO.setNomeRazao(pessoa.getNomeRazao());

        if(pessoa.getEnderecos().isEmpty()){
            pessoaDTO.setEndereco(null);
        } else {
            Endereco endereco = pessoa.getEnderecos().get(0);
            EnderecoDTO enderecoDTO = new EnderecoDTO();
            enderecoDTO.setBairro(endereco.getBairro());
            enderecoDTO.setRua(endereco.getRua());
            enderecoDTO.setId(endereco.getId());
            enderecoDTO.setComplemento(endereco.getComplemento());
            enderecoDTO.setCep(endereco.getCep());
            enderecoDTO.setNumero(endereco.getNumero());
            enderecoDTO.setCidadeIBGE(String.valueOf(endereco.getCidade().getCodIbge()));
            pessoaDTO.setEndereco(enderecoDTO);

        }

        return pessoaDTO;


    }

    public Pessoa findAgenteById(Long id) throws Exception{
        Optional<Pessoa> retorno = pessoaRepository.findById(id);

        if(retorno.isPresent()){
            if(retorno.get().getTipo() == TipoPessoaEnum.C){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa com id " +
                        id + " não é agente, informe outra pessoa");
            } else {
                return retorno.get();
            }
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa com id " + id + " não identificada");
        }
    }

    public List<PessoaFuncionarioDTO> findAllPessoaFuncionario() {

        return pessoaRepository.findAllPessoaFuncionarioDTO();

    }

    public List<PessoaEscoltaDTO> findAllPessoaEscoltaFuncionario() {

        return pessoaRepository.findAllPessoaEscoltaFuncionario();

    }

    public List<PessoaEscoltaDTO> findAllPessoaEscoltaCliente(String parametro) {

        if(parametro == null || parametro.trim() == ""){
            return pessoaRepository.findAllPessoaEscoltaCliente();
        }else{
            return pessoaRepository.findAllPessoaEscoltaCliente(parametro.toLowerCase(Locale.ROOT));
        }

    }


    public List<Pessoa> findAllByIds(List<Long> ids) throws Exception {
        List<Pessoa> agentes = new ArrayList<>();
        for (Long id : ids) {
            Pessoa agente = findAgenteById(id);
            agentes.add(agente);
        }
        return agentes;
    }

}
