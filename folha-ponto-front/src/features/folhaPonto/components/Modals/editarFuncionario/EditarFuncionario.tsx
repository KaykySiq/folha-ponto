import { useEffect, useState } from "react";
import { updateFuncionario } from "../../../services/funcionarioService";
import styles from "./EditarFuncionario.module.css";

interface Props {
    onClose: () => void;
    onFuncionarioEditado: () => void;
    funcionario: { id: number; name: string; employeeId: string };
}

const EditarFuncionario = ({ onClose, onFuncionarioEditado, funcionario }: Props) => {
    const [name, setName] = useState(funcionario.name);
    const [employeeId, setEmployeeId] = useState(funcionario.employeeId);

    useEffect(() => {
        setName(funcionario.name);
        setEmployeeId(funcionario.employeeId);
    }, [funcionario]);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            await updateFuncionario({ id: funcionario.id, name, employeeId });
            setName("");
            setEmployeeId("");
            onFuncionarioEditado();
            onClose();
            console.log("Funcionário editado com sucesso!");
        } catch (error) {
            console.error("Erro ao editar funcionário:", error);
        }
    }
    return (
        <div className="overlay">
            <div className="modal">
                <h2>Editar Funcionário</h2>
                <form className={styles.formEditar} onSubmit={handleSubmit}>
                    <div>
                        <label>Nome do Funcionário:</label>
                        <input type="text" value={name} onChange={(e) => setName(e.target.value)} />
                    </div>
                    <div>
                        <label>ID do Funcionário:</label>
                        <input type="text" value={employeeId} onChange={(e) => setEmployeeId(e.target.value)} />
                    </div>
                    <button className={styles.btnSalvar} type="submit">Salvar</button>
                </form>

                <button className={styles.closeBtn} onClick={onClose}>Fechar</button>
            </div>
        </div>
    );
};

export default EditarFuncionario;