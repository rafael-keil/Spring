import "./home.style.css";
import { ListaPessoas } from "../../components";
import { Link, useHistory } from "react-router-dom";
import { ROUTES } from "../../../routes/routes.constants";
import { useState, useEffect } from "react";
import { useMeLevaAiApi } from "../../../hooks/api/meLevaAi/useMeLevaAiApi";
import { useGlobalUser } from "../../../context";

export function HomeScreen() {
  const [passageiros, setPassageiros] = useState([]);
  const [motoristas, setMotoristas] = useState([]);
  const [, setError] = useState(null);
  const [, setGlobalUser] = useGlobalUser();

  const { push } = useHistory();
  const useApi = useMeLevaAiApi();

  useEffect(() => {
    async function getPassageiros() {
      try {
        const response = await useApi.listarPassageiros();
        setPassageiros(response);
      } catch {
        setError("Não foi possível carregar os passageiros da API");
      }
    }

    async function getMotoristas() {
      try {
        const response = await useApi.listarMotoristas();
        setMotoristas(response);
      } catch {
        setError("Não foi possível carregar os motoristas da API");
      }
    }

    getPassageiros();
    getMotoristas();
  }, [useApi]);

  function handleClick(item, passageiro) {
    const newUser = {
      passageiro: passageiro,
      cpf: item.cpf.numero,
    };

    setGlobalUser(newUser);

    passageiro ? push(ROUTES.HOME_PASSAGEIRO) : push(ROUTES.HOME_MOTORISTA);
  }

  return (
    <div className="home">
      <div className="home__container">
        <h1>Selecione o seu usuário</h1>
        {passageiros.length >= 1 ? (
          <div className="home__list">
            <h3>Passageiros</h3>
            <ListaPessoas
              lista={passageiros}
              handleClick={handleClick}
              passageiro={true}
            />
          </div>
        ) : (
          <div>Nenhum passageiro cadastrado</div>
        )}

        {motoristas.length >= 1 ? (
          <div className="home__list">
            <h3>Motoristas</h3>
            <ListaPessoas
              lista={motoristas}
              handleClick={handleClick}
              passageiro={false}
            />
          </div>
        ) : (
          <div>Nenhum motorista cadastrado</div>
        )}

        <Link to={ROUTES.CADASTRO}>
          <button className="home__button">CADASTRO</button>
        </Link>
      </div>
    </div>
  );
}
