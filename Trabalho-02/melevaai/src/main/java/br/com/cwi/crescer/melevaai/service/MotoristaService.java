package br.com.cwi.crescer.melevaai.service;


import br.com.cwi.crescer.melevaai.domain.CPF;
import br.com.cwi.crescer.melevaai.domain.Motorista;
import br.com.cwi.crescer.melevaai.excepetion.*;
import br.com.cwi.crescer.melevaai.mapper.MotoristaMapper;
import br.com.cwi.crescer.melevaai.repository.MotoristaRepository;
import br.com.cwi.crescer.melevaai.representation.request.CriarMotoristaRequest;
import br.com.cwi.crescer.melevaai.representation.response.ListarTodosMotoristasResponse;
import br.com.cwi.crescer.melevaai.representation.response.MotoristaResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.cwi.crescer.melevaai.mapper.MotoristaMapper.toResponse;

@Service
public class MotoristaService {

    @Autowired
    private MotoristaRepository repository;

    @Autowired
    private VeiculoService veiculoService;

    private ModelMapper modelMapper = new ModelMapper();

    public Motorista criarMotorista(CriarMotoristaRequest request) throws JaCadastradoException, MotoristaComCNHVencidaException, ValidacaoNegocioException {

        Motorista motorista = MotoristaMapper.toDomain(request);

        if (repository.findByCpf(motorista.getCpf()) != null) {
            throw new JaCadastradoException("Motorista já cadastrado");
        }
        if(motorista.getCarteiraHabilitacao().isVencida()) {
            throw new MotoristaComCNHVencidaException("CNH Vencida");
        }
        if (motorista.validaIdadeMinima()) {
            throw new ValidacaoNegocioException("Motorista sem idade mínima.");
        }
        if (!motorista.getCpf().isCPF()) {
            throw new ValidacaoNegocioException("CPF inválido.");
        }

        return repository.save(motorista);
    }

    public void excluirMotorista(String cpf) throws NaoCadastradoException, MotoristaComVeiculoException {
        Motorista motorista = consultarMotoristaPorCpf(new CPF(cpf));

        if (veiculoService.isMotoristaComVeiculo(cpf)) {
            throw new MotoristaComVeiculoException("Motorista vinculado à um veículo.");
        }

        repository.delete(motorista);
    }

    public List<ListarTodosMotoristasResponse> listarMotoristas() {
        return repository.findAll()
                .stream()
                .map(motorista -> modelMapper.map(motorista, ListarTodosMotoristasResponse.class))
                .collect(Collectors.toList());
    }

    public void sacarCredito(double valor, String cpf) throws SemSaldoException, NaoCadastradoException {

        Motorista motorista = consultarMotoristaPorCpf(new CPF(cpf));

        if (motorista.getSaldo().getSaldo() < valor) {
            throw new SemSaldoException("Sem saldo suficiente.");
        }

        motorista.getSaldo().saque(valor);

        repository.save(motorista);
    }

    public MotoristaResponse consultarMotoristaResponsePorCpf(String cpf) throws NaoCadastradoException {

        Motorista motorista = consultarMotoristaPorCpf(new CPF(cpf));
        return toResponse(motorista);
    }

    public Motorista consultarMotoristaPorCpf(CPF cpf) throws NaoCadastradoException {

        Motorista motorista = repository.findByCpf(cpf);

        if (motorista == null) {
            throw new NaoCadastradoException("Motorista não cadastrado");
        }

        return motorista;
    }

}
