package br.com.cwi.crescer.melevaai.mapper;

import br.com.cwi.crescer.melevaai.domain.CategoriaCarteiraHabilitacao;
import br.com.cwi.crescer.melevaai.domain.Cor;
import br.com.cwi.crescer.melevaai.domain.Marca;
import br.com.cwi.crescer.melevaai.domain.Veiculo;
import br.com.cwi.crescer.melevaai.fixture.VeiculoFixture;
import br.com.cwi.crescer.melevaai.representation.request.CriarVeiculoRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VeiculoMapperTest {

    private static VeiculoMapper mapper = new VeiculoMapper();

    @Test
    public void quandoInformarCriarVeiculoRequestDeveRetornarUmVeiculo() {

        CriarVeiculoRequest request = VeiculoFixture.requestCompleto();

        Veiculo veiculo = mapper.toDomain(request);

        assertEquals(request.getMotorista().getNome(), veiculo.getMotorista().getNome());
        assertEquals(request.getMotorista().getCpf().getNumero(), veiculo.getMotorista().getCpf().getNumero());
        assertEquals(request.getMotorista().getDataNascimento(), veiculo.getMotorista().getDataNascimento());
        assertEquals(request.getMotorista().getEmail(), veiculo.getMotorista().getEmail());
        assertEquals(request.getMotorista().getCarteiraHabilitacao().getCategoria(), veiculo.getMotorista().getCarteiraHabilitacao().getCategoria());
        assertEquals(request.getMotorista().getCarteiraHabilitacao().getDataVencimento(), veiculo.getMotorista().getCarteiraHabilitacao().getDataVencimento());
        assertEquals(request.getMotorista().getCarteiraHabilitacao().getNumero(), veiculo.getMotorista().getCarteiraHabilitacao().getNumero());
        assertEquals(request.getPlaca(), veiculo.getPlaca());
        assertEquals(Marca.valueOf(request.getMarca()), veiculo.getMarca());
        assertEquals(request.getModelo(), veiculo.getModelo());
        assertEquals(Cor.valueOf(request.getCor()), veiculo.getCor());
        assertEquals(request.getUrlFoto(), veiculo.getUrlFoto());
        assertEquals(CategoriaCarteiraHabilitacao.valueOf(request.getCategoriaCnh()), veiculo.getCategoriaCarteiraHabilitacao());

    }
}