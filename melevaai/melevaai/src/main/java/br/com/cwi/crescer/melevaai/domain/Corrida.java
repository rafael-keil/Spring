package br.com.cwi.crescer.melevaai.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Corrida {
    private UUID id;
    private Coordenada coordenadaInicio;
    private Coordenada coordenadaFim;
    private Veiculo veiculoSelecionado;
    private Passageiro passageiro;
    private LocalDateTime horaInicio;
    private LocalDateTime horaFinal;
    private Integer tempoEstimado;
    private boolean motoristaAvaliado;
    private boolean passageiroAvaliado;
    private EstadoCorrida estadoCorrida;

    public Corrida(Coordenada coordenadaInicio, Coordenada coordenadaFim,
                   Veiculo veiculoSelecionado, Passageiro passageiro) {
        this.coordenadaInicio = coordenadaInicio;
        this.coordenadaFim = coordenadaFim;
        this.veiculoSelecionado = veiculoSelecionado;
        this.passageiro = passageiro;

        this.id = UUID.randomUUID();
        this.horaInicio = LocalDateTime.now();
        this.tempoEstimado = ThreadLocalRandom.current().nextInt(5, 10 + 1);
        this.estadoCorrida = EstadoCorrida.ESPERANDO;
    }

    public double tempoEstimado() {
        double distanciaKm = Math.sqrt(
                Math.pow(coordenadaFim.getX() - coordenadaInicio.getX(), 2)
                        + Math.pow(coordenadaFim.getY() - coordenadaInicio.getY(), 2));

        return (distanciaKm / 30) * 3600;
    }

    public double calculoValor(double tempoDecorrido) {
        return tempoDecorrido * 0.2;
    }
}
