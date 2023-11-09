package br.com.SecurityProfit.services;

import br.com.SecurityProfit.models.escolta.*;
import br.com.SecurityProfit.models.pessoa.Cidade;
import br.com.SecurityProfit.repositories.ArquivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ArquivoService {

    @Autowired
    ArquivoRepository arquivoRepository;

    @Autowired
    EscoltaService escoltaService;

    public String upload(ArquivoInsertDTO arquivoInsertDTO) throws Exception{

        try {
            Arquivo arquivo = new Arquivo();
            arquivo.setConteudo(arquivoInsertDTO.getConteudo());
            arquivo.setDescricao(arquivoInsertDTO.getDescricao());
            arquivo.setEscolta(escoltaService.findById(arquivoInsertDTO.getEscoltaId()));
            arquivoRepository.saveAndFlush(arquivo);

            return "Arquivo inserido com sucesso";
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Erro ao inserir o arquivo: " + ex.getMessage());
        }
    }

    public List<Arquivo> findArquivoByEscolta(Long escoltaId) {
        return arquivoRepository.findArquivoByEscoltaId(escoltaId);
    }

    public Arquivo findById(Long id) throws Exception {

        Optional<Arquivo> retorno = arquivoRepository.findById(id);

        if(retorno.isPresent())
            return retorno.get();
        else
            throw new Exception("Arquivo com id " + id + " não identificado.");
    }

    public void delete(Long id) throws Exception {
        Arquivo arquivo = findById(id);
        arquivoRepository.delete(arquivo);

    }

    public ArquivoEditDTO updateDescricao(ArquivoEditDTO arquivoEditDTO) throws Exception {
        Arquivo arquivo = findById(arquivoEditDTO.getArquivoId());
        arquivo.setDescricao(arquivoEditDTO.getNovaDescricao());
        arquivoRepository.saveAndFlush(arquivo);
        return arquivoEditDTO;
    }
}
