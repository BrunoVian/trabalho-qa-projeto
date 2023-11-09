package br.com.SecurityProfit.services;

import br.com.SecurityProfit.models.pessoa.Estado;
import br.com.SecurityProfit.repositories.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadoService {

    @Autowired
    EstadoRepository estadoRepository;

    public Estado insert(Estado estado) throws Exception{

        return estadoRepository.saveAndFlush(estado);
    }

    public Estado findById(Long id) throws Exception{
        Optional<Estado> retorno = estadoRepository.findById(id);

        if(retorno.isPresent())
            return retorno.get();
        else
            throw new Exception("Estado com id " + id + " não identificado");
    }

    public List<Estado> findAll(){
        return estadoRepository.findAll();
    }

}
