package br.com.cwi.crescer.melevaai.representation.request;

import br.com.cwi.crescer.melevaai.domain.Motorista;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CriarVeiculoRequest {

    @NotEmpty
    private String placa;

    @NotEmpty
    private String marca;

    @NotEmpty
    private String modelo;

    @NotNull
    private Integer ano;

    @NotEmpty
    private String cor;

    private String urlFoto;

    private Integer lugares;

    @NotEmpty
    private String categoriaCnh;

    @NotEmpty
    private String cpfMotorista;

    private Motorista motorista;

}
