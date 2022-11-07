package br.com.cwi.crescer.melevaai.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Data
@NoArgsConstructor
@Entity
@Table(name = "mla_corrida")
public class Corrida {

    @Id
    private UUID id;

    @Embedded
    @AttributeOverride(name = "x", column = @Column(name = "inicio_x"))
    @AttributeOverride(name = "y", column = @Column(name = "inicio_y"))
    private Coordenada coordenadaInicio;

    @Embedded
    @AttributeOverride(name = "x", column = @Column(name = "fim_x"))
    @AttributeOverride(name = "y", column = @Column(name = "fim_y"))
    private Coordenada coordenadaFim;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(cascade = CascadeType.REFRESH )
    private Veiculo veiculoSelecionado;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(cascade = CascadeType.REFRESH )
    private Passageiro passageiro;

    @Column(name = "hora_Inicio")
    private LocalDateTime horaInicio;

    @Column(name = "hora_final")
    private LocalDateTime horaFinal;

    @Column(name = "tempo_estimado")
    private Integer tempoEstimado;

    @Column(name = "motorista_avaliado")
    private boolean motoristaAvaliado;

    @Column(name = "passageiro_avaliado")
    private boolean passageiroAvaliado;

    @Column(name = "estado_corrida")
    private EstadoCorrida estadoCorrida;

    public Corrida(Coordenada coordenadaInicio, Coordenada coordenadaFim, Veiculo veiculoSelecionado, Passageiro passageiro) {
        this.coordenadaInicio = coordenadaInicio;
        this.coordenadaFim = coordenadaFim;
        this.veiculoSelecionado = veiculoSelecionado;
        this.passageiro = passageiro;

        this.horaInicio = LocalDateTime.now();
        this.tempoEstimado = ThreadLocalRandom.current().nextInt(5, 10 + 1);
        this.estadoCorrida = EstadoCorrida.ESPERANDO;
        this.id = UUID.randomUUID();
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
