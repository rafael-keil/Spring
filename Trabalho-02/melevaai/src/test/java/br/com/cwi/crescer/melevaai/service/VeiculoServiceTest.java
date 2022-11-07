package br.com.cwi.crescer.melevaai.service;

import br.com.cwi.crescer.melevaai.domain.CPF;
import br.com.cwi.crescer.melevaai.domain.Motorista;
import br.com.cwi.crescer.melevaai.domain.Veiculo;
import br.com.cwi.crescer.melevaai.excepetion.JaCadastradoException;
import br.com.cwi.crescer.melevaai.excepetion.MotoristaNaoHabilitadoException;
import br.com.cwi.crescer.melevaai.excepetion.NaoCadastradoException;
import br.com.cwi.crescer.melevaai.excepetion.ValidacaoNegocioException;
import br.com.cwi.crescer.melevaai.fixture.MotoristaFixture;
import br.com.cwi.crescer.melevaai.fixture.VeiculoFixture;
import br.com.cwi.crescer.melevaai.repository.VeiculoRepository;
import br.com.cwi.crescer.melevaai.representation.request.CriarVeiculoRequest;
import br.com.cwi.crescer.melevaai.representation.response.ListarTodosVeiculosResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VeiculoServiceTest {

    @InjectMocks
    private VeiculoService tested;

    @Mock
    private VeiculoRepository repository;

    @Mock
    private MotoristaService motoristaService;

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void quandoChamarlistarVeiculosDeveRetornarTodosVeiculosResponse(){

        Veiculo veiculo = VeiculoFixture.veiculoCompleto();

        List<ListarTodosVeiculosResponse> listarTodosVeiculos = new ArrayList<>();
        listarTodosVeiculos.add(modelMapper.map(veiculo, ListarTodosVeiculosResponse.class));

        List<Veiculo> veiculoListList = new ArrayList<>();
        veiculoListList.add(veiculo);

        when(repository.findAll())
                .thenReturn(veiculoListList);

        List<ListarTodosVeiculosResponse> listarTodosVeiculosResponse = tested.listarVeiculos();

        verify(repository).findAll();

        assertEquals(listarTodosVeiculos, listarTodosVeiculosResponse);
    }

    @Test
    public void quandoChamarCriarVeiculoDeveCriarVeiculo() throws MotoristaNaoHabilitadoException, NaoCadastradoException, JaCadastradoException {

        CriarVeiculoRequest request = VeiculoFixture.requestCompleto();

        Veiculo veiculo = VeiculoFixture.veiculoCompleto();

        Motorista motorista = MotoristaFixture.motoristaCompleto();

        when(motoristaService.consultarMotoristaPorCpf(new CPF(request.getCpfMotorista())))
                .thenReturn(motorista);

        when(repository.findAll())
                .thenReturn(new ArrayList<>());

        when(repository.save(veiculo))
                .thenReturn(veiculo);

        Veiculo veiculoRetornado = tested.criarVeiculo(request);

        verify(motoristaService).consultarMotoristaPorCpf(new CPF(request.getCpfMotorista()));
        verify(repository).findAll();
        verify(repository).save(veiculo);

        assertEquals(request.getPlaca(), veiculoRetornado.getPlaca());
    }

    @Test(expected = JaCadastradoException.class)
    public void quandoVeiculoJaEstiverCadastradoDeveRetornarException() throws MotoristaNaoHabilitadoException, NaoCadastradoException, JaCadastradoException {

        CriarVeiculoRequest request = VeiculoFixture.requestCompleto();

        Veiculo veiculo = VeiculoFixture.veiculoCompleto();

        List<Veiculo> veiculoList = new ArrayList<>();
        veiculoList.add(veiculo);

        Motorista motorista = MotoristaFixture.motoristaCompleto();

        when(motoristaService.consultarMotoristaPorCpf(new CPF(request.getCpfMotorista())))
                .thenReturn(motorista);

        when(repository.findAll())
                .thenReturn(veiculoList);

        tested.criarVeiculo(request);
    }

    @Test(expected = MotoristaNaoHabilitadoException.class)
    public void quandoMotoristaTiverCnhInvalidaDeveRetornarException() throws NaoCadastradoException, MotoristaNaoHabilitadoException, JaCadastradoException {

        CriarVeiculoRequest request = VeiculoFixture.requestCategoriaB();

        Motorista motorista = MotoristaFixture.motoristaCompleto();

        when(motoristaService.consultarMotoristaPorCpf(new CPF(request.getCpfMotorista())))
                .thenReturn(motorista);

        when(repository.findAll())
                .thenReturn(new ArrayList<>());

        tested.criarVeiculo(request);
    }

    @Test
    public void quandoGetVeiculoAleatorioTiverUmVeiculoDeveRetornarEle() throws ValidacaoNegocioException {

        Veiculo veiculo = VeiculoFixture.veiculoCompleto();

        List<Veiculo> veiculoList = new ArrayList<>();
        veiculoList.add(veiculo);

        when(repository.findAll())
                .thenReturn(veiculoList);

        Veiculo veiculoRetornado = tested.getVeiculoAleatorio();

        verify(repository).findAll();

        assertEquals(veiculo, veiculoRetornado);
    }

    @Test
    public void quandoGetVeiculoAleatorioTiverMaisDeveRetornarAleatorio() throws ValidacaoNegocioException {

        Veiculo veiculo = VeiculoFixture.veiculoCompleto();
        Veiculo veiculo2 = VeiculoFixture.veiculoDiferente();

        List<Veiculo> veiculoList = new ArrayList<>();
        veiculoList.add(veiculo);
        veiculoList.add(veiculo2);

        when(repository.findAll())
                .thenReturn(veiculoList);

       Veiculo veiculoRetornado = tested.getVeiculoAleatorio();

        assertTrue(veiculoList.contains(veiculoRetornado));
    }

    @Test(expected = ValidacaoNegocioException.class)
    public void quandoNenhumVeiculoEstiverDisponivelDeveRetornarException() throws ValidacaoNegocioException {

        Veiculo veiculo = VeiculoFixture.veiculoBusy();

        List<Veiculo> veiculoList = new ArrayList<>();
        veiculoList.add(veiculo);

        when(repository.findAll())
                .thenReturn(veiculoList);

        tested.getVeiculoAleatorio();
    }

    @Test
    public void quandoIsMotoristaComVeiculoDeveRetornarBool(){

        Veiculo veiculo = VeiculoFixture.veiculoCompleto();

        List<Veiculo> veiculoList = new ArrayList<>();
        veiculoList.add(veiculo);

        when(repository.findAll())
                .thenReturn(veiculoList);

        boolean booleanRetornado = tested.isMotoristaComVeiculo(veiculo.getMotorista().getCpf().getNumero());

        verify(repository).findAll();

        assertTrue(booleanRetornado);
    }
}