package br.com.SecurityProfit;

import br.com.SecurityProfit.controllers.ChecklistController;
import br.com.SecurityProfit.models.checklist.Checklist;
import br.com.SecurityProfit.models.checklist.ChecklistEditDTO;
import br.com.SecurityProfit.services.ChecklistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ChecklistControllerTest {

    @Mock
    private ChecklistService checklistService;

    @InjectMocks
    private ChecklistController checklistController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInsert() throws Exception {
        Checklist checklist = new Checklist();
        checklist.setId(1L);
        when(checklistService.insert(any())).thenReturn(checklist);

        ResponseEntity<Checklist> response = checklistController.insert(new Checklist());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testEdit() throws Exception {
        ChecklistEditDTO checklistEditDto = new ChecklistEditDTO();
        checklistEditDto.setChecklistId(1L);

        Checklist checklist = new Checklist();
        checklist.setId(1L);

        when(checklistService.edit(any())).thenReturn(checklist);

        ResponseEntity<Checklist> response = checklistController.edit(checklistEditDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(checklist.getNome(), response.getBody().getNome());
    }

    @Test
    void testDelete() throws Exception {
        checklistController.delete(1L);

        verify(checklistService, times(1)).delete(1L);
    }

    @Test
    void testFindAll() {
        List<Checklist> checklists = new ArrayList<>();
        checklists.add(new Checklist());
        when(checklistService.findAll()).thenReturn(checklists);

        ResponseEntity<List<Checklist>> response = checklistController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testFindById() throws Exception {
        Checklist checklist = new Checklist();
        checklist.setId(1L);
        when(checklistService.findById(1L)).thenReturn(checklist);

        ResponseEntity<Checklist> response = checklistController.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
    }
}
