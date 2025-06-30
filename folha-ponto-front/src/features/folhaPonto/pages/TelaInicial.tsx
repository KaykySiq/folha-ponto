import { useState } from "react";
import { editExcelFile } from "../services/fileService";
import logoIbametro from "../../../assets/images/logoIbametro.png";

const TelaInicial = () => {
  const [name, setname] = useState("");
  const [employeeId, setemployeeId] = useState("");
  const [month, setmonth] = useState("");
  const [year, setyear] = useState("");
  const [file, setFile] = useState<File | null>(null);
  const [excelBlob, setExcelBlob] = useState<Blob | null>(null);

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFile(e.target.files?.[0] ?? null);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!file || !name || !employeeId || !month || !year) return;

    const blob = await editExcelFile(file, name, employeeId, month, year);
    setExcelBlob(blob);
  };

  const handleDownload = () => {
    if (!excelBlob) return;
    const url = window.URL.createObjectURL(excelBlob);
    const a = document.createElement("a");
    a.href = url;
    a.download = "FOLHA_PONTO.xls";
    document.body.appendChild(a);
    a.click();
    a.remove();
    window.URL.revokeObjectURL(url);
  };

  const handleListFuncionarios = () => {
    alert("Funcionalidade de lista de funcionários!");
  };

  return (
    <div className="tela-inicial-container">
      <img src={logoIbametro} alt="Logo do Ibametro" />

      <h1>Folha de Ponto</h1>
      <form className="tela-inicial-form" onSubmit={handleSubmit}>
        <div>
          <label>name:</label>
          <input
            value={name}
            onChange={(e) => setname(e.target.value)}
            required
            type="text"
          />
        </div>
        <div>
          <label>Matrícula:</label>
          <input
            value={employeeId}
            onChange={(e) => setemployeeId(e.target.value)}
            required
            type="text"
          />
        </div>
        <div>
          <label>Mês de Referência:</label>
          <input
            value={month}
            onChange={(e) => setmonth(e.target.value)}
            required
            placeholder="Janeiro"
            type="text"
          />
        </div>
        <div>
          <label>year de Referência:</label>
          <input
            value={year}
            onChange={(e) => setyear(e.target.value)}
            required
            placeholder="2025"
            type="text"
          />
        </div>
        <div>
          <label>Arquivo:</label>
          <input
            type="file"
            accept=".xls,.xlsx"
            onChange={handleFileChange}
            required
          />
        </div>
        <div className="btns">
          <button type="submit" className="btn-editar">
            Gerar Folha de Ponto
          </button>
          {excelBlob && (
          <button
            type="button"
            className="btn-download"
          onClick={handleDownload}
          >
            Download
          </button>
          )}
        </div>

      </form>
      <button type="button" className="btn-funcionarios" onClick={handleListFuncionarios}>
        Lista de Funcionários
      </button>
    </div>
  );
};

export default TelaInicial;