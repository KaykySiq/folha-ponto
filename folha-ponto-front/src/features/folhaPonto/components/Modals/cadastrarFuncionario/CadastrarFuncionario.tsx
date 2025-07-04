import { useState } from "react";
import styles from "./CadastrarFuncionario.module.css";
import { createFuncionario } from "../../../services/funcionarioService";

interface Props {
  onClose: () => void;
  onFuncionarioCadastrado: () => void;
}

const CadastrarFuncionario = ({ onClose, onFuncionarioCadastrado }: Props) => {
  const [name, setName] = useState("");
  const [employeeId, setEmployeeId] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await createFuncionario({ name, employeeId });
      setName("");
      setEmployeeId("");
      onFuncionarioCadastrado();
      console.log("Funcionário cadastrado com sucesso!");
    } catch (error) {
      console.error("Erro ao cadastrar funcionário:", error);
    }
  }
  return (
    <div className="overlay">
      <div className="modal">
        <h2>Cadastrar um novo Funcionário</h2>
        <form className={styles.formCadastro} onSubmit={handleSubmit}> 
            <div>
                <label>Nome do Funcionário:</label>
                <input type="text" value={name} onChange={(e) => setName(e.target.value.toUpperCase())} />
            </div>
            <div>
                <label>Matrícula: </label>
                <input type="text" value={employeeId} onChange={(e) => setEmployeeId(e.target.value.toUpperCase())} />
            </div>
            <button className={styles.btnCadastrar} type="submit">Cadastrar</button>
        </form>
        <button className={styles.closeBtn} onClick={onClose}>Fechar</button>
      </div>
    </div>
  );
};

export default CadastrarFuncionario;