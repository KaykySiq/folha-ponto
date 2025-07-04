import { deleteFuncionario } from "../../../services/funcionarioService";
import styles from "../deletarFuncionario/DeletarFuncionario.module.css"

interface Props {
    onClose: () => void;
    onFuncionarioDeletado: () => void;
    funcionario: { id: number, name: string};
}

const DeletarFuncionario = ({ onClose, onFuncionarioDeletado, funcionario }: Props) => {
    const handleDelete = async () => {
        try {
            await deleteFuncionario(funcionario.id);
            onFuncionarioDeletado();
            onClose();
        } catch (error) {
            console.error("Erro ao deletar funcionário.", error);
        }
    };
    return (
        <div className="overlay">
            <div className={`modal ${styles.deletModal}`}>
                <h2>Confirmar Exclusão</h2>
                <p>Tem certeza que deseja deletar "{funcionario.name}" da lista de funcionários?</p>
                <div className={styles.btns}>
                    <button className={styles.btnDel} onClick={handleDelete}>Deletar</button>
                    <button className={styles.btnCancel} onClick={onClose}>Cancelar</button>
                </div>
            </div>
        </div>
    );
};

export default DeletarFuncionario;