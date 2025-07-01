import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import styles from "./ListaFuncionarios.module.css";
import { getFuncionarios, type Funcionario } from "../../services/funcionarioService";
import CadastrarFuncionario from "../../components/Modals/CadastrarFuncionario";

const ListaFuncionarios = () => {
  const [funcionarios, setFuncionarios] = useState<Funcionario[]>([]);
  const [showModal, setShowModal] = useState(false);

  const carregarFuncionarios = async () => {
    const lista = await getFuncionarios();
    setFuncionarios(lista);
  };

  useEffect(() => {
    carregarFuncionarios();
  }, []);

  return (
    <div className={styles.container}>
      <div className={styles.buttonRow}>
        <Link to="/" className={styles.backBtn}>
          Voltar
        </Link>
        <h2 className={styles.title}>Lista de Funcionários</h2>
        <button
          className={styles.registerBtn}
          onClick={() => setShowModal(true)}
          type="button"
        >
          Cadastrar
        </button>
      </div>
        <table className={styles.table}>
          <thead>
            <tr>
              <th>Nome</th>
              <th>Matrícula</th>
              <th>Editar</th>
            </tr>
          </thead>
          <tbody>
            {funcionarios.map(f => (
              <tr>
                <td>{f.name}</td>
                <td>{f.employeeId}</td>
                <td><button className={styles.editBtn} type="button">Editar</button></td>
              </tr>
            ))}
          </tbody>
        </table>

      {showModal && <CadastrarFuncionario onClose={() => setShowModal(false)} onFuncionarioCadastrado={carregarFuncionarios} />}
    </div>
  );
};

export default ListaFuncionarios;