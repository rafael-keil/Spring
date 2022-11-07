import "./lista-veiculos.style.css";

export function ListaVeiculos({ lista }) {
  return (
    <div className="lista_veiculos">
      {lista.map((item) => {
        return (
          <div className="lista_veiculos_item" key={item.placa}>
            <p>{item.placa}</p>
            <p>{item.modelo}</p>
          </div>
        );
      })}
    </div>
  );
}
