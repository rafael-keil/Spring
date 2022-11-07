package br.com.cwi.crescer.melevaai.controller;

import br.com.cwi.crescer.melevaai.excepetion.NaoCadastradoException;
import br.com.cwi.crescer.melevaai.excepetion.SemSaldoException;
import br.com.cwi.crescer.melevaai.representation.request.ValorRequest;
import br.com.cwi.crescer.melevaai.representation.response.PassageiroResponse;
import br.com.cwi.crescer.melevaai.service.PassageiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/privado/passageiros")
public class PassageiroPrivadoController {

    @Autowired
    private PassageiroService service;

    @PutMapping("/{cpf}/conta-virtual")
    @ResponseStatus(HttpStatus.CREATED)
    private void incluirCredito(@RequestBody @Valid ValorRequest request, @PathVariable String cpf) throws SemSaldoException, NaoCadastradoException {

        service.depositarCredito(request.getValor(), cpf);

    }

    @GetMapping("/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    private PassageiroResponse getPassageiro(@PathVariable String cpf) throws NaoCadastradoException {

        return service.consultarPassageiroResponsePorCpf(cpf);

    }

}
