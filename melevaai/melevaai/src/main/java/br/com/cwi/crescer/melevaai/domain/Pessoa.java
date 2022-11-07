package br.com.cwi.crescer.melevaai.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "cpf")
public abstract class Pessoa {

    private String nome;
    private CPF cpf;
    private LocalDate dataNascimento;
    private String email;
    private ContaVirtual saldo;
    private List<Integer> notas;

    @JsonIgnore
    public int getIdade() {
        return Period.between(this.getDataNascimento(), LocalDate.now()).getYears();
    }

    protected abstract int getIdadeMinima();

    public boolean validaIdadeMinima() {
        int idadeMinima = this.getIdadeMinima();
        return this.getIdade() < idadeMinima;
    }

    public double mediaNotas(){
        double somaNota =  notas.stream().reduce(0, Integer::sum);
        return somaNota / notas.size();
    }

}
