package br.com.cwi.crescer.melevaai.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaVirtual {

    private double saldo;

    public void saque(double valorDeSaque) {
        setSaldo(getSaldo() - valorDeSaque);
    }

    public void deposito(double valorDeDeposito) {
        setSaldo(getSaldo() + valorDeDeposito);
    }
}
