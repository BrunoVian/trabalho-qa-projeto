package br.com.SecurityProfit.services;

import br.com.SecurityProfit.models.escolta.Veiculo;
import br.com.SecurityProfit.repositories.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;


    public Veiculo insert(Veiculo veiculo) throws Exception {
        return veiculoRepository.saveAndFlush(veiculo);
    }

    public Veiculo edit(Veiculo veiculo) throws Exception {
        return veiculoRepository.saveAndFlush(veiculo);
    }

    public void remove(Long id) throws Exception {

        Veiculo veiculo = findById(id);
        veiculoRepository.delete(veiculo);

    }

    public Veiculo findById(Long id) throws Exception {
        Optional<Veiculo> retorno = veiculoRepository.findById(id);

        if (retorno.isPresent()) {
            if (retorno.get().getStatus() == true) {
                return retorno.get();
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Veículo com id " + id + " inativo.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Veículo com id " + id + " não identificado.");
        }

    }

    public List<Veiculo> findAll() {

        return veiculoRepository.findAllByOrderByPlacaAscModeloAsc();

    }

    public List<Veiculo> findByFilters(String termoBusca) {
        return veiculoRepository.findByModeloOrPlacaContainingAllIgnoringCase(termoBusca);
    }

    public List<Veiculo> findAllByIds(List<Long> ids) throws Exception {
        List<Veiculo> veiculos = new ArrayList<>();
        for (Long id : ids) {
            Veiculo veiculo = findById(id);
            veiculos.add(veiculo);
        }
        return veiculos;
    }


}
