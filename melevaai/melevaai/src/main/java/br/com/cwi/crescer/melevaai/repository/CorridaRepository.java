package br.com.cwi.crescer.melevaai.repository;

import br.com.cwi.crescer.melevaai.domain.Corrida;

import java.util.List;

public interface CorridaRepository {

    Corrida criarCorrida(Corrida corrida);

    Corrida consultarCorrida(String id);

    List<Corrida> listarCorridas();

}
