import { useState } from "react";
import { Link, useHistory } from "react-router-dom";
import { CATEGORIA, COR, MARCA } from "../../../constants/info-veiculo";
import { useGlobalUser } from "../../../context";
import { useMeLevaAiApi } from "../../../hooks/api/meLevaAi/useMeLevaAiApi";
import { ROUTES } from "../../../routes/routes.constants";
import "./cadastro-veiculo.style.css";

export function CadastroVeiculoScreen() {
  const { push } = useHistory();
  const [globalUser] = useGlobalUser();
  const [carro, setCarro] = useState({
    placa: "",
    marca: "",
    modelo: "",
    ano: "",
    cor: "",
    urlFoto: "",
    lugares: "",
    categoriaCnh: "",
    cpfMotorista: globalUser.cpf,
  });
  const useApi = useMeLevaAiApi();

  function handleChange(event) {
    const { value, name } = event.target;

    const newCar = {
      ...carro,
      [name]: value,
    };

    setCarro(newCar);
  }

  async function handleSubmit(event) {
    event.preventDefault();

    console.log(carro);

    await useApi.cadastroVeiculo(carro);

    push(ROUTES.HOME_MOTORISTA);
  }

  return (
    <div className="cadastro_veiculo">
      <div className="cadastro_veiculo__container">
        <h2>Cadastro de veículo</h2>
        <form onSubmit={handleSubmit} className="cadastro_veiculo__form">
          <div className="cadastro_veiculo__item">
            <label htmlFor="placa">Placa:</label>
            <input
              onChange={handleChange}
              type="text"
              value={carro.placa}
              name="placa"
            />
          </div>

          <div className="cadastro_veiculo__item">
            <label htmlFor="marca">Marca:</label>
            <select
              defaultValue="Selecione uma marca"
              name="marca"
              onChange={handleChange}
            >
              <option value="Selecione uma marca" disabled>
                {" "}
                Selecione uma marca
              </option>
              {MARCA.map((item) => {
                return (
                  <option key={item} value={item}>
                    {item}
                  </option>
                );
              })}
            </select>
          </div>

          <div className="cadastro_veiculo__item">
            <label htmlFor="modelo">Modelo:</label>
            <input
              onChange={handleChange}
              type="text"
              value={carro.modelo}
              name="modelo"
            />
          </div>

          <div className="cadastro_veiculo__item">
            <label htmlFor="ano">Ano:</label>
            <input
              onChange={handleChange}
              type="text"
              value={carro.ano}
              name="ano"
            />
          </div>

          <div className="cadastro_veiculo__item">
            <label htmlFor="cor">Cor:</label>
            <select
              defaultValue="Selecione uma cor"
              name="cor"
              onChange={handleChange}
            >
              <option value="Selecione uma cor" disabled>
                {" "}
                Selecione uma cor
              </option>
              {COR.map((item) => {
                return (
                  <option key={item} value={item}>
                    {item}
                  </option>
                );
              })}
            </select>
          </div>

          <div className="cadastro_veiculo__item">
            <label htmlFor="urlFoto">Url Foto:</label>
            <input
              onChange={handleChange}
              type="text"
              value={carro.urlFoto}
              name="urlFoto"
            />
          </div>

          <div className="cadastro_veiculo__item">
            <label htmlFor="lugares">Lugares:</label>
            <input
              onChange={handleChange}
              type="text"
              value={carro.lugares}
              name="lugares"
            />
          </div>

          <div className="cadastro_veiculo__item">
            <label htmlFor="categoriaCnh">Categoria CNH:</label>
            <select
              defaultValue="Selecione uma categoria"
              name="categoriaCnh"
              onChange={handleChange}
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

          <button>Enviar</button>
        </form>

        <Link to={ROUTES.HOME_MOTORISTA}>
          <button> VOLTAR</button>
        </Link>
      </div>
    </div>
  );
}
