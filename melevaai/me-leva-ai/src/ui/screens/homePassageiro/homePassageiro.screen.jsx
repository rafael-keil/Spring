import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useGlobalUser } from '../../../context/selected-cpf/selected-cpf.context';
import { useMeLevaAiApi } from '../../../hooks/api/meLevaAi/useMeLevaAiApi';
import { ROUTES } from '../../../routes/routes.constants';
import './homePassageiro.style.css';

export function HomePassageiro() {
  const [corridas, setCorridas] = useState([]);
  const [corridaAtual, setCorridaAtual] = useState(null);
  const [corridaParaAvaliar, setCorridaParaAvaliar] = useState(null);
  const [saldo, setSaldo] = useState('');
  const [saldoAtual, setSaldoAtual] = useState(0);
  const [notaMotorista, setNotaMotorista] = useState(1);
  const [coords, setCoords] = useState({
    xInicio: '',
    yInicio: '',
    xFim: '',
    yFim: '',
  });
  const [atualize, setAtualize] = useState(false);
  const [user] = useGlobalUser();
  const useApi = useMeLevaAiApi();

  useEffect(() => {
    async function getUser() {
      const newUser = await useApi.getPassageiro(user.cpf);

      setSaldoAtual(newUser.saldo.saldo);
    }

    getUser();

    async function getCorridas() {
      const newCorridas = await useApi.getCorridasPassageiro(user.cpf);
      const corridasFind = newCorridas
        ? newCorridas.find((corrida) => corrida.estadoCorrida !== 'FINALIZADA')
        : null;

      setCorridaAtual(corridasFind);
      setCorridas(newCorridas);
    }

    getCorridas();

    async function getCorridaMotoristaNaoAvaliado() {
      const newCorridas = await useApi.getCorridasPassageiro(user.cpf);
      const corridasFind = newCorridas
        ? newCorridas.find((corrida) => corrida.motoristaAvaliado === false)
        : null;
      setCorridaParaAvaliar(corridasFind);
    }

    getCorridaMotoristaNaoAvaliado();
  }, [atualize]);

  function handleChangeSaldo(event) {
    setSaldo(event.target.value);
  }

  function handleChangeCoords(event) {
    const { value, name } = event.target;

    const newCoords = {
      ...coords,
      [name]: value,
    };

    setCoords(newCoords);
  }

  async function handleSubmitDeposit(event) {
    event.preventDefault();

    await useApi.incluirCreditoPassageiro(user.cpf, saldo);

    setAtualize(!atualize);
  }

  async function handleSubmitRide(event) {
    event.preventDefault();

    await useApi.chamarCorrida(user.cpf, coords);

    setAtualize(!atualize);
  }

  async function handleAvaliar(event) {
    event.preventDefault();
    const notaInteger = parseInt(notaMotorista);

    await useApi.avaliarMotorista(corridaParaAvaliar.id, notaInteger);
    setAtualize(!atualize);
  }

  function handleChangeNota(event) {
    setNotaMotorista(event.target.value);
  }
  return (
    <div className="home_passageiro">
      <div className="home_passageiro__container">
        <h2>Tela Passageiro</h2>
        <h3>Saldo atual:</h3>
        <h3>R$ {parseFloat(saldoAtual.toFixed(2))}</h3>

        <form onSubmit={handleSubmitDeposit} className="home_passageiro__form">
          <input
            onChange={handleChangeSaldo}
            type="text"
            value={saldo}
            name="saldo"
          />

          <button>Depositar</button>
        </form>

        {corridaAtual !== null &&
        corridaAtual !== undefined &&
        corridaAtual.estadoCorrida === 'ESPERANDO' ? (
          <div>
            <p>Tempo estimado: {corridaAtual.tempoEstimado} minutos</p>
            <p>{corridaAtual.id}</p>
          </div>
        ) : (
          <div className="home_passageiro__ride">
            <h3>Chamar corrida</h3>
            <form onSubmit={handleSubmitRide} className="home_passageiro__form">
              <input
                onChange={handleChangeCoords}
                type="text"
                value={coords.xInicio}
                name="xInicio"
                placeholder="xInicio"
              />
              <input
                onChange={handleChangeCoords}
                type="text"
                value={coords.yInicio}
                name="yInicio"
                placeholder="yInicio"
              />
              <input
                onChange={handleChangeCoords}
                type="text"
                value={coords.xFim}
                name="xFim"
                placeholder="xFim"
              />
              <input
                onChange={handleChangeCoords}
                type="text"
                value={coords.yFim}
                name="yFim"
                placeholder="yFim"
              />

              <button
                disabled={
                  corridaAtual?.estadoCorrida === 'ANDAMENTO' ||
                  corridaParaAvaliar
                }
              >
                Chamar corrida
              </button>
            </form>
          </div>
        )}
        {corridaParaAvaliar ? (
          <form onSubmit={handleAvaliar} className="home_passageiro__form">
            <label htmlFor="notaMotorista">Avalie o seu motorista</label>
            <input
              onChange={handleChangeNota}
              type="text"
              name="notaMotorista"
              id="notaMotorista"
              placeholder="Insira uma nota de 1 até 5"
            />
            <button
              disabled={corridaParaAvaliar.estadoCorrida !== 'FINALIZADA'}
            >
              Enviar
            </button>
          </form>
        ) : (
          ''
        )}
        <div className="home_passageiro__corrida_list">
          {corridas.length
            ? corridas.map((corrida) => {
                return (
                  <div className="home_passageiro__corrida" key={corrida.id}>
                    <p>ID da corrida: {corrida.id}</p>{' '}
                    <p>
                      Nome do motorista:{' '}
                      {corrida.veiculoSelecionado.motorista.nome}
                    </p>
                    <p>Placa do carro: {corrida.veiculoSelecionado.placa}</p>
                    <p>
                      Carro: {corrida.veiculoSelecionado.marca} /{' '}
                      {corrida.veiculoSelecionado.cor}
                    </p>
                  </div>
                );
              })
            : ''}
        </div>

        <Link to={ROUTES.HOME}>
          <button> VOLTAR</button>
        </Link>
      </div>
    </div>
  );
}
