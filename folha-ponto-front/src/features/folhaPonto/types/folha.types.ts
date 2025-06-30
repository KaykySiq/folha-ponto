export interface Funcionario {
  id: number;
  name: string;
  employeeId: string;
}

export interface FuncionarioRequest {
  name: string;
  employeeId: string;
}

export interface DataSheet {
  funcionario: string;
  employeeId: string;  
  monthRef: string;
  yearRef: string;
}
