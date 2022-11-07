package br.com.cwi.crescer.melevaai.controller;

import br.com.cwi.crescer.melevaai.excepetion.MotoristaComVeiculoException;
import br.com.cwi.crescer.melevaai.excepetion.NaoCadastradoException;
import br.com.cwi.crescer.melevaai.excepetion.SemSaldoException;
import br.com.cwi.crescer.melevaai.representation.request.ValorRequest;
import br.com.cwi.crescer.melevaai.representation.response.MotoristaResponse;
import br.com.cwi.crescer.melevaai.service.MotoristaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/privado/motoristas")
public class MotoristaPrivadoController {

    @Autowired
    private MotoristaService service;

    @DeleteMapping("/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    public void excluirMotorista(@PathVariable String cpf) throws NaoCadastradoException, MotoristaComVeiculoException {

        service.excluirMotorista(cpf);

    }

    @PutMapping("/{cpf}/conta-virtual")
    private void sacarCredito(@RequestBody @Valid ValorRequest request, @PathVariable String cpf) throws SemSaldoException, NaoCadastradoException {

        service.sacarCredito(request.getValor(), cpf);

    }


    @GetMapping("/{cpf}")
    private MotoristaResponse getMotorista(@PathVariable String cpf) throws NaoCadastradoException {

        return service.consultarMotoristaResponsePorCpf(cpf);

    }

}
