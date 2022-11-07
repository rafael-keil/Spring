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
import java.util.UUID;
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

        Passageiro passageiro = passageiroService.consultarPassageiroPorCpf(new CPF(cpf));

        veiculo.getMotorista().setBusy(true);

        request.setPassageiro(passageiro);
        request.setVeiculo(veiculo);

        Corrida corrida = CorridaMapper.toDomain(request);
        repository.save(corrida);

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

        repository.save(corrida);

        return iniciarCorridaResponse;

    }

    public void finalizarCorrida(String id) throws NaoCadastradoException, CorridaIndisponivelException, SemSaldoException {

        Corrida corrida = consultarCorridaPorId(id);

        if (corrida.getEstadoCorrida() != EstadoCorrida.ANDAMENTO) {
            throw new CorridaIndisponivelException("Corrida não está em andamento.");
        }

        corrida.setHoraFinal(LocalDateTime.now().plusSeconds(1));
        double tempoPercorrido = ChronoUnit.SECONDS.between(corrida.getHoraInicio(), corrida.getHoraFinal());
        double cobranca = corrida.calculoValor(tempoPercorrido);

        if (corrida.getPassageiro().getSaldo().getSaldo() < cobranca) {
            throw new SemSaldoException("Passageiro sem saldo.");
        }

        corrida.setEstadoCorrida(EstadoCorrida.FINALIZADA);
        corrida.getVeiculoSelecionado().getMotorista().setBusy(false);

        corrida.getVeiculoSelecionado().getMotorista().getSaldo().deposito(cobranca);
        corrida.getPassageiro().getSaldo().saque(cobranca);

        repository.save(corrida);
    }

    public List<ListarTodasCorridasResponse> listarCorridas() {
        return repository.findAll()
                .stream()
                .map(corrida -> modelMapper.map(corrida, ListarTodasCorridasResponse.class))
                .collect(Collectors.toList());
    }

    public List<ListarTodasCorridasResponse> listarTodasCorridasPassageiro(String cpf) {

        return repository
                .findAll()
                .stream()
                .filter(corrida -> corrida.getPassageiro().getCpf().getNumero().equals(cpf))
                .map(corrida -> modelMapper.map(corrida, ListarTodasCorridasResponse.class))
                .collect(Collectors.toList());
    }

    public List<ListarTodasCorridasResponse> listarTodasCorridasMotorista(String cpf) {

        return repository
                .findAll()
                .stream()
                .filter(corrida -> corrida.getVeiculoSelecionado().getMotorista().getCpf().getNumero().equals(cpf))
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

        repository.save(corrida);
    }

    public void avaliarPassageiro(NotaRequest nota, String idCorrida) throws NaoCadastradoException, ValidacaoNegocioException, CorridaIndisponivelException, JaAvaliadoException {

        Corrida corrida = consultarCorridaPorId(idCorrida);

        avaliacaoValida(nota.getNota(), corrida);
        if (corrida.isPassageiroAvaliado()) {
            throw new JaAvaliadoException("Passageiro já avaliado.");
        }

        corrida.getPassageiro().getNotas().add(nota.getNota());
        corrida.setPassageiroAvaliado(true);

        repository.save(corrida);
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

        Corrida corrida = repository.findById(UUID.fromString(id));

        if (corrida == null) {
            throw new NaoCadastradoException("Corrida não encontrada");
        }

        return corrida;
    }
}
