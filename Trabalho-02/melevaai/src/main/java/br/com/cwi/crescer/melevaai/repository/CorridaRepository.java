package br.com.cwi.crescer.melevaai.repository;

import br.com.cwi.crescer.melevaai.domain.Corrida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CorridaRepository extends JpaRepository<Corrida, Integer> {

    Corrida save(Corrida corrida);

    Corrida findById(UUID id);

    List<Corrida> findAll();
}
