package br.com.cwi.crescer.melevaai.fixture;

import br.com.cwi.crescer.melevaai.domain.CPF;
import br.com.cwi.crescer.melevaai.domain.ContaVirtual;
import br.com.cwi.crescer.melevaai.domain.Passageiro;
import br.com.cwi.crescer.melevaai.representation.request.CriarPassageiroRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PassageiroFixture {

    public static Passageiro passageiroCompleto() {

        List<Integer> notas = new ArrayList<>();

        return new Passageiro(
                "nome1",
                new CPF("82089223073"),
                LocalDate.of(2001, 11, 29),
                "email@email.com",
                new ContaVirtual(0),
                notas
        );
    }

    public static CriarPassageiroRequest requestCompleto() {

        return new CriarPassageiroRequest(
                "nome1",
                "82089223073",
                LocalDate.of(2001, 11, 29),
                "email@email.com"
        );
    }


}
