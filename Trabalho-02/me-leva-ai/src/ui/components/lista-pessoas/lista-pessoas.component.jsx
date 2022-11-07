import "./lista-pessoas.style.css";

export function ListaPessoas({ lista, handleClick, passageiro }) {
  return (
    <div className="lista_pessoas">
      {lista.map((item) => {
        return (
          <button
            className="lista_pessoas__button"
            onClick={() => handleClick(item, passageiro)}
            key={item.cpf.numero}
          >
            {item.nome}
          </button>
        );
      })}
    </div>
  );
}
