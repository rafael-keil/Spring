### Api Consumer

- Para este projeto, foi utilizado o Insomnia e o JSON de consumo foi importado do mesmo.

# Me Leva Ai

![](https://cdn.icon-icons.com/icons2/1898/PNG/512/uber_121167.png)


## Ordem para as requisições pelo consumer
                
1. Criar passageiro
	+ POST cadastrarPassageiro
	+ PUT incluirCredito

2. Criar Motorista
	+ POST cadastrarMotorista

3. Criar Veículo
	+ POST cadastrarVeiculo

4. Fazer Corrida
    + POST chamarCorrida
      	+ Copiar o id da corrida retornado, para colocar no URL das seguintes
    + POST iniciarCorrida
    + POST finalizarCorrida
        + Após finalizar, pode se avaliar pelo avaliarMotorista e avaliarPassageiro
                

**Todos os métodos dispõem de seus devidos GET**

## Ordem para as requisições pelo Front
                
1. NPM Install

2. NPM start

3. A interface é intuitiva, só seguir os botões
                
