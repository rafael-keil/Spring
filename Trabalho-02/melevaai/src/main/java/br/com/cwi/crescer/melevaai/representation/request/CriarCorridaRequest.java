package br.com.cwi.crescer.melevaai.representation.request;

import br.com.cwi.crescer.melevaai.domain.Passageiro;
import br.com.cwi.crescer.melevaai.domain.Veiculo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CriarCorridaRequest {

    private Passageiro passageiro;

    @NotNull
    private double xInicio;

    @NotNull
    private double yInicio;

    @NotNull
    private double xFim;

    @NotNull
    private double yFim;

    private Veiculo veiculo;

    public double getxInicio() {
        return xInicio;
    }

    public void setxInicio(double xInicio) {
        this.xInicio = xInicio;
    }

    public double getyInicio() {
        return yInicio;
    }

    public void setyInicio(double yInicio) {
        this.yInicio = yInicio;
    }

    public double getxFim() {
        return xFim;
    }

    public void setxFim(double xFim) {
        this.xFim = xFim;
    }

    public double getyFim() {
        return yFim;
    }

    public void setyFim(double yFim) {
        this.yFim = yFim;
    }
}
