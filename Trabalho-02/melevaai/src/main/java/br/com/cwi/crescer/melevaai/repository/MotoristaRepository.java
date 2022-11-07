package br.com.cwi.crescer.melevaai.repository;

import br.com.cwi.crescer.melevaai.domain.CPF;
import br.com.cwi.crescer.melevaai.domain.Motorista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MotoristaRepository extends JpaRepository<Motorista, Integer> {

    Motorista save(Motorista motorista);

    Motorista findByCpf(CPF cpf);

    List<Motorista> findAll();

    void delete(Motorista motorista);
}
