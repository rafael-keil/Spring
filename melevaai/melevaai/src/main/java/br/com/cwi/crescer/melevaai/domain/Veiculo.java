package br.com.cwi.crescer.melevaai.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "placa")
public class Veiculo {

    private String placa;
    private Marca marca;
    private String modelo;
    private int ano;
    private Cor cor;
    private String urlFoto;
    private CategoriaCarteiraHabilitacao categoriaCarteiraHabilitacao;
    private int lugares;
    private Motorista motorista;

    public boolean isCategoriaCnhProprietarioValid() {
        return this.getCategoriaCarteiraHabilitacao() == this.getMotorista().getCarteiraHabilitacao().getCategoria();
    }

}
