package br.com.cwi.crescer.melevaai.controller;


import br.com.cwi.crescer.melevaai.excepetion.*;
import br.com.cwi.crescer.melevaai.representation.request.CriarVeiculoRequest;
import br.com.cwi.crescer.melevaai.representation.response.ListarTodosVeiculosResponse;
import br.com.cwi.crescer.melevaai.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarVeiculos(@RequestBody @Valid CriarVeiculoRequest request) throws MotoristaNaoHabilitadoException, NaoCadastradoException, JaCadastradoException {

        service.criarVeiculo(request);

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    private ResponseEntity<List<ListarTodosVeiculosResponse>> todosVeiculos() {
        List<ListarTodosVeiculosResponse> listarTodosVeiculosResponses = service.listarVeiculos();

        if (listarTodosVeiculosResponses.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.ok(listarTodosVeiculosResponses);
    }
}
