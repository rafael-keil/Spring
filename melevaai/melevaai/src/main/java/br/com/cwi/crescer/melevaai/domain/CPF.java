package br.com.cwi.crescer.melevaai.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CPF {

    @NotEmpty
    private String numero;

    @JsonIgnore
    public boolean isCPF() {
        if (this.getNumero().equals("00000000000") ||
                this.getNumero().equals("11111111111") ||
                this.getNumero().equals("22222222222") || this.getNumero().equals("33333333333") ||
                this.getNumero().equals("44444444444") || this.getNumero().equals("55555555555") ||
                this.getNumero().equals("66666666666") || this.getNumero().equals("77777777777") ||
                this.getNumero().equals("88888888888") || this.getNumero().equals("99999999999") ||
                (this.getNumero().length() != 11))
            return (false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = (int) (this.getNumero().charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char) (r + 48);

            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (this.getNumero().charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char) (r + 48);

            if ((dig10 == this.getNumero().charAt(9)) && (dig11 == this.getNumero().charAt(10)))
                return (true);
            else return (false);
        } catch (RuntimeException erro) {
            return (false);
        }
    }
}
