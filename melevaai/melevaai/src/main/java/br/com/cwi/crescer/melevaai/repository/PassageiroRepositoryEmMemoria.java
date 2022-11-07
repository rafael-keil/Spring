package br.com.cwi.crescer.melevaai.repository;

import br.com.cwi.crescer.melevaai.domain.Passageiro;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PassageiroRepositoryEmMemoria implements PassageiroRepository {

    private static List<Passageiro> passageiros = new ArrayList<>();

    @Override
    public Passageiro consultarPassageiro(String cpf) {
        return passageiros.stream()
                .filter(m -> m.getCpf().getNumero().equals(cpf))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Passageiro criarPassageiro(Passageiro passageiro) {
        passageiros.add(passageiro);
        return passageiro;
    }

    @Override
    public List<Passageiro> listarPassageiros() {
        return passageiros;
    }

    @Override
    public void removerPassageiro(Passageiro passageiro) {
        passageiros.remove(passageiro);
    }
}
