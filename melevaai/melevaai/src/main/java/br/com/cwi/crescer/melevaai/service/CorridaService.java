package br.com.cwi.crescer.melevaai.service;

import br.com.cwi.crescer.melevaai.domain.*;
import br.com.cwi.crescer.melevaai.excepetion.*;
import br.com.cwi.crescer.melevaai.mapper.CorridaMapper;
import br.com.cwi.crescer.melevaai.repository.CorridaRepository;
import br.com.cwi.crescer.melevaai.representation.request.CriarCorridaRequest;
import br.com.cwi.crescer.melevaai.representation.request.NotaRequest;
import br.com.cwi.crescer.melevaai.representation.response.CriarCorridaResponse;
import br.com.cwi.crescer.melevaai.representation.response.IniciarCorridaResponse;
import br.com.cwi.crescer.melevaai.representation.response.ListarTodasCorridasResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CorridaService {

    @Autowired
    private CorridaRepository repository;

    @Autowired
    private VeiculoService veiculoService;

    @Autowired
    private PassageiroService passageiroService;

    private ModelMapper modelMapper = new ModelMapper();

    public CriarCorridaResponse criarCorrida(CriarCorridaRequest request, String cpf) throws ValidacaoNegocioException, NaoCadastradoException {

        Veiculo veiculo = veiculoService.getVeiculoAleatorio();

        Passageiro passageiro = passageiroService.consultarPassageiroPorCpf(cpf);

        veiculo.getMotorista().setBusy(true);

        request.setPassageiro(passageiro);
        request.setVeiculo(veiculo);

        Corrida corrida = CorridaMapper.toDomain(request);
        repository.criarCorrida(corrida);

        return CorridaMapper.toCriarCorridaResponse(corrida);

    }

    public IniciarCorridaResponse iniciarCorrida(String id) throws NaoCadastradoException, CorridaIndisponivelException {

        Corrida corrida = consultarCorridaPorId(id);

        if (corrida.getEstadoCorrida() != EstadoCorrida.ESPERANDO) {
            throw new CorridaIndisponivelException("Corrida não está em espera.");
        }

        double tempoEstimado = corrida.tempoEstimado();

        IniciarCorridaResponse iniciarCorridaResponse = CorridaMapper.toIniciarCorridaResponse(tempoEstimado, corrida.calculoValor(tempoEstimado));
        corrida.setEstadoCorrida(EstadoCorrida.ANDAMENTO);

        return iniciarCorridaResponse;

    }

    public void finalizarCorrida(String id) throws NaoCadastradoException, CorridaIndisponivelException, SemSaldoException {

        Corrida corrida = consultarCorridaPorId(id);
        Passageiro passageiro = passageiroService.consultarPassageiroPorCpf(corrida.getPassageiro().getCpf().getNumero());

        if (corrida.getEstadoCorrida() != EstadoCorrida.ANDAMENTO) {
            throw new CorridaIndisponivelException("Corrida não está em andamento.");
        }

        corrida.setHoraFinal(LocalDateTime.now());
        double tempoPercorrido = ChronoUnit.SECONDS.between(corrida.getHoraInicio(), corrida.getHoraFinal());
        double cobranca = corrida.calculoValor(tempoPercorrido);

        if (passageiro.getSaldo().getSaldo() < cobranca) {
            throw new SemSaldoException("Passageiro sem saldo.");
        }

        corrida.setEstadoCorrida(EstadoCorrida.FINALIZADA);
        corrida.getVeiculoSelecionado().getMotorista().setBusy(false);

        corrida.getVeiculoSelecionado().getMotorista().getSaldo().deposito(cobranca);
        passageiro.getSaldo().saque(cobranca);

    }

    public List<ListarTodasCorridasResponse> listarCorridas() {
        return repository.listarCorridas()
                .stream()
                .map(corrida -> modelMapper.map(corrida, ListarTodasCorridasResponse.class))
                .collect(Collectors.toList());
    }

    public void avaliarMotorista(NotaRequest nota, String idCorrida) throws NaoCadastradoException, ValidacaoNegocioException, CorridaIndisponivelException, JaAvaliadoException {

        Corrida corrida = consultarCorridaPorId(idCorrida);

        avaliacaoValida(nota.getNota(), corrida);
        if (corrida.isMotoristaAvaliado()) {
            throw new JaAvaliadoException("Motorista já avaliado.");
        }

        corrida.getVeiculoSelecionado().getMotorista().getNotas().add(nota.getNota());
        corrida.setMotoristaAvaliado(true);
    }

    public void avaliarPassageiro(NotaRequest nota, String idCorrida) throws NaoCadastradoException, ValidacaoNegocioException, CorridaIndisponivelException, JaAvaliadoException {

        Corrida corrida = consultarCorridaPorId(idCorrida);
        Passageiro passageiro = passageiroService.consultarPassageiroPorCpf(corrida.getPassageiro().getCpf().getNumero());

        avaliacaoValida(nota.getNota(), corrida);
        if (corrida.isPassageiroAvaliado()) {
            throw new JaAvaliadoException("Passageiro já avaliado.");
        }

        passageiro.getNotas().add(nota.getNota());
        corrida.setPassageiroAvaliado(true);
    }

    public List<ListarTodasCorridasResponse> listarTodasCorridasMotorista(String cpf) throws NaoCadastradoException {

        return repository
                .listarCorridas()
                .stream()
                .filter(corrida -> corrida.getVeiculoSelecionado().getMotorista().getCpf().getNumero().equals(cpf))
                .map(corrida -> modelMapper.map(corrida, ListarTodasCorridasResponse.class))
                .collect(Collectors.toList());

    }

    public List<ListarTodasCorridasResponse> listarTodasCorridasPassageiro(String cpf) throws NaoCadastradoException {

        return repository
                .listarCorridas()
                .stream()
                .filter(corrida -> corrida.getPassageiro().getCpf().getNumero().equals(cpf))
                .map(corrida -> modelMapper.map(corrida, ListarTodasCorridasResponse.class))
                .collect(Collectors.toList());

    }


    public void avaliacaoValida(Integer nota, Corrida corrida) throws ValidacaoNegocioException, CorridaIndisponivelException {
        if (nota < 1 || nota > 5) {
            throw new ValidacaoNegocioException("Nota inválida.");
        }
        if (corrida.getEstadoCorrida() != EstadoCorrida.FINALIZADA) {
            throw new CorridaIndisponivelException("Corrida não finalizada.");
        }
    }


    public Corrida consultarCorridaPorId(String id) throws NaoCadastradoException {

        Corrida corrida = repository.consultarCorrida(id);

        if (corrida == null) {
            throw new NaoCadastradoException("Corrida não encontrada");
        }

        return corrida;
    }
}
