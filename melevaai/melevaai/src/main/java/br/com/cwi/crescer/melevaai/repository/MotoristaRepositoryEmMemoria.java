package br.com.cwi.crescer.melevaai.repository;

import br.com.cwi.crescer.melevaai.domain.Motorista;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MotoristaRepositoryEmMemoria implements MotoristaRepository {

    private static List<Motorista> motoristas = new ArrayList<>();

    @Override
    public Motorista consultarMotorista(String cpf) {
        return motoristas
                .stream()
                .filter(m -> m.getCpf().getNumero().equals(cpf))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Motorista criarMotorista(Motorista motorista) {
        motoristas.add(motorista);
        return motorista;
    }

    @Override
    public List<Motorista> listarMotoristas() {
        return motoristas;
    }

    @Override
    public void removerMotorista(Motorista motorista) {
        motoristas.remove(motorista);
    }
}
