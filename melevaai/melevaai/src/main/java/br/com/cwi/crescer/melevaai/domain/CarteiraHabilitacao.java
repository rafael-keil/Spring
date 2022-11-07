package br.com.cwi.crescer.melevaai.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarteiraHabilitacao {

    private CategoriaCarteiraHabilitacao categoria;
    private String numero;
    private LocalDate dataVencimento;

    @JsonIgnore
    public boolean isVencida() {
        return dataVencimento.isBefore(LocalDate.now());
    }
}
