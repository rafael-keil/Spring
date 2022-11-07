package br.com.cwi.crescer.melevaai.fixture;

import br.com.cwi.crescer.melevaai.domain.*;
import br.com.cwi.crescer.melevaai.representation.request.CriarCarteiraHabilitacaoRequest;
import br.com.cwi.crescer.melevaai.representation.request.CriarMotoristaRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MotoristaFixture {

    public static Motorista motoristaCompleto() {

        List<Integer> notas = new ArrayList<>();

        return new Motorista(
                "nome1",
                new CPF("82089223073"),
                LocalDate.of(2001, 11, 29),
                "email@email.com",
                new ContaVirtual(0),
                notas,
                new CarteiraHabilitacao(
                        CategoriaCarteiraHabilitacao.A,
                        "1234526789",
                        LocalDate.now().plusYears(1)
                ));
    }

    public static CriarMotoristaRequest requestCompleto() {

        return new CriarMotoristaRequest(
                "nome1",
                "82089223073",
                "email@email.com",
                LocalDate.of(2001, 11, 29),
                new CriarCarteiraHabilitacaoRequest(
                        "1234526789",
                        CategoriaCarteiraHabilitacao.A,
                        LocalDate.now().plusYears(1)
                ));
    }

    public static  CriarMotoristaRequest requestSemIdadeMinima(){

        CriarMotoristaRequest request = requestCompleto();

        request.getCarteiraHabilitacao().setDataVencimento(LocalDate.now().minusYears(1));

        return request;

    }

    public static  CriarMotoristaRequest requestCnhVencida(){

        CriarMotoristaRequest request = requestCompleto();

        request.setDataNascimento( LocalDate.of(2010, 11, 29));

        return request;

    }

    public static  CriarMotoristaRequest requestCpfInvalido(){

        CriarMotoristaRequest request = requestCompleto();

        request.setCpf( "52555552" );

        return request;

    }


}
