package br.com.cwi.crescer.melevaai.mapper;

import br.com.cwi.crescer.melevaai.domain.Passageiro;
import br.com.cwi.crescer.melevaai.fixture.PassageiroFixture;
import br.com.cwi.crescer.melevaai.representation.request.CriarPassageiroRequest;
import br.com.cwi.crescer.melevaai.representation.response.PassageiroResponse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class PassageiroMapperTest {

    private static PassageiroMapper mapper = new PassageiroMapper();

    @Test
    public void quandoInformarCriarPassageiroRequestDeveRetornarUmPassageiro(){

        CriarPassageiroRequest request = PassageiroFixture.requestCompleto();

        Passageiro passageiro = mapper.toDomain(request);

        assertEquals(request.getNome(), passageiro.getNome());
        assertEquals(request.getCpf(), passageiro.getCpf().getNumero());
        assertEquals(request.getDataNascimento(), passageiro.getDataNascimento());
        assertEquals(request.getEmail(), passageiro.getEmail());
    }

    @Test
    public void quandoInformarPassageiroDeveRetornarUmPassageiroResponse(){

        Passageiro passageiro = PassageiroFixture.passageiroCompleto();

        PassageiroResponse response = mapper.toResponse(passageiro);

        assertEquals(response.getNome(), passageiro.getNome());
        assertEquals(response.getCpf().getNumero(), passageiro.getCpf().getNumero());
        assertEquals(response.getDataNascimento(), passageiro.getDataNascimento());
        assertEquals(response.getEmail(), passageiro.getEmail());
        assertEquals(response.getSaldo().getSaldo(), passageiro.getSaldo().getSaldo(), 0);
        assertEquals(response.getNotas(), passageiro.getNotas());

    }
}