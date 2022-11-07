package br.com.cwi.crescer.melevaai.fixture;

import br.com.cwi.crescer.melevaai.domain.CPF;
import br.com.cwi.crescer.melevaai.domain.Passageiro;
import br.com.cwi.crescer.melevaai.representation.request.CriarPassageiroRequest;

import java.time.LocalDate;

public class PassageiroFixture {

    public static Passageiro passageiroCompleto() {

        Passageiro passageiro = new Passageiro(
                "nome1",
                new CPF("82089223073"),
                LocalDate.of(2001, 11, 29),
                "email@email.com"
        );

        passageiro.getSaldo().deposito(1000);

        return passageiro;
    }

    public static CriarPassageiroRequest requestCompleto() {

        return new CriarPassageiroRequest(
                "nome1",
                "82089223073",
                LocalDate.of(2001, 11, 29),
                "email@email.com"
        );
    }


    public static CriarPassageiroRequest requestSemIdadeMinima() {

        CriarPassageiroRequest request = requestCompleto();

        request.setDataNascimento( LocalDate.of(2010, 11, 29));

        return request;
    }

    public static CriarPassageiroRequest requestCpfInvalido() {

        CriarPassageiroRequest request = requestCompleto();

        request.setCpf("aaaaaa");

        return request;
    }
}
