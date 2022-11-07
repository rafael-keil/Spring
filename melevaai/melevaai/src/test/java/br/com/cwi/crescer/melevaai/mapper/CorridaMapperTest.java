package br.com.cwi.crescer.melevaai.mapper;

import br.com.cwi.crescer.melevaai.domain.Cor;
import br.com.cwi.crescer.melevaai.domain.Corrida;
import br.com.cwi.crescer.melevaai.domain.Marca;
import br.com.cwi.crescer.melevaai.fixture.CorridaFixture;
import br.com.cwi.crescer.melevaai.representation.request.CriarCorridaRequest;
import br.com.cwi.crescer.melevaai.representation.response.CriarCorridaResponse;
import br.com.cwi.crescer.melevaai.representation.response.IniciarCorridaResponse;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CorridaMapperTest {

    private static CorridaMapper mapper = new CorridaMapper();

    @Test
    public void quandoInformarCriarCorridaRequestDeveRetornarUmaCorrida() {

        CriarCorridaRequest request = CorridaFixture.requestCompleto();

        Corrida corrida = mapper.toDomain(request);

        assertEquals(request.getVeiculo().getMotorista().getNome(), corrida.getVeiculoSelecionado().getMotorista().getNome());
        assertEquals(request.getVeiculo().getMotorista().getCpf().getNumero(), corrida.getVeiculoSelecionado().getMotorista().getCpf().getNumero());
        assertEquals(request.getVeiculo().getMotorista().getDataNascimento(), corrida.getVeiculoSelecionado().getMotorista().getDataNascimento());
        assertEquals(request.getVeiculo().getMotorista().getEmail(), corrida.getVeiculoSelecionado().getMotorista().getEmail());
        assertEquals(request.getVeiculo().getMotorista().getCarteiraHabilitacao().getCategoria(), corrida.getVeiculoSelecionado().getMotorista().getCarteiraHabilitacao().getCategoria());
        assertEquals(request.getVeiculo().getMotorista().getCarteiraHabilitacao().getDataVencimento(), corrida.getVeiculoSelecionado().getMotorista().getCarteiraHabilitacao().getDataVencimento());
        assertEquals(request.getVeiculo().getMotorista().getCarteiraHabilitacao().getNumero(), corrida.getVeiculoSelecionado().getMotorista().getCarteiraHabilitacao().getNumero());
        assertEquals(request.getVeiculo().getPlaca(), corrida.getVeiculoSelecionado().getPlaca());
        assertEquals(request.getVeiculo().getMarca(), corrida.getVeiculoSelecionado().getMarca());
        assertEquals(request.getVeiculo().getModelo(), corrida.getVeiculoSelecionado().getModelo());
        assertEquals(request.getVeiculo().getCor(), corrida.getVeiculoSelecionado().getCor());
        assertEquals(request.getVeiculo().getUrlFoto(), corrida.getVeiculoSelecionado().getUrlFoto());
        assertEquals(request.getVeiculo().getCategoriaCarteiraHabilitacao(), corrida.getVeiculoSelecionado().getCategoriaCarteiraHabilitacao());
        assertEquals(request.getPassageiro().getCpf().getNumero(), corrida.getPassageiro().getCpf().getNumero());
        assertEquals(request.getxInicio(), corrida.getCoordenadaInicio().getX(), 0);
        assertEquals(request.getyInicio(), corrida.getCoordenadaInicio().getY(), 0);
        assertEquals(request.getxFim(), corrida.getCoordenadaFim().getX(), 0);
        assertEquals(request.getyFim(), corrida.getCoordenadaFim().getY(), 0);

    }

    @Test
    public void quandoInformarTempoEstimadoEValorDeveRetornarUmIniciarCorridaResponse(){
        double tempoEstimado = 20.5;
        double valor = 50.2;

        IniciarCorridaResponse response = mapper.toIniciarCorridaResponse(tempoEstimado, valor);

        assertEquals(tempoEstimado, response.getTempoEstimado(), 0);
        assertEquals(valor, response.getPrecoEstimado(), 0);
    }

    @Test
    public void quandoInformarCorridaDeveRetornarUmCriarCorridaResponse() {

        Corrida corrida = CorridaFixture.corridaCompleto();

        CriarCorridaResponse response = mapper.toCriarCorridaResponse(corrida);

        assertEquals(response.getVeiculoSelecionado().getMotorista().getNome(), corrida.getVeiculoSelecionado().getMotorista().getNome());
        assertEquals(response.getVeiculoSelecionado().getPlaca(), corrida.getVeiculoSelecionado().getPlaca());
        assertEquals(Marca.valueOf(response.getVeiculoSelecionado().getMarca()), corrida.getVeiculoSelecionado().getMarca());
        assertEquals(response.getVeiculoSelecionado().getModelo(), corrida.getVeiculoSelecionado().getModelo());
        assertEquals(Cor.valueOf(response.getVeiculoSelecionado().getCor()), corrida.getVeiculoSelecionado().getCor());
        assertEquals(response.getVeiculoSelecionado().getUrlFoto(), corrida.getVeiculoSelecionado().getUrlFoto());
        assertEquals(response.getId(), corrida.getId().toString());

    }

}