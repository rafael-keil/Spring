package br.com.cwi.crescer.melevaai.repository;

import br.com.cwi.crescer.melevaai.domain.Passageiro;

import java.util.List;

public interface PassageiroRepository {

    Passageiro criarPassageiro(Passageiro passageiro);

    Passageiro consultarPassageiro(String cpf);

    List<Passageiro> listarPassageiros();

    void removerPassageiro(Passageiro passageiro);
}
