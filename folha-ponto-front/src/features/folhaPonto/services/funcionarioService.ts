import axios from "axios";
import type { Funcionario, FuncionarioRequest } from "../types/folha.types";

export type { Funcionario };

export const getFuncionarios = async (): Promise<Funcionario[]> => {
  const response = await axios.get<Funcionario[]>("http://localhost:8080/funcionarios/listar");
  return response.data;
};

export const createFuncionario = async (funcionario: FuncionarioRequest): Promise<Funcionario> => {
  const response = await axios.post<Funcionario>("http://localhost:8080/funcionarios/cadastrar", funcionario);
  return response.data;
}

export const updateFuncionario = async (funcionario: Funcionario): Promise<Funcionario> => {
  const response = await axios.put<Funcionario> (`http://localhost:8080/funcionarios/${funcionario.id}/atualizar`, funcionario);
  return response.data;
}

export const deleteFuncionario = async (id: number): Promise<void> => {
  await axios.delete<Funcionario> (`http://localhost:8080/funcionarios/${id}`);

}