package br.com.cwi.crescer.melevaai.repository;

import br.com.cwi.crescer.melevaai.domain.CategoriaCarteiraHabilitacao;
import br.com.cwi.crescer.melevaai.domain.Veiculo;

import java.util.List;

public interface VeiculoRepository {

    void cadastrarVeiculo(Veiculo veiculo);

    public List<Veiculo> listarVeiculos();

    List<Veiculo> consultarVeiculoPorCategoria(CategoriaCarteiraHabilitacao categoria);
}
