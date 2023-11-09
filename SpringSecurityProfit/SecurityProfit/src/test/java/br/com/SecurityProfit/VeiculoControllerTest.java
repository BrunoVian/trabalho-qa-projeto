package br.com.SecurityProfit;

import br.com.SecurityProfit.controllers.VeiculoController;
import br.com.SecurityProfit.models.escolta.Veiculo;
import br.com.SecurityProfit.services.VeiculoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class VeiculoControllerTest {

    @Mock
    private VeiculoService veiculoService;

    @InjectMocks
    private VeiculoController veiculoController;

    Veiculo veiculo = new Veiculo();

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInsertVeiculo() throws Exception {
        when(veiculoService.insert(any(Veiculo.class))).thenReturn(veiculo);

        ResponseEntity<Veiculo> responseEntity = veiculoController.insert(veiculo);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(veiculo, responseEntity.getBody());
    }

    @Test
    public void testEditVeiculo() throws Exception {
        when(veiculoService.insert(any(Veiculo.class))).thenReturn(veiculo);
        ResponseEntity<Veiculo> responseEntityInsert = veiculoController.insert(veiculo);
        Long veiculoId = responseEntityInsert.getBody().getId();

        Veiculo veiculoEditado = new Veiculo();
        veiculoEditado.setId(veiculoId);
        veiculoEditado.setMarca("Marca Editada");
        veiculoEditado.setCor("Cor Editada");

        when(veiculoService.edit(any(Veiculo.class))).thenReturn(veiculoEditado);
        ResponseEntity<Veiculo> responseEntityEdit = veiculoController.edit(veiculoEditado);

        assertEquals(HttpStatus.OK, responseEntityEdit.getStatusCode());

        Veiculo veiculoEditadoResponse = responseEntityEdit.getBody();
        assertEquals(veiculoEditado.getCor(), veiculoEditadoResponse.getCor());
        assertEquals(veiculoEditado.getModelo(), veiculoEditadoResponse.getModelo());
    }

    @Test
    public void testDeleteVeiculo() throws Exception {
        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);

        doNothing().when(veiculoService).remove(1L);

        ResponseEntity<?> responseEntity = veiculoController.delete(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(veiculoService, times(1)).remove(1L);
    }

}
