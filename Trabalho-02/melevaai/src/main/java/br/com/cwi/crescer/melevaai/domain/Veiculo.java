package br.com.cwi.crescer.melevaai.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "mla_veiculo")
@SequenceGenerator(name = "mla_seq_veiculo", sequenceName = "mla_seq_veiculo", allocationSize = 1)
@EqualsAndHashCode(of = {"placa"})
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mla_seq_veiculo")
    private Integer id;

    @Column(name = "placa")
    private String placa;

    @Column(name = "marca")
    private Marca marca;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "ano")
    private int ano;

    @Column(name = "cor")
    private Cor cor;

    @Column(name = "foto")
    private String urlFoto;

    @Column(name = "categoria_cnh")
    private CategoriaCarteiraHabilitacao categoriaCarteiraHabilitacao;

    @Column(name = "lugares")
    private int lugares;

    @ManyToOne(cascade = CascadeType.ALL)
    private Motorista motorista;

    public Veiculo(String placa, Marca marca, String modelo, int ano, Cor cor, String urlFoto, CategoriaCarteiraHabilitacao categoriaCarteiraHabilitacao, int lugares, Motorista motorista) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
        this.urlFoto = urlFoto;
        this.categoriaCarteiraHabilitacao = categoriaCarteiraHabilitacao;
        this.lugares = lugares;
        this.motorista = motorista;
    }

    public boolean isCategoriaCnhProprietarioValid() {
        return this.getCategoriaCarteiraHabilitacao() == this.getMotorista().getCarteiraHabilitacao().getCategoria();
    }

}
