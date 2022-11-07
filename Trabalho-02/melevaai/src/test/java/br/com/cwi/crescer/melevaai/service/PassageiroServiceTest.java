package br.com.cwi.crescer.melevaai.service;

import br.com.cwi.crescer.melevaai.domain.CPF;
import br.com.cwi.crescer.melevaai.domain.Passageiro;
import br.com.cwi.crescer.melevaai.excepetion.*;
import br.com.cwi.crescer.melevaai.fixture.PassageiroFixture;
import br.com.cwi.crescer.melevaai.mapper.PassageiroMapper;
import br.com.cwi.crescer.melevaai.repository.PassageiroRepository;
import br.com.cwi.crescer.melevaai.representation.request.CriarPassageiroRequest;
import br.com.cwi.crescer.melevaai.representation.response.ListarTodosPassageirosResponse;
import br.com.cwi.crescer.melevaai.representation.response.PassageiroResponse;
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
public class PassageiroServiceTest {

    @InjectMocks
    private PassageiroService tested;

    @Mock
    private PassageiroRepository repository;

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void quandoChamarCadastrarPassageiroDeveCadastrarMotorista() throws JaCadastradoException, ValidacaoNegocioException {

        CriarPassageiroRequest request = PassageiroFixture.requestCompleto();

        Passageiro passageiro = PassageiroFixture.passageiroCompleto();

        when(repository.save(passageiro))
                .thenReturn(passageiro);

        when(repository.findByCpf(passageiro.getCpf()))
                .thenReturn(null);

        Passageiro passageiroRetornado = tested.criarPassageiro(request);

        verify(repository).save(passageiro);
        verify(repository).findByCpf(passageiro.getCpf());

        assertEquals(request.getCpf(), passageiroRetornado.getCpf().getNumero());
    }

    @Test(expected = JaCadastradoException.class)
    public void quandoMotoristaJaTiverCadastradoDeveRetornarException() throws JaCadastradoException, ValidacaoNegocioException {

        CriarPassageiroRequest request = PassageiroFixture.requestCompleto();

        Passageiro passageiro = PassageiroFixture.passageiroCompleto();

        when(repository.findByCpf(passageiro.getCpf()))
                .thenReturn(passageiro);

        tested.criarPassageiro(request);
    }

    @Test(expected = ValidacaoNegocioException.class)
    public void quandoMotoristaNaoTiverIdadeMinimaDeveRetornarException() throws JaCadastradoException, ValidacaoNegocioException {

        CriarPassageiroRequest request = PassageiroFixture.requestSemIdadeMinima();

        Passageiro passageiro = PassageiroFixture.passageiroCompleto();

        when(repository.findByCpf(passageiro.getCpf()))
                .thenReturn(null);

        tested.criarPassageiro(request);
    }

    @Test(expected = ValidacaoNegocioException.class)
    public void quandoCpfForInvalidoDeveRetornarException() throws JaCadastradoException, ValidacaoNegocioException {

        CriarPassageiroRequest request = PassageiroFixture.requestCpfInvalido();

        PassageiroFixture.passageiroCompleto();

        when(repository.findByCpf(any(CPF.class)))
                .thenReturn(null);

        tested.criarPassageiro(request);
    }

    @Test
    public void quandoChamarDepositarCreditoDeveAdicionarSaldoDoPassageiro() throws SemSaldoException, NaoCadastradoException {

        double valor = 10;

        Passageiro passageiro = PassageiroFixture.passageiroCompleto();

        when(repository.findByCpf(passageiro.getCpf()))
                .thenReturn(passageiro);

        tested.depositarCredito(valor, passageiro.getCpf().getNumero());

        passageiro.getSaldo().deposito(valor);

        verify(repository).findByCpf(passageiro.getCpf());
        verify(repository).save(passageiro);
    }

    @Test(expected = SemSaldoException.class)
    public void quandoDepositarValorMenorQueZeroDeveDarException() throws SemSaldoException, NaoCadastradoException {

        double valor = -10;

        Passageiro passageiro = PassageiroFixture.passageiroCompleto();

        when(repository.findByCpf(passageiro.getCpf()))
                .thenReturn(passageiro);

        tested.depositarCredito(valor, passageiro.getCpf().getNumero());

        verify(repository).findByCpf(passageiro.getCpf());
    }

    @Test
    public void quandoChamarListarPassageirosDeveRetornarTodosPassageirosResponse(){

        Passageiro passageiro = PassageiroFixture.passageiroCompleto();

        List<ListarTodosPassageirosResponse> listarTodosPassageiros = new ArrayList<>();
        listarTodosPassageiros.add(modelMapper.map(passageiro, ListarTodosPassageirosResponse.class));

        List<Passageiro> passageiroList = new ArrayList<>();
        passageiroList.add(passageiro);

        when(repository.findAll())
                .thenReturn(passageiroList);

        List<ListarTodosPassageirosResponse> listarTodosPassageirosResponse = tested.listarPassageiros();

        verify(repository).findAll();

        assertEquals(listarTodosPassageiros, listarTodosPassageirosResponse);
    }

    @Test
    public void quandoConsultarPassageiroResponsePorCpfDeveRetornarPassageiroReponse() throws NaoCadastradoException {

        Passageiro passageiro = PassageiroFixture.passageiroCompleto();

        PassageiroResponse passageiroResponse = PassageiroMapper.toResponse(passageiro);

        when(repository.findByCpf(passageiro.getCpf()))
                .thenReturn(passageiro);

        PassageiroResponse passageiroResponseTested = tested.consultarPassageiroResponsePorCpf(passageiro.getCpf().getNumero());

        verify(repository).findByCpf(passageiro.getCpf());

        assertEquals(passageiroResponseTested, passageiroResponse);

    }

    @Test
    public void quandoConsultarPassageiroPorCpfDeveRetornarPassageiro() throws NaoCadastradoException {

        Passageiro passageiro = PassageiroFixture.passageiroCompleto();

        when(repository.findByCpf(passageiro.getCpf()))
                .thenReturn(passageiro);

        tested.consultarPassageiroPorCpf(passageiro.getCpf());

        verify(repository).findByCpf(passageiro.getCpf());
    }

    @Test(expected = NaoCadastradoException.class)
    public void quandoConsultarPassageiroPorCpfInvalidoDeveRetornarException() throws NaoCadastradoException {

        Passageiro passageiro = PassageiroFixture.passageiroCompleto();

        when(repository.findByCpf(passageiro.getCpf()))
                .thenReturn(null);

        tested.consultarPassageiroPorCpf(passageiro.getCpf());

        verify(repository).findByCpf(passageiro.getCpf());
    }
}