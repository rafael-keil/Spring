package br.com.cwi.crescer.melevaai.mapper;

import br.com.cwi.crescer.melevaai.domain.Motorista;
import br.com.cwi.crescer.melevaai.fixture.MotoristaFixture;
import br.com.cwi.crescer.melevaai.representation.request.CriarMotoristaRequest;
import br.com.cwi.crescer.melevaai.representation.response.MotoristaResponse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MotoristaMapperTest {

    private static MotoristaMapper mapper = new MotoristaMapper();

    @Test
    public void quandoInformarCriarMotoristaRequestDeveRetornarUmMotorista(){

        CriarMotoristaRequest request = MotoristaFixture.requestCompleto();

        Motorista motorista = mapper.toDomain(request);

        assertEquals(request.getNome(), motorista.getNome());
        assertEquals(request.getCpf(), motorista.getCpf().getNumero());
        assertEquals(request.getDataNascimento(), motorista.getDataNascimento());
        assertEquals(request.getEmail(), motorista.getEmail());
        assertEquals(request.getCarteiraHabilitacao().getCategoria(), motorista.getCarteiraHabilitacao().getCategoria());
        assertEquals(request.getCarteiraHabilitacao().getDataVencimento(), motorista.getCarteiraHabilitacao().getDataVencimento());
        assertEquals(request.getCarteiraHabilitacao().getNumero(), motorista.getCarteiraHabilitacao().getNumero());
    }

    @Test
    public void quandoInformarMotoristaDeveRetornarUmMotoristaResponse(){

        Motorista motorista = MotoristaFixture.motoristaCompleto();

        MotoristaResponse response = mapper.toResponse(motorista);

        assertEquals(response.getNome(), motorista.getNome());
        assertEquals(response.getCpf().getNumero(), motorista.getCpf().getNumero());
        assertEquals(response.getDataNascimento(), motorista.getDataNascimento());
        assertEquals(response.getEmail(), motorista.getEmail());
        assertEquals(response.getSaldo().getSaldo(), motorista.getSaldo().getSaldo(), 0);
        assertEquals(response.getNotas(), motorista.getNotas());
        assertEquals(response.getCarteiraHabilitacao().getCategoria(), motorista.getCarteiraHabilitacao().getCategoria());
        assertEquals(response.getCarteiraHabilitacao().getDataVencimento(), motorista.getCarteiraHabilitacao().getDataVencimento());
        assertEquals(response.getCarteiraHabilitacao().getNumero(), motorista.getCarteiraHabilitacao().getNumero());
    }

}