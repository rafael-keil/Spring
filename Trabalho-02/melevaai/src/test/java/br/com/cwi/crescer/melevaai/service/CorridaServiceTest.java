package br.com.cwi.crescer.melevaai.service;

import br.com.cwi.crescer.melevaai.domain.*;
import br.com.cwi.crescer.melevaai.excepetion.*;
import br.com.cwi.crescer.melevaai.fixture.CorridaFixture;
import br.com.cwi.crescer.melevaai.fixture.PassageiroFixture;
import br.com.cwi.crescer.melevaai.fixture.VeiculoFixture;
import br.com.cwi.crescer.melevaai.repository.CorridaRepository;
import br.com.cwi.crescer.melevaai.representation.request.CriarCorridaRequest;
import br.com.cwi.crescer.melevaai.representation.request.NotaRequest;
import br.com.cwi.crescer.melevaai.representation.response.CriarCorridaResponse;
import br.com.cwi.crescer.melevaai.representation.response.IniciarCorridaResponse;
import br.com.cwi.crescer.melevaai.representation.response.ListarTodasCorridasResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CorridaServiceTest {

    @InjectMocks
    private CorridaService tested;

    @Mock
    private CorridaRepository repository;

    @Mock
    private VeiculoService veiculoService;

    @Mock
    private PassageiroService passageiroService;

    private ModelMapper modelMapper = new ModelMapper();

    @Captor
    private ArgumentCaptor<Corrida> corridaArgumentCaptor;

    @Test
    public void quandoChamarCadastrarCorridaDeveCadastrarCorrida() throws ValidacaoNegocioException, NaoCadastradoException {

        CriarCorridaRequest request = CorridaFixture.requestCompleto();

        Corrida corrida = CorridaFixture.corridaCompleto();

        Veiculo veiculo = VeiculoFixture.veiculoCompleto();

        Passageiro passageiro = PassageiroFixture.passageiroCompleto();

        when(veiculoService.getVeiculoAleatorio())
                .thenReturn(veiculo);

        when(passageiroService.consultarPassageiroPorCpf(any(CPF.class)))
                .thenReturn(passageiro);

        CriarCorridaResponse corridaRetornada = tested.criarCorrida(request, passageiro.getCpf().getNumero());

        verify(veiculoService).getVeiculoAleatorio();

        verify(passageiroService).consultarPassageiroPorCpf(passageiro.getCpf());

        verify(repository).save(any(Corrida.class));

        assertEquals(corrida.getVeiculoSelecionado().getPlaca() ,corridaRetornada.getVeiculoSelecionado().getPlaca());
    }

    @Test
    public void quandoChamarIniciarCorridaDeveIniciar() throws NaoCadastradoException, CorridaIndisponivelException {

        Corrida corrida = CorridaFixture.corridaCompleto();

        when(repository.findById(corrida.getId()))
                .thenReturn(corrida);

        IniciarCorridaResponse corridaRetornada = tested.iniciarCorrida(corrida.getId().toString());

        corrida.setEstadoCorrida(EstadoCorrida.ANDAMENTO);

        verify(repository).findById(corrida.getId());

        verify(repository).save(corrida);


        assertEquals(corrida.tempoEstimado(), corridaRetornada.getTempoEstimado(), 0.01);
    }

    @Test(expected = CorridaIndisponivelException.class)
    public void quandoChamarIniciarCorridaEmAndamentoDeveRetornarException() throws NaoCadastradoException, CorridaIndisponivelException {

        Corrida corrida = CorridaFixture.corridaAndamento();

        when(repository.findById(corrida.getId()))
                .thenReturn(corrida);

        tested.iniciarCorrida(corrida.getId().toString());
    }

    @Test
    public void quandoChamarFinalizarCorridaDeveFinalizar() throws NaoCadastradoException, CorridaIndisponivelException, SemSaldoException {

        Corrida corrida = CorridaFixture.corridaAndamento();

        when(repository.findById(corrida.getId()))
                .thenReturn(corrida);

        tested.finalizarCorrida(corrida.getId().toString());

        corrida.setHoraFinal(LocalDateTime.now());
        double tempoPercorrido = ChronoUnit.SECONDS.between(corrida.getHoraInicio(), corrida.getHoraFinal());
        double cobranca = corrida.calculoValor(tempoPercorrido);
        corrida.setEstadoCorrida(EstadoCorrida.FINALIZADA);
        corrida.getVeiculoSelecionado().getMotorista().setBusy(false);
        corrida.getVeiculoSelecionado().getMotorista().getSaldo().deposito(cobranca);
        corrida.getPassageiro().getSaldo().saque(cobranca);

        verify(repository).findById(corrida.getId());

        verify(repository).save(corridaArgumentCaptor.capture());

        assertEquals(corrida, corridaArgumentCaptor.getValue());
    }

    @Test(expected = CorridaIndisponivelException.class)
    public void quandoChamarFinalizarEmEsperaDeveRetornarException() throws NaoCadastradoException, CorridaIndisponivelException, SemSaldoException {

        Corrida corrida = CorridaFixture.corridaCompleto();

        when(repository.findById(corrida.getId()))
                .thenReturn(corrida);

        tested.finalizarCorrida(corrida.getId().toString());
    }

    @Test(expected = SemSaldoException.class)
    public void quandoChamarFinalizarDeveRetornarExceptionSePassageiroEstiverSemSaldo() throws NaoCadastradoException, CorridaIndisponivelException, SemSaldoException {

        Corrida corrida = CorridaFixture.corridaAndamentoSemSaldo();

        when(repository.findById(corrida.getId()))
                .thenReturn(corrida);

        tested.finalizarCorrida(corrida.getId().toString());
    }

    @Test
    public void quandoChamarListarCorridasDeveRetornarTodasCorridasResponse(){

        Corrida corrida = CorridaFixture.corridaCompleto();

        List<ListarTodasCorridasResponse> listarTodasCorridas = new ArrayList<>();
        listarTodasCorridas.add(modelMapper.map(corrida, ListarTodasCorridasResponse.class));

        List<Corrida> corridaList = new ArrayList<>();
        corridaList.add(corrida);

        when(repository.findAll())
                .thenReturn(corridaList);

        List<ListarTodasCorridasResponse> listarTodasCorridasResponse = tested.listarCorridas();

        verify(repository).findAll();

        assertEquals(listarTodasCorridas, listarTodasCorridasResponse);
    }

    @Test
    public void quandoChamarListarCorridasPassageiroDeveRetornarTodasCorridasResponse(){

        Corrida corrida = CorridaFixture.corridaCompleto();

        List<ListarTodasCorridasResponse> listarTodasCorridas = new ArrayList<>();
        listarTodasCorridas.add(modelMapper.map(corrida, ListarTodasCorridasResponse.class));

        List<Corrida> corridaList = new ArrayList<>();
        corridaList.add(corrida);

        when(repository.findAll())
                .thenReturn(corridaList);

        List<ListarTodasCorridasResponse> listarTodasCorridasResponse = tested.listarTodasCorridasPassageiro(corrida.getPassageiro().getCpf().getNumero());

        verify(repository).findAll();

        assertEquals(listarTodasCorridas, listarTodasCorridasResponse);
    }

    @Test
    public void quandoChamarListarCorridasPassageiroDeveRetornarNenhumMatch(){

        Corrida corrida = CorridaFixture.corridaCompleto();

        List<Corrida> corridaList = new ArrayList<>();
        corridaList.add(corrida);

        when(repository.findAll())
                .thenReturn(corridaList);

        List<ListarTodasCorridasResponse> listarTodasCorridasResponse = tested.listarTodasCorridasMotorista("1354");

        assertTrue(listarTodasCorridasResponse.isEmpty());
    }

    @Test
    public void quandoChamarListarCorridasMotoristaDeveRetornarTodasCorridasResponse(){

        Corrida corrida = CorridaFixture.corridaCompleto();

        List<ListarTodasCorridasResponse> listarTodasCorridas = new ArrayList<>();
        listarTodasCorridas.add(modelMapper.map(corrida, ListarTodasCorridasResponse.class));

        List<Corrida> corridaList = new ArrayList<>();
        corridaList.add(corrida);

        when(repository.findAll())
                .thenReturn(corridaList);

        List<ListarTodasCorridasResponse> listarTodasCorridasResponse = tested.listarTodasCorridasMotorista(corrida.getVeiculoSelecionado().getMotorista().getCpf().getNumero());

        verify(repository).findAll();

        assertEquals(listarTodasCorridas, listarTodasCorridasResponse);
    }

    @Test
    public void quandoChamarListarCorridasMotoristaDeveRetornarNenhumMatch(){

        Corrida corrida = CorridaFixture.corridaCompleto();

        List<Corrida> corridaList = new ArrayList<>();
        corridaList.add(corrida);

        when(repository.findAll())
                .thenReturn(corridaList);

        List<ListarTodasCorridasResponse> listarTodasCorridasResponse = tested.listarTodasCorridasMotorista("1354");

        assertTrue(listarTodasCorridasResponse.isEmpty());
    }

    @Test
    public void quandoAvaliarMotoristaDeveDarNota() throws ValidacaoNegocioException, JaAvaliadoException, NaoCadastradoException, CorridaIndisponivelException {

        Corrida corrida = CorridaFixture.corridaFinalizada();

        NotaRequest nota = new NotaRequest();
        nota.setNota(5);

        when(repository.findById(corrida.getId()))
                .thenReturn(corrida);

        tested.avaliarMotorista(nota, corrida.getId().toString());

        verify(repository).findById(corrida.getId());

        verify(repository).save(corridaArgumentCaptor.capture());

        List<Integer> notas = corridaArgumentCaptor.getValue().getVeiculoSelecionado().getMotorista().getNotas();

        assertEquals(nota.getNota(), notas.get(notas.size()-1));
    }

    @Test(expected = JaAvaliadoException.class)
    public void quandoMotoristaJaEstiverAvaliadoDeveRetornarException() throws ValidacaoNegocioException, JaAvaliadoException, NaoCadastradoException, CorridaIndisponivelException {

        Corrida corrida = CorridaFixture.corridaFinalizadaAvaliada();

        NotaRequest nota = new NotaRequest();
        nota.setNota(5);

        when(repository.findById(corrida.getId()))
                .thenReturn(corrida);

        tested.avaliarMotorista(nota, corrida.getId().toString());

        verify(repository).findById(corrida.getId());
    }

    @Test
    public void quandoAvaliarPassageiroDeveDarNota() throws ValidacaoNegocioException, JaAvaliadoException, NaoCadastradoException, CorridaIndisponivelException {

        Corrida corrida = CorridaFixture.corridaFinalizada();

        NotaRequest nota = new NotaRequest();
        nota.setNota(5);

        when(repository.findById(corrida.getId()))
                .thenReturn(corrida);

        tested.avaliarPassageiro(nota, corrida.getId().toString());

        verify(repository).findById(corrida.getId());

        verify(repository).save(corridaArgumentCaptor.capture());

        List<Integer> notas = corridaArgumentCaptor.getValue().getPassageiro().getNotas();

        assertEquals(nota.getNota(), notas.get(notas.size()-1));
    }

    @Test(expected = JaAvaliadoException.class)
    public void quandoPassageiroJaEstiverAvaliadoDeveRetornarException() throws ValidacaoNegocioException, JaAvaliadoException, NaoCadastradoException, CorridaIndisponivelException {

        Corrida corrida = CorridaFixture.corridaFinalizadaAvaliada();

        NotaRequest nota = new NotaRequest();
        nota.setNota(5);

        when(repository.findById(corrida.getId()))
                .thenReturn(corrida);

        tested.avaliarPassageiro(nota, corrida.getId().toString());

        verify(repository).findById(corrida.getId());
    }

    @Test(expected = ValidacaoNegocioException.class)
    public void quandoNotaForInvalidaDeveRetornarException() throws ValidacaoNegocioException, CorridaIndisponivelException {

        Corrida corrida = CorridaFixture.corridaFinalizadaAvaliada();

        tested.avaliacaoValida(6, corrida);
    }

    @Test(expected = CorridaIndisponivelException.class)
    public void quandoCorridaEstiverEmEstadoErradoDeveRetornarException() throws ValidacaoNegocioException, CorridaIndisponivelException {

        Corrida corrida = CorridaFixture.corridaAndamento();

        tested.avaliacaoValida(5, corrida);
    }

    @Test
    public void quandoCorridaPorIdDeveRetornarCorrida() throws NaoCadastradoException {

        Corrida corrida = CorridaFixture.corridaCompleto();

        when(repository.findById(corrida.getId()))
                .thenReturn(corrida);

        tested.consultarCorridaPorId(corrida.getId().toString());

        verify(repository).findById(corrida.getId());
    }

    @Test(expected = NaoCadastradoException.class)
    public void quandoConsultarCorridaPorIdInvalidoDeveRetornarException() throws NaoCadastradoException {

        Corrida corrida = CorridaFixture.corridaCompleto();

        when(repository.findById(corrida.getId()))
                .thenReturn(null);

        tested.consultarCorridaPorId(corrida.getId().toString());

        verify(repository).findById(corrida.getId());
    }
}