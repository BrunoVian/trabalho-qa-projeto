package br.com.SecurityProfit.services;

import br.com.SecurityProfit.models.escolta.Empresa;
import br.com.SecurityProfit.repositories.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    public Empresa insert(Empresa empresa) throws Exception{
        return empresaRepository.saveAndFlush(empresa);
    }

    public Empresa edit(Empresa empresa) throws Exception{
        return empresaRepository.saveAndFlush(empresa);
    }

    public List<Empresa> findAll(){
        return empresaRepository.findAll();
    }

    public Empresa findById(Long id) throws Exception{
        Optional<Empresa> retorno = empresaRepository.findById(id);

        if(retorno.isPresent())
            return retorno.get();
        else
            throw new Exception("Empresa com ID " + id + " Não Identificada");
    }


}
