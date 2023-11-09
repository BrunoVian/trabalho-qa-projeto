package br.com.SecurityProfit.services;

import br.com.SecurityProfit.models.pessoa.Cidade;
import br.com.SecurityProfit.repositories.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CidadeService {

    @Autowired
    CidadeRepository cidadeRepository;

    public Cidade insert(Cidade cidade) throws Exception{

        return cidadeRepository.saveAndFlush(cidade);
    }

    public Cidade findById(Long id) throws Exception{
        Optional<Cidade> retorno = cidadeRepository.findById(id);

        if(retorno.isPresent())
            return retorno.get();
        else
            throw new Exception("Cidade com id " + id + " não identificada");
    }

    public Cidade findByCodIbge(String codIbge) throws ResponseStatusException {

        int cod = Integer.parseInt(codIbge);

        Optional<Cidade> retorno = cidadeRepository.findCidadeByCodIbge(cod);

        if(retorno.isPresent())
            return retorno.get();
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cidade com ibge " + codIbge + " não identificada.");
    }

    public List<Cidade> findAll(){
        return cidadeRepository.findAll();
    }
}
