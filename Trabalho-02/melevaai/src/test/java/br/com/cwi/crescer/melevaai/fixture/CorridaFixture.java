package br.com.cwi.crescer.melevaai.fixture;

import br.com.cwi.crescer.melevaai.domain.Coordenada;
import br.com.cwi.crescer.melevaai.domain.Corrida;
import br.com.cwi.crescer.melevaai.domain.EstadoCorrida;
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

    public static Corrida corridaAndamento(){

        Corrida corrida = corridaCompleto();

        corrida.setEstadoCorrida(EstadoCorrida.ANDAMENTO);

        return corrida;
    }

    public static Corrida corridaAndamentoSemSaldo(){

        Corrida corrida = corridaAndamento();

        corrida.getPassageiro().getSaldo().setSaldo(0);

        return corrida;
    }

    public static Corrida corridaFinalizada(){

        Corrida corrida = corridaAndamento();

        corrida.setEstadoCorrida(EstadoCorrida.FINALIZADA);

        return corrida;
    }

    public static Corrida corridaFinalizadaAvaliada(){

        Corrida corrida = corridaFinalizada();

        corrida.setMotoristaAvaliado(true);
        corrida.setPassageiroAvaliado(true);

        return corrida;
    }
}
