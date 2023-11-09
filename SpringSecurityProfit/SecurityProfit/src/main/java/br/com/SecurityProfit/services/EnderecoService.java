package br.com.SecurityProfit.services;

import br.com.SecurityProfit.models.pessoa.Cidade;
import br.com.SecurityProfit.models.pessoa.Endereco;
import br.com.SecurityProfit.models.pessoa.EnderecoDTO;
import br.com.SecurityProfit.models.pessoa.Pessoa;
import br.com.SecurityProfit.repositories.CidadeRepository;
import br.com.SecurityProfit.repositories.EnderecoRepository;
import br.com.SecurityProfit.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    CidadeRepository cidadeRepository;

    public Endereco insert(EnderecoDTO enderecoDTO) throws Exception {
        /*Endereco endereco = new Endereco();

        Optional<Cidade> retornoCidade = cidadeRepository.findById(enderecoDTO.getCidadeId());

        if (retornoCidade.isPresent()) {
            Cidade cidade = retornoCidade.get();
            endereco.setCep(enderecoDTO.getCep());
            endereco.setNumero(enderecoDTO.getNumero());
            endereco.setStatus(enderecoDTO.getStatus());
            endereco.setBairro(enderecoDTO.getBairro());
            endereco.setComplemento(enderecoDTO.getComplemento());
            endereco.setRua(enderecoDTO.getRua());
            endereco.setDescricao(enderecoDTO.getDescricao());
            endereco.setCidade(cidade);
            Optional<Pessoa> retornoPessoa = pessoaRepository.findById(enderecoDTO.getPessoaId());
            if (retornoPessoa.isPresent()) {
                Pessoa pessoa = retornoPessoa.get();
                endereco.setPessoa(pessoa);
            } else {
                throw new Exception("Pessoa com id " + enderecoDTO.getPessoaId() + " não encontrada");
            }
        } else {
            throw new Exception("Cidade com id " + enderecoDTO.getCidadeId() + " não encontrada");
        }

        return enderecoRepository.saveAndFlush(endereco);
    }

    public Endereco edit(Endereco endereco) throws Exception {
        return enderecoRepository.saveAndFlush(endereco);
    }

    public List<EnderecoDTO> findAll() {
        List<Endereco> enderecos = enderecoRepository.findAll();
        List<EnderecoDTO> enderecoDTOs = new ArrayList<>();

        for (Endereco endereco : enderecos) {
            EnderecoDTO enderecoDTO = new EnderecoDTO();
            enderecoDTO.setId(endereco.getId());
            enderecoDTO.setDescricao(endereco.getDescricao());
            enderecoDTO.setCep(endereco.getCep());
            enderecoDTO.setRua(endereco.getRua());
            enderecoDTO.setBairro(endereco.getBairro());
            enderecoDTO.setComplemento(endereco.getComplemento());
            enderecoDTO.setStatus(endereco.getStatus());
            enderecoDTO.setPessoaId(endereco.getPessoa().getId());
            enderecoDTO.setCidadeId(endereco.getCidade().getId());

            enderecoDTOs.add(enderecoDTO);
        }

        return enderecoDTOs;
    }


    public void remove(Long id) throws Exception{
        Endereco endereco = findById(id);
        endereco.setStatus(false);
        enderecoRepository.saveAndFlush(endereco);
    }

    public Endereco findById(Long id) throws Exception {
        Optional<Endereco> retornoEndereco = enderecoRepository.findById(id);

        if(retornoEndereco.isPresent())
            return retornoEndereco.get();
        else
            throw new Exception("Endereço com id "+ id + "Não encontrado");
    }

    public EnderecoDTO findByIdDTO(Long id) throws Exception {
        Optional<Endereco> retornoEndereco = enderecoRepository.findById(id);

        if (retornoEndereco.isPresent()) {
            Endereco endereco = retornoEndereco.get();
            EnderecoDTO enderecoDTO = new EnderecoDTO();

            enderecoDTO.setId(endereco.getId());
            enderecoDTO.setDescricao(endereco.getDescricao());
            enderecoDTO.setCep(endereco.getCep());
            enderecoDTO.setRua(endereco.getRua());
            enderecoDTO.setBairro(endereco.getBairro());
            enderecoDTO.setComplemento(endereco.getComplemento());
            enderecoDTO.setStatus(endereco.getStatus());
            enderecoDTO.setPessoaId(endereco.getPessoa().getId());
            enderecoDTO.setCidadeId(endereco.getCidade().getId());

            return enderecoDTO;
        } else {
            throw new Exception("Endereço com id " + id + "Não encontrado");
        }*/
        return null;
    }




}
