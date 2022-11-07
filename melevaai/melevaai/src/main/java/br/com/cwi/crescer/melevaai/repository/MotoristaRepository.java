package br.com.cwi.crescer.melevaai.repository;

import br.com.cwi.crescer.melevaai.domain.Motorista;

import java.util.List;

public interface MotoristaRepository {

    Motorista consultarMotorista(String cpf);

    Motorista criarMotorista(Motorista motorista);

    List<Motorista> listarMotoristas();

    void removerMotorista(Motorista motorista);

}
