package br.com.cwi.crescer.melevaai.fixture;

import br.com.cwi.crescer.melevaai.domain.Coordenada;
import br.com.cwi.crescer.melevaai.domain.Corrida;
import br.com.cwi.crescer.melevaai.representation.request.CriarCorridaRequest;

public class CorridaFixture {

    public static Corrida corridaCompleto() {

        return new Corrida(
                new Coordenada(
                        1,
                        1
                ),
                new Coordenada(
                        2,
                        2
                ),
                VeiculoFixture.veiculoCompleto(),
                PassageiroFixture.passageiroCompleto()
        );
    }

    public static CriarCorridaRequest requestCompleto() {

        return new CriarCorridaRequest(
                PassageiroFixture.passageiroCompleto(),
                1,
                1,
                2,
                2,
                VeiculoFixture.veiculoCompleto()
        );
    }

}
