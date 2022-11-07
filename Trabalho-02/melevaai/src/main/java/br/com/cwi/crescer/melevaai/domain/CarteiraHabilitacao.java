package br.com.cwi.crescer.melevaai.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "mla_cnh")
@SequenceGenerator(name = "mla_seq_cnh", sequenceName = "mla_seq_cnh", allocationSize = 1)
public class CarteiraHabilitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mla_seq_cnh")
    private Integer id;

    @Column(name = "categoria")
    private CategoriaCarteiraHabilitacao categoria;

    @Column(name = "numero")
    private String numero;

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    public CarteiraHabilitacao(CategoriaCarteiraHabilitacao categoria, String numero, LocalDate dataVencimento) {
        this.categoria = categoria;
        this.numero = numero;
        this.dataVencimento = dataVencimento;
    }

    @JsonIgnore
    public boolean isVencida() {
        return dataVencimento.isBefore(LocalDate.now());
    }
}
