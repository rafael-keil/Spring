package br.com.cwi.crescer.melevaai.repository;

import br.com.cwi.crescer.melevaai.domain.CPF;
import br.com.cwi.crescer.melevaai.domain.Passageiro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PassageiroRepository extends JpaRepository<Passageiro, Integer> {

    Passageiro save(Passageiro passageiro);

    Passageiro findByCpf(CPF cpf);

    List<Passageiro> findAll();

    void deleteByCpf(String cpf);
}
