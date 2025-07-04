import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import styles from "./ListaFuncionarios.module.css";
import { getFuncionarios, type Funcionario } from "../../services/funcionarioService";
import CadastrarFuncionario from "../../components/Modals/cadastrarFuncionario/CadastrarFuncionario";
import EditarFuncionario from "../../components/Modals/editarFuncionario/EditarFuncionario";
import DeletarFuncionario from "../../components/Modals/deletarFuncionario/DeletarFuncionario";
import excluir from "../../../../assets/icons/excluir.png"
import editar from "../../../../assets/icons/editar.png"

const ListaFuncionarios = () => {
  const [funcionarios, setFuncionarios] = useState<Funcionario[]>([]);
  const [showModal, setShowModal] = useState(false);
  const [funcionarioEdit, setFuncionarioEdit] = useState<Funcionario | null>(null);
  const [funcionarioDelete, setFuncionarioDelete] = useState<Funcionario | null>(null);

  const carregarFuncionarios = async () => {
    const lista = await getFuncionarios();
    setFuncionarios(lista);
  };

  useEffect(() => {
    carregarFuncionarios();
  }, []);

  const handleEditar = (funcionario: Funcionario) => {
    setFuncionarioEdit(funcionario);
    setShowModal(true);
  };

  const handleDeletar = (funcionario: Funcionario) => {
    setFuncionarioDelete(funcionario);
  };

  return (
    <div className={styles.container}>
      <div className={styles.buttonRow}>
        <Link to="/" className={styles.backBtn}>
          Voltar
        </Link>
        <h2 className={styles.title}>Lista de Funcionários</h2>
        <button
          className={styles.registerBtn}
          onClick={() => {
            setFuncionarioEdit(null);
            setShowModal(true);
          }}
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
            <tr key={f.id}>
              <td>{f.name}</td>
              <td>{f.employeeId}</td>
              <td className={styles.actions}>
                <button
                  className={styles.editBtn}
                  type="button"
                  onClick={() => handleEditar(f)}
                >
                  <img src={editar} alt="Editar" className={styles.icon} />
                </button>
                <button 
                  className={styles.delBtn} 
                  type="button" 
                  onClick={() => handleDeletar(f)}>
                    <img src={excluir} alt="Excluir" className={styles.icon} />
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {showModal && funcionarioEdit === null && (
        <CadastrarFuncionario
          onClose={() => setShowModal(false)}
          onFuncionarioCadastrado={carregarFuncionarios}
        />
      )}

      {showModal && funcionarioEdit && (
        <EditarFuncionario
          onClose={() => {
            setShowModal(false);
            setFuncionarioEdit(null);
          }}
          onFuncionarioEditado={carregarFuncionarios}
          funcionario={funcionarioEdit}
        />
      )}

      {funcionarioDelete && (
        <DeletarFuncionario
          onClose={() => setFuncionarioDelete(null)}
          onFuncionarioDeletado={() => {
            setFuncionarioDelete(null);
            carregarFuncionarios();
          }}
          funcionario={funcionarioDelete}
        />
      )}
    </div>
  );
};

export default ListaFuncionarios;