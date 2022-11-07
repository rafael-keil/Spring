import { useState } from "react";
import { useMeLevaAiApi } from "../../../hooks/api/meLevaAi/useMeLevaAiApi";
import { ROUTES } from "../../../routes/routes.constants";
import { Link, useHistory } from "react-router-dom";
import "./cadastro.style.css";
import { CATEGORIA } from "../../../constants/info-veiculo";

export function CadastroScreen() {
  const { push } = useHistory();
  const [isMotorista, setIsMotorista] = useState(false);
  const [user, setUser] = useState({
    nome: "",
    cpf: "",
    dataNascimento: "",
    email: "",
    carteiraHabilitacao: {
      categoria: "",
      numero: "",
      dataVencimento: "",
    },
  });

  const useApi = useMeLevaAiApi();

  function handleChange(event) {
    const { value, name } = event.target;

    const newUser = {
      ...user,
      [name]: value,
    };

    setUser(newUser);
  }

  function handleChangeCarteira(event) {
    const { value, name } = event.target;

    const newUser = {
      ...user,
      carteiraHabilitacao: {
        ...user.carteiraHabilitacao,
        [name]: value,
      },
    };

    setUser(newUser);
  }

  async function handleSubmit(event) {
    event.preventDefault();
    try {
      if (isMotorista) {
        await useApi.cadastroMotorista(user);
      } else {
        await useApi.cadastroPassageiro(user);
      }
      push(ROUTES.HOME);
    } catch {}
  }

  function handleCheckbox() {
    setIsMotorista(!isMotorista);
  }

  return (
    <div className="cadastro">
      <div className="cadastro__container">
        <h1>Cadastro</h1>
        <form onSubmit={handleSubmit} className="cadastro__form">
          <div className="cadastro__passageiro">
            <div className="cadastro__input">
              <label htmlFor="nome" className="">
                Nome:
              </label>
              <input
                onChange={handleChange}
                type="text"
                value={user.nome}
                name="nome"
              />
            </div>

            <div className="cadastro__input">
              <label htmlFor="cpf" className="">
                CPF:
              </label>
              <input
                onChange={handleChange}
                type="text"
                value={user.cpf}
                name="cpf"
              />
            </div>

            <div className="cadastro__input">
              <label htmlFor="dataNascimento" className="">
                Data de Nascimento:
              </label>
              <input
                onChange={handleChange}
                type="text"
                placeholder="dd/mm/aaaa"
                value={user.dataNascimento}
                name="dataNascimento"
              />
            </div>

            <div className="cadastro__input">
              <label htmlFor="email" className="">
                Email:
              </label>
              <input
                onChange={handleChange}
                type="text"
                placeholder="email@email.com"
                value={user.email}
                name="email"
              />
            </div>

            <div className="cadastro__checkbox">
              <label htmlFor="isMotorista" className="">
                É motorista?
              </label>
              <input
                onChange={handleCheckbox}
                type="checkbox"
                checked={isMotorista}
                name="isMotorista"
              />
            </div>
          </div>

          {isMotorista ? (
            <>
              <h3> carteira de habilitação</h3>
              <div className="cadastro__motorista">
                <div className="cadastro__input">
                  <label htmlFor="categoria" className="">
                    categoria:
                  </label>
                  <select
                    defaultValue="Selecione uma categoria"
                    name="categoria"
                    onChange={handleChangeCarteira}
                  >
                    <option value="Selecione uma categoria" disabled>
                      {" "}
                      Selecione uma categoria
                    </option>
                    {CATEGORIA.map((item) => {
                      return (
                        <option key={item} value={item}>
                          {item}
                        </option>
                      );
                    })}
                  </select>
                </div>

                <div className="cadastro__input">
                  <label htmlFor="numero" className="">
                    Número:
                  </label>
                  <input
                    onChange={handleChangeCarteira}
                    type="text"
                    value={user.carteiraHabilitacao.numero}
                    name="numero"
                  />
                </div>

                <div className="cadastro__input">
                  <label htmlFor="dataVencimento" className="">
                    Data de vencimento:
                  </label>
                  <input
                    onChange={handleChangeCarteira}
                    type="text"
                    placeholder="dd/mm/yyyy"
                    value={user.carteiraHabilitacao.dataVencimento}
                    name="dataVencimento"
                  />
                </div>
              </div>
            </>
          ) : null}

          <button>Enviar</button>
        </form>
        <Link to={ROUTES.HOME}>
          <button> VOLTAR</button>
        </Link>
      </div>
    </div>
  );
}
