package br.com.cwi.crescer.melevaai.service;

import br.com.cwi.crescer.melevaai.domain.CPF;
import br.com.cwi.crescer.melevaai.domain.Motorista;
import br.com.cwi.crescer.melevaai.excepetion.*;
import br.com.cwi.crescer.melevaai.fixture.MotoristaFixture;
import br.com.cwi.crescer.melevaai.mapper.MotoristaMapper;
import br.com.cwi.crescer.melevaai.repository.MotoristaRepository;
import br.com.cwi.crescer.melevaai.representation.request.CriarMotoristaRequest;
import br.com.cwi.crescer.melevaai.representation.response.ListarTodosMotoristasResponse;
import br.com.cwi.crescer.melevaai.representation.response.MotoristaResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MotoristaServiceTest {

    @InjectMocks
    private MotoristaService tested;

    @Mock
    private MotoristaRepository repository;

    @Mock
    private VeiculoService veiculoService;

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void quandoChamarCadastrarMotoristaDeveCadastrarMotorista() throws JaCadastradoException, MotoristaComCNHVencidaException, ValidacaoNegocioException {

        CriarMotoristaRequest request = MotoristaFixture.requestCompleto();

        Motorista motorista = MotoristaFixture.motoristaCompleto();

        when(repository.save(motorista))
                .thenReturn(motorista);

        when(repository.findByCpf(motorista.getCpf()))
                .thenReturn(null);

        Motorista motoristaRetornado = tested.criarMotorista(request);

        verify(repository).save(motorista);
        verify(repository).findByCpf(motorista.getCpf());

        assertEquals(request.getCpf(), motoristaRetornado.getCpf().getNumero());
    }

    @Test(expected = JaCadastradoException.class)
    public void quandoMotoristaJaTiverCadastradoDeveRetornarException() throws JaCadastradoException, MotoristaComCNHVencidaException, ValidacaoNegocioException {

        CriarMotoristaRequest request = MotoristaFixture.requestCompleto();

        Motorista motorista = MotoristaFixture.motoristaCompleto();

        when(repository.findByCpf(motorista.getCpf()))
                .thenReturn(motorista);

        tested.criarMotorista(request);
    }

    @Test(expected = MotoristaComCNHVencidaException.class)
    public void quandoMotoristaTiverCnhVencidaDeveRetornarException() throws JaCadastradoException, MotoristaComCNHVencidaException, ValidacaoNegocioException {

        CriarMotoristaRequest request = MotoristaFixture.requestCnhVencida();

        Motorista motorista = MotoristaFixture.motoristaCompleto();

        when(repository.findByCpf(motorista.getCpf()))
                .thenReturn(null);

        tested.criarMotorista(request);
    }

    @Test(expected = ValidacaoNegocioException.class)
    public void quandoMotoristaNaoTiverIdadeMinimaDeveRetornarException() throws JaCadastradoException, MotoristaComCNHVencidaException, ValidacaoNegocioException {

        CriarMotoristaRequest request = MotoristaFixture.requestSemIdadeMinima();

        Motorista motorista = MotoristaFixture.motoristaCompleto();

        when(repository.findByCpf(motorista.getCpf()))
                .thenReturn(null);

        tested.criarMotorista(request);
    }

    @Test(expected = ValidacaoNegocioException.class)
    public void quandoCpfForInvalidoDeveRetornarException() throws JaCadastradoException, MotoristaComCNHVencidaException, ValidacaoNegocioException {

        CriarMotoristaRequest request = MotoristaFixture.requestCpfInvalido();

        Motorista motorista = MotoristaFixture.motoristaCompleto();

        when(repository.findByCpf(any(CPF.class)))
                .thenReturn(null);

        tested.criarMotorista(request);
    }

    @Test
    public void quandoChamarCadastrarExcluirMotoristaDeveExcluirMotorista() throws MotoristaComVeiculoException, NaoCadastradoException {

        Motorista motorista = MotoristaFixture.motoristaCompleto();

        when(veiculoService.isMotoristaComVeiculo(motorista.getCpf().getNumero()))
                .thenReturn(false);

        when(repository.findByCpf(motorista.getCpf()))
                .thenReturn(motorista);

        tested.excluirMotorista(motorista.getCpf().getNumero());

        verify(repository).findByCpf(motorista.getCpf());
        verify(veiculoService).isMotoristaComVeiculo(motorista.getCpf().getNumero());
        verify(repository).delete(motorista);
    }

    @Test(expected = MotoristaComVeiculoException.class)
    public void quandoMotoristaTiverVeiculoDeveDarException() throws MotoristaComVeiculoException, NaoCadastradoException {

        Motorista motorista = MotoristaFixture.motoristaCompleto();

        when(veiculoService.isMotoristaComVeiculo(any(String.class)))
                .thenReturn(true);

        when(repository.findByCpf(motorista.getCpf()))
                .thenReturn(motorista);

        tested.excluirMotorista(motorista.getCpf().getNumero());

        verify(repository).findByCpf(motorista.getCpf());
        verify(veiculoService).isMotoristaComVeiculo(motorista.getCpf().getNumero());
    }

    @Test
    public void quandoChamarListarMotoristasDeveRetornarTodosMotoristasResponse(){

        Motorista motorista = MotoristaFixture.motoristaCompleto();

        List<ListarTodosMotoristasResponse> listarTodosMotoristas = new ArrayList<>();
        listarTodosMotoristas.add(modelMapper.map(motorista, ListarTodosMotoristasResponse.class));

        List<Motorista> motoristaList = new ArrayList<>();
        motoristaList.add(motorista);

        when(repository.findAll()).thenReturn(motoristaList);

        List<ListarTodosMotoristasResponse> listarTodosMotoristasResponse = tested.listarMotoristas();

        verify(repository).findAll();

        assertEquals(listarTodosMotoristas, listarTodosMotoristasResponse);
    }

    @Test
    public void quandoChamarSacarCreditoDeveRetirarSaldoDoMotorista() throws SemSaldoException, NaoCadastradoException {

        double valor = 10;

        Motorista motorista = MotoristaFixture.motoristaComSaldo();

        when(repository.findByCpf(motorista.getCpf()))
                .thenReturn(motorista);

        tested.sacarCredito(valor, motorista.getCpf().getNumero());

        motorista.getSaldo().saque(valor);

        verify(repository).findByCpf(motorista.getCpf());
        verify(repository).save(motorista);
    }

    @Test(expected = SemSaldoException.class)
    public void quandoChamarSacarCreditoSemSaldoDeveRetornarException() throws SemSaldoException, NaoCadastradoException {

        double valor = 10;

        Motorista motorista = MotoristaFixture.motoristaCompleto();

        when(repository.findByCpf(motorista.getCpf()))
                .thenReturn(motorista);

        tested.sacarCredito(valor, motorista.getCpf().getNumero());

        verify(repository).findByCpf(motorista.getCpf());
    }

    @Test
    public void quandoConsultarMotoristaResponsePorCpfDeveRetornarMotoristaReponse() throws NaoCadastradoException {

        Motorista motorista = MotoristaFixture.motoristaCompleto();

        MotoristaResponse motoristaResponse = MotoristaMapper.toResponse(motorista);

        when(repository.findByCpf(motorista.getCpf()))
                .thenReturn(motorista);

        MotoristaResponse motoristaResponseTested = tested.consultarMotoristaResponsePorCpf(motorista.getCpf().getNumero());

        verify(repository).findByCpf(motorista.getCpf());

    }

    @Test
    public void quandoConsultarMotoristaPorCpfDeveRetornarMotorista() throws NaoCadastradoException {

        Motorista motorista = MotoristaFixture.motoristaCompleto();

        when(repository.findByCpf(motorista.getCpf()))
                .thenReturn(motorista);

        tested.consultarMotoristaPorCpf(motorista.getCpf());

        verify(repository).findByCpf(motorista.getCpf());
    }

    @Test(expected = NaoCadastradoException.class)
    public void quandoConsultarMotoristaPorCpfInvalidoDeveRetornarException() throws NaoCadastradoException {

        Motorista motorista = MotoristaFixture.motoristaCompleto();

        when(repository.findByCpf(motorista.getCpf()))
                .thenReturn(null);

        tested.consultarMotoristaPorCpf(motorista.getCpf());

        verify(repository).findByCpf(motorista.getCpf());
    }
}