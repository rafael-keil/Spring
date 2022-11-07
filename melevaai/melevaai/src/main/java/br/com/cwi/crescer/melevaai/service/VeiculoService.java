package br.com.cwi.crescer.melevaai.service;

import br.com.cwi.crescer.melevaai.domain.Motorista;
import br.com.cwi.crescer.melevaai.domain.Veiculo;
import br.com.cwi.crescer.melevaai.excepetion.MotoristaComVeiculoException;
import br.com.cwi.crescer.melevaai.excepetion.MotoristaNaoHabilitadoException;
import br.com.cwi.crescer.melevaai.excepetion.NaoCadastradoException;
import br.com.cwi.crescer.melevaai.excepetion.ValidacaoNegocioException;
import br.com.cwi.crescer.melevaai.mapper.VeiculoMapper;
import br.com.cwi.crescer.melevaai.repository.VeiculoRepository;
import br.com.cwi.crescer.melevaai.representation.request.CriarVeiculoRequest;
import br.com.cwi.crescer.melevaai.representation.response.ListarTodosVeiculosResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository repository;

    @Autowired
    private MotoristaService motoristaService;

    private ModelMapper modelMapper = new ModelMapper();

    public boolean isMotoristaComVeiculo(String placa){

        return repository
                .listarVeiculos()
                .stream()
                .anyMatch(veiculo -> veiculo.getPlaca().equals(placa));
    }

    public List<ListarTodosVeiculosResponse> listarVeiculos() {
        return repository.listarVeiculos().stream()
                .map(motorista -> modelMapper.map(motorista, ListarTodosVeiculosResponse.class))
                .collect(Collectors.toList());
    }

    public void criarVeiculo(CriarVeiculoRequest request) throws NaoCadastradoException, ValidacaoNegocioException, MotoristaComVeiculoException, MotoristaNaoHabilitadoException {

        Motorista motorista = motoristaService.consultarMotoristaPorCpf(request.getCpfMotorista());

        request.setMotorista(motorista);

        Veiculo veiculo = VeiculoMapper.toDomain(request);

        if (repository.listarVeiculos().contains(veiculo) || veiculo.getMotorista() == null) {
            throw new ValidacaoNegocioException("Veículo já cadastrado.");
        }
        if (isMotoristaComVeiculo(veiculo.getPlaca())) {
            throw new MotoristaComVeiculoException("Motorista já possui veículo.");
        }
        if (!veiculo.isCategoriaCnhProprietarioValid()) {
            throw new MotoristaNaoHabilitadoException("Motorista não habilitado.");
        }

        repository.cadastrarVeiculo(veiculo);
    }

    public Veiculo getVeiculoAleatorio() throws ValidacaoNegocioException {
        List<Veiculo> veiculosFiltrados = repository
                .listarVeiculos()
                .stream()
                .filter(veiculo -> !veiculo.getMotorista().isBusy() && !veiculo.getMotorista().getCarteiraHabilitacao().isVencida())
                .collect(Collectors.toList());

        if (veiculosFiltrados.isEmpty()) {
            throw new ValidacaoNegocioException("Nenhum veiculo disponível");
        }
        if (veiculosFiltrados.size() == 1) {
            return veiculosFiltrados.get(0);
        }

        int indexAleatorio = ThreadLocalRandom.current().nextInt(0, veiculosFiltrados.size() - 1);
        return veiculosFiltrados.get(indexAleatorio);
    }
}
