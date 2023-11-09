package br.com.SecurityProfit.repositories;

import br.com.SecurityProfit.models.checklist.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChecklistRepository  extends JpaRepository<Checklist, Long> {

}
