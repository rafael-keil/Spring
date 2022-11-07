package br.com.cwi.crescer.melevaai.service;

import br.com.cwi.crescer.melevaai.domain.Motorista;
import br.com.cwi.crescer.melevaai.excepetion.*;
import br.com.cwi.crescer.melevaai.fixture.MotoristaFixture;
import br.com.cwi.crescer.melevaai.repository.MotoristaRepository;
import br.com.cwi.crescer.melevaai.representation.request.CriarMotoristaRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MotoristaServiceTest {

    @InjectMocks
    private MotoristaService motoristaService;

    @Mock
    private MotoristaRepository repository;

    @Mock
    private VeiculoService veiculoService;

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void quandoChamarCadastrarVeiculoDeveCadastrarVeiculoBaseEmMemoria() throws JaCadastradoException, MotoristaComCNHVencidaException, ValidacaoNegocioException {

        CriarMotoristaRequest request = MotoristaFixture.requestCompleto();

        Motorista motorista = MotoristaFixture.motoristaCompleto();

        when(repository.criarMotorista(any(Motorista.class)))
                .thenReturn(motorista);

        when(repository.consultarMotorista(request.getCpf()))
                .thenReturn(null);

        Motorista motoristaRetornado = motoristaService.criarMotorista(request);

        verify(repository).criarMotorista(any(Motorista.class));
        verify(repository).consultarMotorista(any(String.class));

        assertEquals(request.getCpf(), motoristaRetornado.getCpf().getNumero());
    }

    @Test(expected = JaCadastradoException.class)
    public void quandoMotoristaJaTiverCadastradoDeveRetornarException() throws JaCadastradoException, MotoristaComCNHVencidaException, ValidacaoNegocioException {

        CriarMotoristaRequest request = MotoristaFixture.requestCompleto();

        Motorista motorista = MotoristaFixture.motoristaCompleto();

        when(repository.consultarMotorista(request.getCpf()))
                .thenReturn(motorista);

        motoristaService.criarMotorista(request);

    }

    @Test(expected = MotoristaComCNHVencidaException.class)
    public void quandoMotoristaTiverCnhVencidaDeveRetornarException() throws JaCadastradoException, MotoristaComCNHVencidaException, ValidacaoNegocioException {

        CriarMotoristaRequest request = MotoristaFixture.requestSemIdadeMinima();

        when(repository.consultarMotorista(request.getCpf()))
                .thenReturn(null);

        motoristaService.criarMotorista(request);

    }

    @Test(expected = ValidacaoNegocioException.class)
    public void quandoMotoristaNaoTiverIdadeMinimaDeveRetornarException() throws JaCadastradoException, MotoristaComCNHVencidaException, ValidacaoNegocioException {

        CriarMotoristaRequest request = MotoristaFixture.requestCnhVencida();

        when(repository.consultarMotorista(request.getCpf()))
                .thenReturn(null);

        motoristaService.criarMotorista(request);

    }

    @Test(expected = ValidacaoNegocioException.class)
    public void quandoCpfForInvalidoDeveRetornarException() throws JaCadastradoException, MotoristaComCNHVencidaException, ValidacaoNegocioException {

        CriarMotoristaRequest request = MotoristaFixture.requestCpfInvalido();

        when(repository.consultarMotorista(request.getCpf()))
                .thenReturn(null);

        motoristaService.criarMotorista(request);

    }

    @Test
    public void quandoChamarCadastrarExcluirMotoristaDeveExcluirMotorista() throws MotoristaComVeiculoException, NaoCadastradoException {

        Motorista motorista = MotoristaFixture.motoristaCompleto();

        when(veiculoService.isMotoristaComVeiculo(any(String.class)))
                .thenReturn(false);

        when(repository.consultarMotorista(any(String.class)))
                .thenReturn(motorista);

        motoristaService.excluirMotorista("82089223073");

        verify(veiculoService).isMotoristaComVeiculo(any(String.class));
        verify(repository).removerMotorista(any(Motorista.class));

    }

    @Test(expected = MotoristaComVeiculoException.class)
    public void quandoMotoristaTiverVeiculoDeveDarException() throws MotoristaComVeiculoException, NaoCadastradoException {

        Motorista motorista = MotoristaFixture.motoristaCompleto();

        when(veiculoService.isMotoristaComVeiculo(any(String.class)))
                .thenReturn(true);

        when(repository.consultarMotorista(any(String.class)))
                .thenReturn(motorista);

        motoristaService.excluirMotorista("82089223073");

    }




}