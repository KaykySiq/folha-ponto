import axios from "axios";
import type { DataSheet } from "../types/folha.types";

export const uploadArquivo = async (file: File): Promise<DataSheet> => {
  const formData = new FormData();
  formData.append("file", file);

  const response = await axios.post("http://localhost:8080/arquivo/upload", formData, {
    headers: { "Content-Type": "multipart/form-data" },
  });

  const data = response.data as DataSheet;

  return {
    funcionario: data.funcionario,
    employeeId: data.employeeId,
    monthRef: data.monthRef,
    yearRef: data.yearRef,
  } as DataSheet;
};

export const editExcelFile = async (
  file: File,
  name: string,
  employeeId: string,
  month: string,
  year: string
): Promise<Blob> => {
  const formData = new FormData();
  formData.append("file", file);
  formData.append("name", name);
  formData.append("employeeId", employeeId);
  formData.append("month", month);
  formData.append("year", year);

  const response = await axios.post("http://localhost:8080/excel/editar", formData, {
    headers: { "Content-Type": "multipart/form-data" },
    responseType: "blob",
  });

  return response.data as Blob;
};
