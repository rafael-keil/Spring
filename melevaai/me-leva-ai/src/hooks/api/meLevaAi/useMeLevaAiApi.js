import { useMemo } from 'react';
import { useHttp } from '../_base/use-http';

export function useMeLevaAiApi() {
  const httpInstance = useHttp('http://localhost:8100/me-leva-ai');

  async function cadastroPassageiro(passageiro) {
    const response = await httpInstance.post('/passageiros', passageiro);
    return response;
  }

  async function cadastroMotorista(motorista) {
    const response = await httpInstance.post('/motoristas', motorista);
    return response;
  }

  async function cadastroVeiculo(veiculo) {
    const response = await httpInstance.post('/veiculos', veiculo);
    return response;
  }

  async function listarPassageiros() {
    const response = await httpInstance.get('/passageiros');
    return response;
  }

  async function listarMotoristas() {
    const response = await httpInstance.get('/motoristas');
    return response;
  }

  async function listarCorridas() {
    const response = await httpInstance.get('/corridas');
    return response;
  }

  async function listarVeiculos() {
    const response = await httpInstance.get('/veiculos');
    return response;
  }

  async function avaliarMotorista(idCorrida, nota) {
    const response = await httpInstance.post(
      `/corridas/${idCorrida}/motoristas/avaliacao`,
      { nota }
    );
    return response;
  }

  async function avaliarPassageiros(idCorrida, nota) {
    const response = await httpInstance.post(
      `/corridas/${idCorrida}/passageiros/avaliacao`,
      { nota }
    );
    return response;
  }

  async function chamarCorrida(cpfPassageiro, coordenadas) {
    const response = await httpInstance.post(
      `/corridas/passageiros/${cpfPassageiro}`,
      coordenadas
    );
    return response;
  }

  async function iniciarCorrida(idCorrida) {
    const response = await httpInstance.post(`/corridas/${idCorrida}`);
    return response;
  }

  async function finalizarCorrida(idCorrida) {
    const response = await httpInstance.put(`/corridas/${idCorrida}`);
    return response;
  }

  async function excluirMotorista(cpfMotorista) {
    const response = await httpInstance.delet(`/motoristas/${cpfMotorista}`);
    return response;
  }

  async function sacarCreditoMotorista(cpfMotorista, valor) {
    const response = await httpInstance.put(
      `/motoristas/${cpfMotorista}/conta-virtual`,
      { valor }
    );
    return response;
  }

  async function incluirCreditoPassageiro(cpfPassageiro, valor) {
    const response = await httpInstance.put(
      `/passageiros/${cpfPassageiro}/conta-virtual`,
      { valor }
    );
    return response;
  }

  async function getPassageiro(cpfPassageiro) {
    const response = await httpInstance.get(`/passageiros/${cpfPassageiro}`);
    return response;
  }

  async function getMotorista(cpfMotorista) {
    const response = await httpInstance.get(`/motoristas/${cpfMotorista}`);
    return response;
  }

  async function getCorridasPassageiro(cpfPassageiro) {
    const response = await httpInstance.get(
      `/corridas/passageiros/${cpfPassageiro}`
    );
    return response;
  }

  async function getCorridasMotorista(cpfMotorista) {
    const response = await httpInstance.get(
      `/corridas/motoristas/${cpfMotorista}`
    );
    return response;
  }

  return useMemo(
    () => ({
      cadastroPassageiro,
      cadastroMotorista,
      cadastroVeiculo,
      listarPassageiros,
      listarMotoristas,
      listarCorridas,
      listarVeiculos,
      avaliarMotorista,
      avaliarPassageiros,
      chamarCorrida,
      iniciarCorrida,
      finalizarCorrida,
      excluirMotorista,
      sacarCreditoMotorista,
      incluirCreditoPassageiro,
      getPassageiro,
      getMotorista,
      getCorridasPassageiro,
      getCorridasMotorista,
    }),
    []
  );
}
