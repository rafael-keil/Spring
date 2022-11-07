import { useEffect, useState } from "react";
import { Link, useHistory } from "react-router-dom";
import { useGlobalUser } from "../../../context";
import { useMeLevaAiApi } from "../../../hooks/api/meLevaAi/useMeLevaAiApi";
import { ROUTES } from "../../../routes/routes.constants";
import { ListaVeiculos } from "../../components";
import "./homeMotorista.style.css";

export function HomeMotorista() {
  const [passageiros, setPassageiros] = useState([]);
  const { push } = useHistory();
  const [veiculos, setVeiculos] = useState([]);
  const [corridas, setCorridas] = useState([]);
  const [corridaEmAndamento, setCorridaEmAndamento] = useState([]);
  const [saldo, setSaldo] = useState("");
  const [saldoAtual, setSaldoAtual] = useState(0);
  const [isChange, setIsChange] = useState(false);
  const [user] = useGlobalUser();
  const [, setError] = useState(null);
  const useApi = useMeLevaAiApi();
  const [corridaParaAvaliar, setCorridaParaAvaliar] = useState(null);
  const [notaPassageiro, setNotaPassageiro] = useState(1);

  useEffect(() => {
    async function getVeiculos() {
      try {
        const response = await useApi.listarVeiculos();
        const newVeiculos = response
          ? response.filter(
              (veiculo) => veiculo.motorista.cpf.numero === user.cpf
            )
          : [];

        setVeiculos(newVeiculos);
      } catch {
        setError("Não foi possível carregar os veiculos ou corridas da API");
      }
    }

    async function getMotorista() {
      const newUser = await useApi.getMotorista(user.cpf);

      setSaldoAtual(newUser.saldo.saldo);
    }

    async function getCorridas() {
      try {
        const corridasMotorista = await useApi.getCorridasMotorista(user.cpf);

        setCorridas(corridasMotorista);
      } catch {
        setError("Não foi possível carregar os veiculos ou corridas da API");
      }
    }

    async function getCorridaPassageiroNaoAvaliado() {
      const newCorridas = await useApi.getCorridasMotorista(user.cpf);
      const corridasFind = newCorridas
        ? newCorridas.find(
            (corrida) =>
              !corrida.passageiroAvaliado &&
              corrida.estadoCorrida === "FINALIZADA"
          )
        : null;
      setCorridaParaAvaliar(corridasFind);
    }

    getCorridaPassageiroNaoAvaliado();

    async function getPassageiro() {
      try {
        const newPassageiros = await useApi.listarPassageiros();

        setPassageiros(newPassageiros);
      } catch {
        setError("Não foi possível carregar os veiculos ou corridas da API");
      }
    }

    getPassageiro();
    getVeiculos();
    getCorridas();
    getMotorista();
  }, [useApi, isChange]);

  useEffect(() => {
    async function getCorridasNaoFinalizadas() {
      try {
        const corridaEmAndamentoEncontrada = corridas.find(
          (corrida) => corrida.estadoCorrida !== "FINALIZADA"
        );

        setCorridaEmAndamento(corridaEmAndamentoEncontrada);
      } catch {
        setError("Não foi possível carregar os veiculos ou corridas da API");
      }
    }
    getCorridasNaoFinalizadas();
  }, [corridas]);

  async function handleAccept() {
    await useApi.iniciarCorrida(corridaEmAndamento.id);

    setIsChange(!isChange);
  }

  async function handleEnd() {
    await useApi.finalizarCorrida(corridaEmAndamento.id);

    setIsChange(!isChange);
  }

  async function handleSubmitSaque(event) {
    event.preventDefault();

    await useApi.sacarCreditoMotorista(user.cpf, saldo);

    setIsChange(!isChange);
  }

  function handleChangeSaldo(event) {
    setSaldo(event.target.value);
  }

  async function handleDelete(event) {
    event.preventDefault();

    if (window.confirm("Deseja deletar?")) {
      await useApi.excluirMotorista(user.cpf);
      push(ROUTES.HOME);
    }
  }

  async function handleAvaliar(event) {
    event.preventDefault();
    const notaInteger = parseInt(notaPassageiro);

    await useApi.avaliarPassageiros(corridaParaAvaliar.id, notaInteger);

    setIsChange(!isChange);
  }

  function handleChangeNota(event) {
    setNotaPassageiro(event.target.value);
  }
  return (
    <div className="home_motorista">
      <div className="home_motorista__container">
        <h2>Tela do motorista</h2>
        <h3>Saldo atual: </h3>
        <h3>R$ {parseFloat(saldoAtual.toFixed(2))}</h3>

        <form onSubmit={handleSubmitSaque} className="home_motorista__form">
          <input
            onChange={handleChangeSaldo}
            type="text"
            value={saldo}
            name="saldo"
          />

          <button>SACAR</button>
        </form>

        {veiculos.length >= 1 ? (
          <div className="home_motorista__veiculos">
            <h3>Veículos</h3>
            <ListaVeiculos lista={veiculos} />
          </div>
        ) : (
          <div>Nenhum veículo cadastrado</div>
        )}
        <Link to={ROUTES.CADASTRO_VEICULO}>
          <button>CADASTRAR VEÍCULO</button>
        </Link>

        {corridaEmAndamento ? (
          <div className="home_motorista__corrida">
            <h3>Corrida Atual</h3>
            <div>{corridaEmAndamento.id}</div>
            {corridaEmAndamento.estadoCorrida === "ESPERANDO" ? (
              <button onClick={handleAccept} disabled={corridaParaAvaliar}>
                ACEITAR
              </button>
            ) : (
              <button onClick={handleEnd}>FINALIZAR</button>
            )}
          </div>
        ) : (
          ""
        )}
        {corridaParaAvaliar ? (
          <form onSubmit={handleAvaliar} className="home_motorista__form">
            <label htmlFor="notaPassageiro">Avalie o seu passageiro</label>
            <input
              onChange={handleChangeNota}
              type="text"
              name="notaPassageiro"
              id="notaPassageiro"
              placeholder="Insira uma nota de 1 até 5"
            />
            <button disabled={!corridaParaAvaliar}>Enviar</button>
          </form>
        ) : (
          ""
        )}
        <div className="home_passageiro__corrida_list">
          {corridas.length && passageiros.length
            ? corridas.map((corrida) => {
                return (
                  <div className="home_passageiro__corrida" key={corrida.id}>
                    <p>ID da corrida: {corrida.id}</p>{" "}
                    <p>
                      Nome do Passageiro:{" "}
                      {
                        passageiros.find(
                          (passageiro) =>
                            passageiro.cpf.numero ===
                            corrida.cpfPassageiro.numero
                        ).nome
                      }
                    </p>
                    <p>Placa do carro: {corrida.veiculoSelecionado.placa}</p>
                    <p>
                      Carro: {corrida.veiculoSelecionado.marca} /{" "}
                      {corrida.veiculoSelecionado.cor}
                    </p>
                  </div>
                );
              })
            : ""}
        </div>

        <button onClick={handleDelete}>DELETAR CONTA</button>
        <Link to={ROUTES.HOME}>
          <button> VOLTAR</button>
        </Link>
      </div>
    </div>
  );
}
