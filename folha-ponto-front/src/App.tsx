import './App.css'
import { BrowserRouter, Routes, Route } from "react-router-dom";
import TelaInicial from './features/folhaPonto/pages/TelaInicial/TelaInicial';
import ListaFuncionarios from './features/folhaPonto/pages/ListaFuncionarios/ListaFuncionarios';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<TelaInicial />} />
        <Route path="/funcionarios" element={<ListaFuncionarios />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
