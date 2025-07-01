import axios from "axios";
import type { Funcionario } from "../types/folha.types";

export type { Funcionario };

export const getFuncionarios = async (): Promise<Funcionario[]> => {
  const response = await axios.get<Funcionario[]>("http://localhost:8080/funcionarios/listar");
  return response.data;
};

export const createFuncionario = async (funcionario: Funcionario): Promise<Funcionario> => {
  const response = await axios.post<Funcionario>("http://localhost:8080/funcionarios/cadastrar", funcionario);
  return response.data;
}