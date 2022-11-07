package br.com.cwi.crescer.melevaai.repository;

import br.com.cwi.crescer.melevaai.domain.Corrida;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CorridaRepositoryEmMemoria implements CorridaRepository {

    List<Corrida> corridas = new ArrayList<>();

    @Override
    public Corrida criarCorrida(Corrida corrida){
        corridas.add(corrida);
        return corrida;
    }

    @Override
    public Corrida consultarCorrida(String id) {
        return corridas
                .stream()
                .filter(c -> c.getId().toString().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Corrida> listarCorridas() {
        return corridas;
    }


}

